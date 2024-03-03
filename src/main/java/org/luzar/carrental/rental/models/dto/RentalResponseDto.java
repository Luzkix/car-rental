package org.luzar.carrental.rental.models.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RentalResponseDto(
        Long id,
        LocalDateTime createdAt,
        LocalDate rentedFromDate,
        LocalDate rentedToDate,
        int rentPrice,
        Long carId,
        Long customerId
) {
}
