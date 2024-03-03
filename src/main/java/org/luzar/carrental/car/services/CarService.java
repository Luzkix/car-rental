package org.luzar.carrental.car.services;

import org.luzar.carrental.car.models.Car;
import org.luzar.carrental.car.models.dto.CarDto;
import org.luzar.carrental.car.models.dto.CarResponseDto;
import org.luzar.carrental.globalexceptionhandling.customexceptions.CustomExceptionEntityNotFound;

import java.util.List;

public interface CarService {

    CarResponseDto save(CarDto car);
    List<CarResponseDto> findAll();

    CarResponseDto findById(Long id);

    Car findCarById(Long id);

    CarResponseDto update(Long id, CarDto car) throws CustomExceptionEntityNotFound;

    CarResponseDto deleteById(Long id) throws CustomExceptionEntityNotFound;
}
