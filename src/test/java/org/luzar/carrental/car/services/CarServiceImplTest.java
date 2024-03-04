package org.luzar.carrental.car.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.luzar.carrental.car.models.Car;
import org.luzar.carrental.car.models.dto.CarDto;
import org.luzar.carrental.car.models.dto.CarResponseDto;
import org.luzar.carrental.car.repositories.CarRepository;
import org.luzar.carrental.globalexceptionhandling.customexceptions.CustomExceptionEntityNotFound;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class CarServiceImplTest {
    //what service i want to test
    @InjectMocks //injects mocked components into studentService class
    private CarServiceImpl carService;

    //declare dependencies
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarMapper carMapper;

    //setup test objects
    private CarDto dto1;
    private Car car1;
    private Car savedCar1;
    private CarResponseDto responseDto1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //we want to open mocks for current class
        prepareTestObjects();
    }

    private void prepareTestObjects() {
        dto1 = new CarDto(
                "skoda",
                "octavia",
                LocalDate.parse("2020-01-01"),
                "blue",
                40,
                "1234567",
                1
        );

        car1 = new Car(
                null,
                "skoda",
                "octavia",
                LocalDate.parse("2020-01-01"),
                "blue",
                40,
                "1234567",
                1,
                false,
                null,
                null
        );

        savedCar1 = new Car(
                1L,
                "skoda",
                "octavia",
                LocalDate.parse("2020-01-01"),
                "blue",
                40,
                "1234567",
                1,
                false,
                null,
                null
        );

        responseDto1 = new CarResponseDto(
                1L,
                "skoda",
                "octavia",
                LocalDate.parse("2020-01-01"),
                "blue",
                40,
                "1234567",
                1,
                false,
                null
        );
    }

    @Test
    void save_returnsCorrectlyProcessedResponseDto() {
        //Given
            //data already prepared in prepareTestObjects() method

        //Mock all calls to external classes
        Mockito.when(carMapper.dtoIntoCar(dto1))
                .thenReturn(car1);
        Mockito.when(carRepository.save(car1))
                .thenReturn(savedCar1);
        Mockito.when(carMapper.carIntoResponseDto(savedCar1))
                .thenReturn(responseDto1);

        //When
        CarResponseDto responseDto = carService.save(dto1);

        //Then
        assertEquals(responseDto.id(),savedCar1.getId());
        assertEquals(responseDto.brand(),savedCar1.getBrand());
        assertEquals(responseDto.model(),savedCar1.getModel());
        assertEquals(responseDto.manufactureYear(),savedCar1.getManufactureYear());
        assertEquals(responseDto.mileage(),savedCar1.getMileage());

        Mockito.verify(carMapper,Mockito.times(1))
                .dtoIntoCar(dto1);
        Mockito.verify(carRepository,Mockito.times(1))
                .save(car1);
        Mockito.verify(carMapper,Mockito.times(1))
                .carIntoResponseDto(savedCar1);
    }


    @Test
    void update_returnsCorrectlyProcessedResponseDto() {
        //Given
        //data already prepared in prepareTestObjects() method
        Long id = 1L;

        CarDto dto = new CarDto(
                "skoda",
                "octavia",
                LocalDate.parse("2020-01-01"),
                "updated color yellow",
                40,
                "1234567",
                1
        );

        Car car = savedCar1;

        Car updatedCar = new Car();
        BeanUtils.copyProperties(car, updatedCar);
        updatedCar.setColor(dto.color());

        CarResponseDto response = new CarResponseDto(
                updatedCar.getId(),
                updatedCar.getBrand(),
                updatedCar.getModel(),
                updatedCar.getManufactureYear(),
                updatedCar.getColor(),
                updatedCar.getMileage(),
                updatedCar.getLicensePlate(),
                updatedCar.getPriceCategory(),
                updatedCar.isDiscarded(),
                updatedCar.getDiscardedDate()
        );


        //Mock all calls to external classes
        Mockito.when(carRepository.findById(id))
                .thenReturn(Optional.of(car));
        Mockito.when(carRepository.save(updatedCar))
                .thenReturn(updatedCar);
        Mockito.when(carMapper.carIntoResponseDto(updatedCar))
                .thenReturn(response);

        //When
        CarResponseDto responseDto = carService.update(id,dto);

        //Then
        assertEquals(responseDto.id(),updatedCar.getId());
        assertEquals(responseDto.brand(),updatedCar.getBrand());
        assertEquals(responseDto.model(),updatedCar.getModel());
        assertEquals(responseDto.manufactureYear(),updatedCar.getManufactureYear());
        assertEquals(responseDto.mileage(),updatedCar.getMileage());

        Mockito.verify(carRepository,Mockito.times(1))
                .save(any());
    }


    @Test
    void update_discardedCarThrowsCustomExceptionEntityNotFound() {
        Long id = 20L;
        car1.setDiscarded(true);

        Mockito.when(carRepository.findById(id))
                .thenReturn(Optional.of(car1));

        //checking both throwing correct type of exception and its message
        var exception = assertThrows(CustomExceptionEntityNotFound.class,() -> carService.update(id,dto1));
        assertEquals("Car with ID " + id + " is not in DB / is discarded, thus cannot be updated!",exception.getMessage());
    }
}