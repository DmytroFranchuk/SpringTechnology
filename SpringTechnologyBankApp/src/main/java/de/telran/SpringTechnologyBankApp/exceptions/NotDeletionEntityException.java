package de.telran.SpringTechnologyBankApp.exceptions;

public class NotDeletionEntityException extends RuntimeException{
    public NotDeletionEntityException(String messageError) {
        super(messageError);
    }
}
