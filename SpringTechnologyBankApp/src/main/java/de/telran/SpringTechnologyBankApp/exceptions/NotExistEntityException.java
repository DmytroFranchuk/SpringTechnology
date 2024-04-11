package de.telran.SpringTechnologyBankApp.exceptions;

public class NotExistEntityException extends RuntimeException {
    public NotExistEntityException(String messageError) {
        super(messageError);
    }
}
