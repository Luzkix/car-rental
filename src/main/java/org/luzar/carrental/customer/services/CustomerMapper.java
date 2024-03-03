package org.luzar.carrental.customer.services;

import org.luzar.carrental.customer.models.Customer;
import org.luzar.carrental.customer.models.dto.CustomerDto;
import org.luzar.carrental.customer.models.dto.CustomerRentalResponseDto;
import org.luzar.carrental.customer.models.dto.CustomerResponseDto;
import org.luzar.carrental.rental.models.Rental;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer dtoIntoCustomer(CustomerDto dto) {
        if(dto == null) throw new NullPointerException("Customer Dto cannot be null!");

        return Customer.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .birthDate(dto.birthDate())
                .address(dto.address())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .build();
    }
    public CustomerResponseDto customerIntoResponseDto(Customer customer) {
        if (customer == null) { //e.g. if there is null output from DB / customer not found
            return null;
        } else return new CustomerResponseDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getBirthDate(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }

    public CustomerRentalResponseDto rentalToCustomerRentalResponseDto(Rental rental) {
        return new CustomerRentalResponseDto(
                rental.getCar().getId(),
                rental.getCar().getBrand(),
                rental.getCar().getModel(),
                rental.getId(),
                rental.getRentedFromDate(),
                rental.getRentedToDate(),
                rental.getRentPrice()
        );
    }
}
