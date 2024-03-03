package org.luzar.carrental;

import lombok.extern.slf4j.Slf4j;
import org.luzar.carrental.car.models.Car;
import org.luzar.carrental.car.models.dto.CarDto;
import org.luzar.carrental.car.models.dto.CarResponseDto;
import org.luzar.carrental.car.services.CarService;
import org.luzar.carrental.customer.models.Customer;
import org.luzar.carrental.customer.models.dto.CustomerDto;
import org.luzar.carrental.customer.models.dto.CustomerResponseDto;
import org.luzar.carrental.customer.services.CustomerService;
import org.luzar.carrental.rental.models.dto.RentalDto;
import org.luzar.carrental.rental.services.RentalService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Transactional
@Component
public class AppStartupRunner implements ApplicationRunner {
    //The class is responsible for populating some initial data into the database (if db is empty) -> for convenience
    private final CarService carService;
    private final CustomerService customerService;
    private final RentalService rentalService;

    public AppStartupRunner(CarService carService, CustomerService customerService, RentalService rentalService) {
        this.carService = carService;
        this.customerService = customerService;
        this.rentalService = rentalService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Checking if database is empty");
        if (carService.findAll().isEmpty()) {
            log.info("Database is empty -> initiating creation of default entities into database");
            createDefaultObjectsIntoDtb();
        }
    }

    private void createDefaultObjectsIntoDtb() {
        List<Car> cars = createDefaultCars();
        List<Customer> customers = createDefaultCustomers();
        createDefaultRentals(cars, customers);
    }

    private List<Car> createDefaultCars() {
        log.info("Creating default cars");

        List<Car> cars = new ArrayList<>();

        List<CarDto> carDtos = Arrays.asList(
                new CarDto("skoda","model1", LocalDate.parse("2020-01-01"),"green",1000,"1111111",1),
                new CarDto("mercedes","model2", LocalDate.parse("2021-01-01"),"yellow",2000,"1111112",3),
                new CarDto("vw","model3", LocalDate.parse("2022-01-01"),"silver",3000,"1111113",2),
                new CarDto("volvo","model4", LocalDate.parse("2023-01-01"),"red",4000,"1111114",2),
                new CarDto("bmw","model5", LocalDate.parse("2019-01-01"),"black",5000,"1111115",3)
        );

        carDtos.forEach(carDto -> {
            CarResponseDto responseDto = carService.save(carDto);
            cars.add(carService.findCarById(responseDto.id()));
        });

        return cars;
    }

    private List<Customer> createDefaultCustomers() {
        log.info("Creating default customers");

        List<Customer> customers = new ArrayList<>();

        List<CustomerDto> customerDtos = Arrays.asList(
                new CustomerDto("name1", "surname1", LocalDate.parse("1980-01-01"), "address1", "1email@seznam.cz", "420111111111"),
                new CustomerDto("name2", "surname2", LocalDate.parse("1990-01-01"), "address2", "2email@seznam.cz", "420111111112"),
                new CustomerDto("name3", "surname3", LocalDate.parse("2000-01-01"), "address3", "3email@seznam.cz", "420111111113")
        );

        customerDtos.forEach(customerDto -> {
            CustomerResponseDto responseDto = customerService.save(customerDto);
            customers.add(customerService.findCustomerById(responseDto.id()));
        });

        return customers;
    }

    private void createDefaultRentals(List<Car> cars, List<Customer> customers) {
        log.info("Creating default rentals");

        List<RentalDto> rentalDtos = Arrays.asList(
                new RentalDto(LocalDate.parse("2024-01-01"),LocalDate.parse("2024-01-11"),cars.get(0).getId(),customers.get(0).getId()),
                new RentalDto(LocalDate.parse("2024-02-01"),LocalDate.parse("2024-02-05"),cars.get(1).getId(),customers.get(1).getId()),
                new RentalDto(LocalDate.parse("2024-03-01"),null,cars.get(0).getId(),customers.get(1).getId()),
                new RentalDto(LocalDate.parse("2024-02-03"),null,cars.get(4).getId(),customers.get(2).getId())
        );

        rentalDtos.forEach(rentalService::save);
    }
}
