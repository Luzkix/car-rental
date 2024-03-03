package org.luzar.carrental.car.services;

import org.luzar.carrental.car.models.Car;
import org.luzar.carrental.car.models.dto.CarDto;
import org.luzar.carrental.car.models.dto.CarResponseDto;
import org.luzar.carrental.car.repositories.CarRepository;
import org.luzar.carrental.globalexceptionhandling.customexceptions.CustomExceptionEntityNotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public CarResponseDto save(CarDto dto) {
        return carMapper.carIntoResponseDto(
                carRepository.save(carMapper.dtoIntoCar(dto))
        );
    }

    @Override
    public CarResponseDto update(Long id, CarDto dto) throws CustomExceptionEntityNotFound{
        Car car = carRepository.findById(id).orElse(null);

        if (car == null || car.isDiscarded()) throw new CustomExceptionEntityNotFound("Car with ID " + id + " is not in DB / is discarded, thus cannot be updated!");

        BeanUtils.copyProperties(dto, car);

        return carMapper.carIntoResponseDto(
                carRepository.save(car)
        );
    }

    @Override
    public CarResponseDto findById(Long id) {
        return carMapper.carIntoResponseDto(
                carRepository.findById(id).orElse(null)
        );
    }

    @Override
    public Car findCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public List<CarResponseDto> findAll() {
        return carRepository.findAll().stream()
                .map(carMapper::carIntoResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CarResponseDto deleteById(Long id) throws CustomExceptionEntityNotFound {
        Car car = carRepository.findById(id).orElse(null);

        if (car == null) throw new CustomExceptionEntityNotFound("Car with ID " + id + " is not in DB, thus cannot be discarded");

        car.setDiscarded(true);
        car.setDiscardedDate(LocalDate.now());

        carRepository.save(car);

        return carMapper.carIntoResponseDto(car);
    }
}
