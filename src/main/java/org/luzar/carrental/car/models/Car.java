package org.luzar.carrental.car.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String brand;

    @Column
    @NotNull
    private String model;

    @Column
    private LocalDate manufactureYear;

    @Column
    private String color;

    @Column
    private int mileage;

    @Column
    private String licensePlate;

    @Column
    private int priceCategory;

    @Column
    private boolean discarded;

    @Column
    private LocalDate discardedDate;


    @OneToMany(mappedBy = "car")
    @JsonManagedReference
    private List<Rental> rentals;


}
