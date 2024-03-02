package org.luzar.carrental.car.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.luzar.carrental.car.models.Car;
import org.luzar.carrental.car.models.dto.CarDto;
import org.luzar.carrental.car.models.dto.CarResponseDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CarMapperTest {
    private CarMapper carMapper;


    @BeforeEach
    void setUp() {
        carMapper = new CarMapper();
    }

    @Test
    void mapCarDtoIntoCarObject_ShouldReturnCorrectObject() {
        //Given
        CarDto dto = new CarDto(
                "skoda",
                "octavia",
                LocalDate.parse("2020-01-01"),
                "red",
                50,
                "A000B00",
                true,
                1
        );

        //When
        Car car = carMapper.dtoIntoCar(dto);

        //Then
        assertEquals(dto.brand(),car.getBrand());
        assertEquals(dto.model(),car.getModel());
        assertEquals(dto.manufactureYear(),car.getManufactureYear());
        assertEquals(dto.color(),car.getColor());
        assertEquals(dto.mileage(),car.getMileage());
        assertEquals(dto.licensePlate(),car.getLicensePlate());
        assertEquals(dto.rentable(),car.isRentable());
        assertEquals(dto.priceCategory(),car.getPriceCategory());

        assertFalse(car.isDiscarded());
        assertNull(car.getDiscardedDate());
        assertNull(car.getRentals());
    }

    @Test
    void mapNullCarDtoIntoCarObject_shouldThrowNullPointerException() {
        //checking both throwing correct type of exception and its message
        var exception = assertThrows(NullPointerException.class,() -> carMapper.dtoIntoCar(null));
        assertEquals("Car Dto cannot be null!",exception.getMessage());
    }

    @Test
    void mapCarObjectIntoCarResponseDto_ShouldReturnCorrectDto() {
        //Given
        Car car = new Car(
                1L,
                "skoda",
                "octavia",
                LocalDate.parse("2020-01-01"),
                "red",
                50,
                "A000B00",
                true,
                1,
                true,
                LocalDate.parse("2024-03-03"),
                null
        );

        //When
        CarResponseDto response = carMapper.carIntoResponseDto(car);

        //Then
        assertEquals(response.id(),car.getId());
        assertEquals(response.brand(),car.getBrand());
        assertEquals(response.model(),car.getModel());
        assertEquals(response.manufactureYear(),car.getManufactureYear());
        assertEquals(response.color(),car.getColor());
        assertEquals(response.mileage(),car.getMileage());
        assertEquals(response.licensePlate(),car.getLicensePlate());
        assertEquals(response.rentable(),car.isRentable());
        assertEquals(response.priceCategory(),car.getPriceCategory());
        assertEquals(response.discarded(),car.isDiscarded());
        assertEquals(response.discardedDate(),car.getDiscardedDate());
    }

    @Test
    void mapNullCarObjectIntoCarResponseDto_ShouldReturnNullDto() {
        //Given
        Car car = null;

        //When
        CarResponseDto response = carMapper.carIntoResponseDto(car);

        //Then
        assertNull(response);
    }
}