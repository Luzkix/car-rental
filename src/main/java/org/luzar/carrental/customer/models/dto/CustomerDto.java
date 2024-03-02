package org.luzar.carrental.customer.models.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CustomerDto(
        @NotBlank(message = "FirstName must not be blank!")
        String firstName,

        @NotBlank(message = "LastName must not be blank!")
        String lastName,

        @NotNull
        @PastOrPresent(message = "BirthDate year cant be higher than actual year!")
        LocalDate birthDate,

        @NotBlank(message = "Address must not be blank!")
        String address,

        @Email
        String email,

        @Size(min = 9, message = "Phone number must have minimum 9 characters!")
        String phoneNumber
) {
}
