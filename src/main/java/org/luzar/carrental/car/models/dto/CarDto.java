package org.luzar.carrental.car.models.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CarDto(

        @NotBlank(message = "Brand must not be blank!")
        String brand,

        @NotBlank(message = "Model must not be blank!")
        String model,

        //@NotBlank(message = "Model must not be blank!")
        @NotNull
        @PastOrPresent(message = "Manufacture year cant be higher than actual year!")
        LocalDate manufactureYear,

        @NotBlank(message = "Color must not be blank!")
        String color,

        @PositiveOrZero(message = "Mileage must be at least 0!")
        int mileage,

        @Size(max = 7, message = "License plate can have maximum 7 characters")
        String licensePlate,

        @NotNull
        @Min(value = 1, message = "Price category must be between 1 and 3")
        @Max(value = 3, message = "Price category must be between 1 and 3")
        int priceCategory
) {
}
