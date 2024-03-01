package org.luzar.carrental.car.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.luzar.carrental.car.models.dto.CarDto;
import org.luzar.carrental.car.models.dto.CarResponseDto;
import org.luzar.carrental.car.services.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "Car", description = "Car management Api")
public class CarController {

    private final CarService carService;

    @Operation(
            summary = "Add new car",
            description = "Add new car into car database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = CarResponseDto.class))
            )
    })
    @PostMapping(
            value = "/car",
            produces = "application/json"
    )
    public ResponseEntity<CarResponseDto> save(
            @Valid @RequestBody CarDto car
    ) {
        return new ResponseEntity<>(carService.save(car), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Fetch all cars",
            description = "Feetches all cars from car database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = CarResponseDto.class))
            )
    })
    @GetMapping(
            value = "/cars",
            produces = "application/json"
    )
    public ResponseEntity<List<CarResponseDto>> findAll() {
        return new ResponseEntity<>(carService.findAll(), HttpStatus.OK);
    }

}
