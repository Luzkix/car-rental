package org.luzar.carrental.rental.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.luzar.carrental.car.models.Car;
import org.luzar.carrental.customer.models.Customer;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Rental {

    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDate rentedFromDate;

    @Column
    private LocalDate rentedToDate;

    @Column
    private boolean activeRental;

    @Column
    private int rentPrice;


    @ManyToOne
    @JoinColumn(
            name = "car_id"
    )
    @JsonBackReference
    private Car car;

    @ManyToOne
    @JoinColumn(
            name = "customer_id"
    )
    @JsonBackReference
    private Customer customer;
}
