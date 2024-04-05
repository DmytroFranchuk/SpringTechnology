package de.telran.SpringTechnologyBankApp.exceptions;

public class NotCreationEntityException extends RuntimeException {
    public NotCreationEntityException(String messageError) {
        super(messageError);
    }
}
