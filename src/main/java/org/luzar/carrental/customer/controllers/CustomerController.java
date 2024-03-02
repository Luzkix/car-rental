package org.luzar.carrental.customer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.luzar.carrental.car.models.dto.CarResponseDto;
import org.luzar.carrental.customer.models.dto.CustomerDto;
import org.luzar.carrental.customer.models.dto.CustomerRentalResponseDto;
import org.luzar.carrental.customer.models.dto.CustomerResponseDto;
import org.luzar.carrental.customer.services.CustomerService;
import org.luzar.carrental.globalexceptionhandling.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "Customer", description = "Customer management Api")
public class CustomerController {
    public static final String CUSTOMERURI = "/customer";

    private CustomerService customerService;

    @Operation(
            summary = "Add new customer",
            description = "Add new customer into customer database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = CustomerResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Errors resulting from incorrect input parameters (catched by @Valid constrains)",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "Custom errors resulting from incorrect input parameters (e.g. child age of customer, etc)",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "All other errors for simplification",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))
            )

    })
    @PostMapping(
            value = CUSTOMERURI,
            produces = "application/json"
    )
    public ResponseEntity<CustomerResponseDto> save(
            @Valid @RequestBody CustomerDto dto
    ) {
        return new ResponseEntity<>(customerService.save(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update customer",
            description = "Update particular customer in customer database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = CustomerResponseDto.class))
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
            value = CUSTOMERURI + "/{id}",
            produces = "application/json"
    )
    public ResponseEntity<CustomerResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerDto dto
    ) {
        return new ResponseEntity<>(customerService.update(id,dto), HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch single customer by ID",
            description = "Fetches selected customer from database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = CustomerResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "empty body -> no customer found with set ID",
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
            value = CUSTOMERURI + "/{id}",
            produces = "application/json"
    )
    public ResponseEntity<?> findById(
            @PathVariable Long id
    ) {
        CustomerResponseDto responseDto = customerService.findById(id);
        HttpStatus httpStatus = responseDto == null? HttpStatus.NO_CONTENT : HttpStatus.OK;

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @Operation(
            summary = "Fetch all customers",
            description = "Fetches all customers from customer database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = CustomerResponseDto.class))
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
            value = CUSTOMERURI,
            produces = "application/json"
    )
    public ResponseEntity<List<CustomerResponseDto>> findAll() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete customer by ID",
            description = "Deleting selected customer from custromer database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation returns deleted customer",
                    content = @Content(schema = @Schema(implementation = CustomerResponseDto.class))
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
            value = CUSTOMERURI + "/{id}",
            produces = "application/json"
    )
    public ResponseEntity<CustomerResponseDto> deleteById(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(customerService.deleteById(id), HttpStatus.OK);
    }


    @Operation(
            summary = "All previously rented cars by specified customer",
            description = "Fetches all previously rented cars for specified customer ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = CustomerRentalResponseDto.class))
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
    @GetMapping(
            value = CUSTOMERURI + "/{id}/rentals",
            produces = "application/json"
    )
    public ResponseEntity<List<CustomerRentalResponseDto>> findRentalsByCustomerId(
            @PathVariable Long id
    ) {
        return new ResponseEntity<List<CustomerRentalResponseDto>>(customerService.findRentalsByCustomerId(id), HttpStatus.OK);
    }

}
