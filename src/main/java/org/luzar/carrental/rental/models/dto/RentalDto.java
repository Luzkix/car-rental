package org.luzar.carrental.rental.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record RentalDto(
        @NotNull
        LocalDate rentedFromDate,

        LocalDate rentedToDate,

        @NotNull
        @Positive
        Long carId,
        @NotNull
        @Positive
        Long customerId
) {
}
