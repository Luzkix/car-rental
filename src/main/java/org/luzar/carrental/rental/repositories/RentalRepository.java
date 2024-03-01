package org.luzar.carrental.rental.repositories;

import org.luzar.carrental.rental.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental,Long> {

}
