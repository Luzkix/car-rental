package org.luzar.carrental.car.services;

import org.luzar.carrental.car.models.dto.CarDto;
import org.luzar.carrental.car.models.dto.CarResponseDto;

import java.util.List;

public interface CarService {

    CarResponseDto save(CarDto car);
    List<CarResponseDto> findAll();


}
