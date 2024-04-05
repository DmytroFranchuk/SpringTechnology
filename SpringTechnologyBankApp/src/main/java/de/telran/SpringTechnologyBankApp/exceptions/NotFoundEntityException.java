package de.telran.SpringTechnologyBankApp.exceptions;

public class NotFoundEntityException extends RuntimeException {

    public NotFoundEntityException(String messageError) {
        super(messageError);
    }

}
