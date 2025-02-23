package org.example.petclinicmanagementsystem.Configuration.Exception;

import lombok.Getter;

import java.text.MessageFormat;

@Getter
public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String message) {
        super(message);
    }

    public ElementNotFoundException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }
}
