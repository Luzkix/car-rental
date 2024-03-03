package org.luzar.carrental.rental.services;

import org.luzar.carrental.globalexceptionhandling.customexceptions.CustomExceptionAllInputParametersNull;
import org.luzar.carrental.globalexceptionhandling.customexceptions.CustomExceptionEntityNotFound;
import org.luzar.carrental.rental.models.dto.RentalDto;
import org.luzar.carrental.rental.models.dto.RentalResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface RentalService {

    RentalResponseDto save(RentalDto dto) throws CustomExceptionEntityNotFound;

    RentalResponseDto update(Long id, LocalDate rentedToDate, Integer rentPrice)
            throws CustomExceptionEntityNotFound, CustomExceptionAllInputParametersNull;

    RentalResponseDto findById(Long id);

    List<RentalResponseDto> findAll();

    RentalResponseDto deleteById(Long id);
}
