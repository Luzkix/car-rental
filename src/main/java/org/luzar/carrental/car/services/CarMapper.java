package org.luzar.carrental.car.services;

import org.luzar.carrental.car.models.Car;
import org.luzar.carrental.car.models.dto.CarDto;
import org.luzar.carrental.car.models.dto.CarResponseDto;
import org.springframework.stereotype.Service;

@Service
public class CarMapper {

    public Car dtoIntoCar(CarDto dto) {
        if(dto == null) throw new NullPointerException("Car Dto cannot be null!");

        return Car.builder()
                .brand(dto.brand())
                .model(dto.model())
                .manufactureYear(dto.manufactureYear())
                .color(dto.color())
                .mileage(dto.mileage())
                .licensePlate(dto.licensePlate())
                .rentable(dto.rentable())
                .priceCategory(dto.priceCategory())
                .build();
    }
    public CarResponseDto carIntoResponseDto(Car car) {
        return new CarResponseDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getManufactureYear(),
                car.getColor(),
                car.getMileage(),
                car.getLicensePlate(),
                car.isRentable(),
                car.getPriceCategory()
        );
    }
}
