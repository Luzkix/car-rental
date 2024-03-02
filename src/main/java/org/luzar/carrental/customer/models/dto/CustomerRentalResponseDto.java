package org.luzar.carrental.customer.models.dto;

import java.time.LocalDate;

public record CustomerRentalResponseDto(
        Long carId,
        String brand,
        String model,
        LocalDate rentedFromDate,
        LocalDate rentedToDate,
        int rentPrice
) {
}
