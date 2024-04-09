package de.telran.SpringTechnologyBankApp.controllers.handler;

import de.telran.SpringTechnologyBankApp.dtos.errorData.ErrorResponseDto;
import de.telran.SpringTechnologyBankApp.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(@NotNull ConstraintViolationException exception) {
        ErrorResponseDto errorData = new ErrorResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(@NotNull HttpMessageNotReadableException exception) {
        ErrorResponseDto errorData = new ErrorResponseDto(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolationException(@NotNull DataIntegrityViolationException exception) {
        String errorMessage = exception.getLocalizedMessage().replaceAll("\\\"", "");
        Pattern pattern = Pattern.compile("Подробности:(.*?)\\]");
        Matcher matcher = pattern.matcher(errorMessage);
        String details = "";
        if (matcher.find()) {
            details = matcher.group(1).trim();
        }
        ErrorResponseDto errorData = new ErrorResponseDto(details, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(@NotNull MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        BindingResult result = ex.getBindingResult();
        for (FieldError fieldError : result.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(@NotNull MethodArgumentNotValidException exception) {
//        BindingResult result = exception.getBindingResult();
//        FieldError error = result.getFieldError();
//        String errorMessage = error != null ? error.getDefaultMessage() : "Validation error";
//        ErrorResponseDto errorData = new ErrorResponseDto(errorMessage, HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(errorData, HttpStatus.BAD_REQUEST);
//    }
