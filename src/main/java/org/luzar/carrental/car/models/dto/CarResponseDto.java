package org.luzar.carrental.car.models.dto;

import java.time.LocalDate;

public record CarResponseDto(
        Long id,

        String brand,

        String model,

        LocalDate manufactureYear,

        String color,

        int mileage,

        String licensePlate,

        boolean rentable,

        int priceCategory,

        boolean discarded,

        LocalDate discardedDate
) {
}
