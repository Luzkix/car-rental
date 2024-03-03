package org.luzar.carrental.customer.services;

import org.luzar.carrental.customer.models.Customer;
import org.luzar.carrental.customer.models.dto.CustomerDto;
import org.luzar.carrental.customer.models.dto.CustomerRentalResponseDto;
import org.luzar.carrental.customer.models.dto.CustomerResponseDto;
import org.luzar.carrental.customer.repositories.CustomerRepository;
import org.luzar.carrental.globalexceptionhandling.customexceptions.CustomExceptionChildBirthDate;
import org.luzar.carrental.globalexceptionhandling.customexceptions.CustomExceptionEntityNotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerResponseDto save(CustomerDto dto) {
        if(dto.birthDate().isAfter(LocalDate.now().minus(Period.ofYears(18)))) throw new CustomExceptionChildBirthDate("Customer must be at least 18 years old!");

        return customerMapper.customerIntoResponseDto(
                customerRepository.save(customerMapper.dtoIntoCustomer(dto))
        );
    }

    @Override
    public List<CustomerResponseDto> findAll() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerIntoResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDto findById(Long id) {
        return customerMapper.customerIntoResponseDto(
                customerRepository.findById(id).orElse(null)
        );
    }

    @Override
    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public CustomerResponseDto update(Long id, CustomerDto dto) throws CustomExceptionEntityNotFound {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) throw new CustomExceptionEntityNotFound("Customer with ID " + id + " is not in DB, thus cannot be updated!");

        BeanUtils.copyProperties(dto, customer);

        return customerMapper.customerIntoResponseDto(
                customerRepository.save(customer)
        );
    }

    @Override
    public CustomerResponseDto deleteById(Long id) throws CustomExceptionEntityNotFound {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) throw new CustomExceptionEntityNotFound("Customer with ID " + id + " is not in DB, thus cannot be deleted!");

        customerRepository.delete(customer);

        return customerMapper.customerIntoResponseDto(customer);
    }

    @Override
    public List<CustomerRentalResponseDto> findRentalsByCustomerId(Long id) throws CustomExceptionEntityNotFound {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) throw new CustomExceptionEntityNotFound("Customer with ID " + id + " is not in DB, thus cannot be queried for rentals!");

        return customer.getRentals().stream()
                .map(customerMapper::rentalToCustomerRentalResponseDto)
                .collect(Collectors.toList());
    }
}
