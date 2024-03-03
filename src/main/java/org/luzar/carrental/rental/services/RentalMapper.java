package org.luzar.carrental.rental.services;

import org.luzar.carrental.rental.models.Rental;
import org.luzar.carrental.rental.models.dto.RentalResponseDto;
import org.springframework.stereotype.Service;

@Service
public class RentalMapper {

    public RentalResponseDto rentalIntoResponseDto(Rental rental) {
        if (rental == null) { //e.g. if there is null output from DB / rental not found
            return null;
        } else return new RentalResponseDto(
                rental.getId(),
                rental.getCreatedAt(),
                rental.getRentedFromDate(),
                rental.getRentedToDate(),
                rental.getRentPrice(),
                rental.getCar().getId(),
                rental.getCustomer().getId()
        );
    }

}
