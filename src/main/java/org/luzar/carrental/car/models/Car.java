package org.luzar.carrental.car.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.luzar.carrental.rental.models.Rental;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private LocalDate manufactureYear;

    @Column
    private String color;

    @Column
    private int mileage = 0;

    @Column
    private String licensePlate;

    @Column
    private boolean rentable = true;

    @Column
    private int priceCategory;

    @Column
    private boolean discarded = false;

    @Column
    private LocalDate discardedDate;


    @OneToMany(mappedBy = "car")
    @JsonManagedReference
    private List<Rental> rentals;


}
