package org.luzar.carrental.customer.services;

import org.luzar.carrental.customer.models.dto.CustomerDto;
import org.luzar.carrental.customer.models.dto.CustomerRentalResponseDto;
import org.luzar.carrental.customer.models.dto.CustomerResponseDto;
import org.luzar.carrental.globalexceptionhandling.customexceptions.CustomExceptionChildBirthDate;
import org.luzar.carrental.globalexceptionhandling.customexceptions.CustomExceptionEntityNotFound;

import java.util.List;

public interface CustomerService {

    CustomerResponseDto save(CustomerDto dto) throws CustomExceptionChildBirthDate;

    List<CustomerResponseDto> findAll();

    CustomerResponseDto findById(Long id);

    CustomerResponseDto update(Long id, CustomerDto dto) throws CustomExceptionEntityNotFound;

    CustomerResponseDto deleteById(Long id) throws CustomExceptionEntityNotFound;

    List<CustomerRentalResponseDto> findRentalsByCustomerId(Long id) throws CustomExceptionEntityNotFound;
}

