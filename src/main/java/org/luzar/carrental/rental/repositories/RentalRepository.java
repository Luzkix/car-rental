package org.luzar.carrental.rental.repositories;

import org.luzar.carrental.rental.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Long> {

    List<Rental> findAllByRentedToDateNull();
}
