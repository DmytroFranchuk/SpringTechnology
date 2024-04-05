package de.telran.SpringTechnologyBankApp.controllers.handler;

import de.telran.SpringTechnologyBankApp.dtos.errorData.ErrorResponseDto;
import de.telran.SpringTechnologyBankApp.exceptions.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundManagerException(@NotNull NotFoundEntityException exception) {
        ErrorResponseDto errorData = new ErrorResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotCreationEntityException.class)
    public ResponseEntity<ErrorResponseDto> handleNotCreationEntityException(@NotNull NotCreationEntityException exception) {
        ErrorResponseDto errorData = new ErrorResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotUpdatedEntityException.class)
    public ResponseEntity<ErrorResponseDto> handleNotUpdatedEntityException(@NotNull NotUpdatedEntityException exception) {
        ErrorResponseDto errorData = new ErrorResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotDeletionEntityException.class)
    public ResponseEntity<ErrorResponseDto> handleNotDeletionEntityException(@NotNull NotDeletionEntityException exception) {
        ErrorResponseDto errorData = new ErrorResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotActiveEntityException.class)
    public ResponseEntity<ErrorResponseDto> handleNotActiveEntityException(@NotNull NotActiveEntityException exception) {
        ErrorResponseDto errorData = new ErrorResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND);
    }
}
