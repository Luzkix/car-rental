package org.luzar.carrental.rental.services;

import org.luzar.carrental.rental.repositories.RentalRepository;
import org.springframework.stereotype.Service;

@Service
public class RentalServiceImpl implements RentalService{
    private final RentalRepository rentalRepository;

    public RentalServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }



}
