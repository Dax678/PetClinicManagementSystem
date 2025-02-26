package org.example.petclinicmanagementsystem.Configuration.Exception;

import java.text.MessageFormat;

public class ElementAlreadyExistsException extends RuntimeException {
    public ElementAlreadyExistsException(String message) {
        super(message);
    }

    public ElementAlreadyExistsException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }
}
