package org.luzar.carrental.customer.models;

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
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    private String firstName;

    @Column
    @NotNull
    private String lastName;

    @Column(updatable = false)
    @NotNull
    private LocalDate birthDate;

    @Column
    private String address;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column
    private String phoneNumber;


    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Rental> rentals;


}
