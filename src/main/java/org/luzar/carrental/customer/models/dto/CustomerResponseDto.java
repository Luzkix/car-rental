package org.luzar.carrental.customer.models.dto;

import java.time.LocalDate;

public record CustomerResponseDto(
        Long id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String address,
        String email,
        String phoneNumber
) {
}
