package de.telran.SpringTechnologyBankApp.dtos.errorData;

import lombok.Value;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Value
public class ErrorResponseDto {
    String messageError;
    HttpStatus httpStatus;
    Timestamp timeError;

    public ErrorResponseDto(String messageError, HttpStatus httpStatus) {
        this.messageError = messageError;
        this.httpStatus = httpStatus;
        this.timeError = new Timestamp(System.currentTimeMillis());
    }
}
