package org.luzar.carrental.car.services;

import org.luzar.carrental.car.models.dto.CarDto;
import org.luzar.carrental.car.models.dto.CarResponseDto;
import org.luzar.carrental.car.repositories.CarRepository;
import org.springframework.stereotype.Service;

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
    public List<CarResponseDto> findAll() {
        return carRepository.findAll().stream()
                .map(carMapper::carIntoResponseDto)
                .collect(Collectors.toList());
    }
}
