package de.telran.SpringTechnologyBankApp.services.utilities;

import java.util.function.Consumer;

public class Utils {
    private Utils() {
    }

    public static <T> void updateFieldIfNotNull(T newValue, Consumer<T> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }
}
