package de.telran.SpringTechnologyBankApp.exceptions;

public class NotValidTransactionException extends RuntimeException {
    public NotValidTransactionException(String messageError) {
        super(messageError);
    }
}
