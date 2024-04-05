package de.telran.SpringTechnologyBankApp.exceptions;

public class NotUpdatedEntityException extends RuntimeException {
    public NotUpdatedEntityException(String messageError) {
        super(messageError);
    }
}
