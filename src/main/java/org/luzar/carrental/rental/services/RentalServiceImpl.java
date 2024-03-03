package org.luzar.carrental.rental.services;

import org.luzar.carrental.car.models.Car;
import org.luzar.carrental.car.services.CarService;
import org.luzar.carrental.customer.models.Customer;
import org.luzar.carrental.customer.services.CustomerService;
import org.luzar.carrental.globalexceptionhandling.customexceptions.CustomExceptionAllInputParametersNull;
import org.luzar.carrental.globalexceptionhandling.customexceptions.CustomExceptionEntityNotFound;
import org.luzar.carrental.rental.models.Rental;
import org.luzar.carrental.rental.models.dto.RentalDto;
import org.luzar.carrental.rental.models.dto.RentalResponseDto;
import org.luzar.carrental.rental.repositories.RentalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService{
    private static final int RENTAL_RATE = 3000; //basic day rental rate in CZK
    private static final Map<Integer, Float> RATE_ADJUST = Map.ofEntries(
            Map.entry(1, 1F),
            Map.entry(2, 1.5F),
            Map.entry(3, 2.5F)
    );

    private final RentalRepository rentalRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final RentalMapper rentalMapper;

    public RentalServiceImpl(RentalRepository rentalRepository, CarService carService, CustomerService customerService, RentalMapper rentalMapper) {
        this.rentalRepository = rentalRepository;
        this.carService = carService;
        this.customerService = customerService;
        this.rentalMapper = rentalMapper;
    }


    @Override
    public RentalResponseDto save(RentalDto dto) throws CustomExceptionEntityNotFound{
        Car car = carService.findCarById(dto.carId());
        if(car == null) throw new CustomExceptionEntityNotFound("Car id does not exist or car was discarded! Rental was not processed.");

        Customer customer = customerService.findCustomerById(dto.customerId());
        if(customer == null) throw new CustomExceptionEntityNotFound("Customer id does not exist! Rental was not processed.");

        if(carIsRented(car)) throw new CustomExceptionEntityNotFound("Car is already rented!");

        Rental rental = new Rental();
        rental.setCreatedAt(LocalDateTime.now());
        rental.setRentedFromDate(dto.rentedFromDate());

        //setting rentPrice if rental is already closed
        if(dto.rentedToDate() != null) {
            rental.setRentedToDate(dto.rentedToDate());
            rental.setRentPrice(
                    calculateRentPrice(dto.rentedFromDate(), dto.rentedToDate(), car.getPriceCategory())
            );
        }

        rental.setCar(car);
        rental.setCustomer(customer);

        Rental savedRental = rentalRepository.save(rental);

        return rentalMapper.rentalIntoResponseDto(savedRental);
    }

    private boolean carIsRented(Car car) {

        return rentalRepository.findAllByRentedToDateNull()
                .stream()
                .anyMatch(rental -> rental.getCar().equals(car));

    }

    private int calculateRentPrice(LocalDate fromDate, LocalDate toDate, int priceCategory) {

        long daysBetween = ChronoUnit.DAYS.between(fromDate, toDate);
        Float rateAdj = RATE_ADJUST.get(priceCategory);

        return Math.round(daysBetween * RENTAL_RATE * rateAdj);
    }

    @Override
    public RentalResponseDto update(Long id, LocalDate rentedToDate, Integer rentPrice)
            throws CustomExceptionEntityNotFound, CustomExceptionAllInputParametersNull{
        Rental rental = rentalRepository.findById(id).orElse(null);

        if (rental == null) throw new CustomExceptionEntityNotFound("Rental with ID " + id + " is not in DB, thus cannot be updated!");

        if (rentedToDate == null && rentPrice == null)
            throw new CustomExceptionAllInputParametersNull("All input params are null, nothing to be updated!");


        //updating rentedToDate and rentPrice
        if (rentedToDate != null) {
            rental.setRentedToDate(rentedToDate);
            rental.setRentPrice(
                    calculateRentPrice(rental.getRentedFromDate(), rental.getRentedToDate(), rental.getCar().getPriceCategory())
            );
        }

        //updating rentPrice (e.g. overriding automatically calculated price) -> only when rentedToDate is not null!
        if (rentPrice != null) {
            if (rental.getRentedToDate() == null) throw new CustomExceptionAllInputParametersNull("Cannot set rentPrice for ongoing rentals (null rentedToDate field)!");
            rental.setRentPrice(rentPrice);
        }

        return rentalMapper.rentalIntoResponseDto(rental);
    }

    @Override
    public RentalResponseDto findById(Long id) {
        return rentalMapper.rentalIntoResponseDto(
                rentalRepository.findById(id).orElse(null)
        );
    }

    @Override
    public List<RentalResponseDto> findAll() {
        return rentalRepository.findAll().stream()
                .map(rentalMapper::rentalIntoResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentalResponseDto deleteById(Long id) throws CustomExceptionEntityNotFound{
        Rental rental = rentalRepository.findById(id).orElse(null);

        if (rental == null) throw new CustomExceptionEntityNotFound("Rental with ID " + id + " is not in DB, thus cannot be deleted");

        rentalRepository.delete(rental);

        return rentalMapper.rentalIntoResponseDto(rental);
    }
}
