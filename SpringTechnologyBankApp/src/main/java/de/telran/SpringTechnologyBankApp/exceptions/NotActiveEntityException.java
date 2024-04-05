package de.telran.SpringTechnologyBankApp.exceptions;

public class NotActiveEntityException extends RuntimeException {
    public NotActiveEntityException(String messageError) {
        super(messageError);
    }
}
