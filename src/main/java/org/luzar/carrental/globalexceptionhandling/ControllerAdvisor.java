package org.luzar.carrental.globalexceptionhandling;


import lombok.extern.slf4j.Slf4j;
import org.luzar.carrental.globalexceptionhandling.dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@Slf4j
@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @Override //custom handling of @Valid errors (better
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName,errorMessage);
                    log.error(fieldName + " : " + errorMessage);
                });

        return new ResponseEntity<>(new ErrorDto(httpStatus.value(), errors.toString()),httpStatus);
    }
}
