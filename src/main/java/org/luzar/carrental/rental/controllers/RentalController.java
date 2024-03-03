package org.luzar.carrental.rental.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.luzar.carrental.globalexceptionhandling.dto.ErrorDto;
import org.luzar.carrental.rental.models.dto.RentalDto;
import org.luzar.carrental.rental.models.dto.RentalResponseDto;
import org.luzar.carrental.rental.services.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "Rental", description = "Rental management Api")
public class RentalController {
    public static final String RENTALURI = "/rental";

    private RentalService rentalService;

    @Operation(
            summary = "Add new rental",
            description = "Add new rental into rental database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = RentalResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Errors resulting from incorrect input parameters (catched by @Valid constrains)",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "Custom errors resulting from incorrect input parameters (e.g. wrong IDs, etc)",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "All other errors for simplification",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            )

    })
    @PostMapping(
            value = RENTALURI,
            produces = "application/json"
    )
    public ResponseEntity<RentalResponseDto> save(
            @Valid @RequestBody RentalDto dto
    ) {
        return new ResponseEntity<>(rentalService.save(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update rental",
            description = "Set rentedToDate field or override rentPrice in rental database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = RentalResponseDto.class))
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
            value = RENTALURI + "/{id}",
            produces = "application/json"
    )
    public ResponseEntity<RentalResponseDto> update(
            @PathVariable Long id,
            @RequestParam(value = "rentedToDate", required = false) LocalDate rentedToDate,
            @PositiveOrZero
            @RequestParam(value = "rentPrice", required = false) Integer rentPrice
    ) {
        return new ResponseEntity<>(rentalService.update(id, rentedToDate, rentPrice), HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch single rental by ID",
            description = "Fetches selected rental from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = RentalResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "empty body -> no rental found with set ID",
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
            value = RENTALURI + "/{id}",
            produces = "application/json"
    )
    public ResponseEntity<?> findById(
            @PathVariable Long id
    ) {
        RentalResponseDto responseDto = rentalService.findById(id);
        HttpStatus httpStatus = responseDto == null? HttpStatus.NO_CONTENT : HttpStatus.OK;

        return new ResponseEntity<>(responseDto, httpStatus);
    }


    @Operation(
            summary = "Fetch all rentals",
            description = "Fetches all rentals from customer database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = RentalResponseDto.class))
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
            value = RENTALURI,
            produces = "application/json"
    )
    public ResponseEntity<List<RentalResponseDto>> findAll() {
        return new ResponseEntity<>(rentalService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete rental by ID",
            description = "Deleting selected rental from rental database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation returns deleted customer",
                    content = @Content(schema = @Schema(implementation = RentalResponseDto.class))
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
            value = RENTALURI + "/{id}",
            produces = "application/json"
    )
    public ResponseEntity<RentalResponseDto> deleteById(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(rentalService.deleteById(id), HttpStatus.OK);
    }

}
