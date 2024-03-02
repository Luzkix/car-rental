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
import org.luzar.carrental.globalexceptionhandling.dto.ErrorDto;
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
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Errors resulting from incorrect input parameters (catched by @Valid constrains)",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "All other errors for simplification",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
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
            summary = "Update car",
            description = "Update particular car in car database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = CarResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Errors resulting from incorrect input parameters (catched by @Valid constrains)",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "Custom errors resulting from incorrect input parameters (e.g. non existent entity in DB, etc)",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "All other errors for simplification",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            )
    })
    @PutMapping(
            value = "/car/{id}",
            produces = "application/json"
    )
    public ResponseEntity<CarResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody CarDto car
    ) {
        return new ResponseEntity<>(carService.update(id,car), HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch single car by ID",
            description = "Feetches selected car from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = CarResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "empty body -> no car found with set ID",
                    content = @Content(schema = @Schema())
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Errors resulting from incorrect input parameters (catched by @Valid constrains)",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "All other errors for simplification",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            )
    })
    @GetMapping(
            value = "/car/{id}",
            produces = "application/json"
    )
    public ResponseEntity<?> findById(
            @PathVariable Long id
    ) {
        CarResponseDto responseDto = carService.findById(id);
        HttpStatus httpStatus = responseDto == null? HttpStatus.NO_CONTENT : HttpStatus.OK;

        return new ResponseEntity<>(responseDto, httpStatus);
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
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Errors resulting from incorrect input parameters (catched by @Valid constrains)",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "All other errors for simplification",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            )
    })
    @GetMapping(
            value = "/car",
            produces = "application/json"
    )
    public ResponseEntity<List<CarResponseDto>> findAll() {
        return new ResponseEntity<>(carService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Discard car by ID",
            description = "Marking selected car as 'Discareded' in car database (substitutes real deletion)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = CarResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Errors resulting from incorrect input parameters (catched by @Valid constrains)",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "Custom errors resulting from incorrect input parameters (e.g. non existent entity in DB, etc)",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "All other errors for simplification",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            )
    })
    @DeleteMapping(
            value = "/car/{id}",
            produces = "application/json"
    )
    public ResponseEntity<CarResponseDto> deleteById(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(carService.deleteById(id), HttpStatus.OK);
    }

}
