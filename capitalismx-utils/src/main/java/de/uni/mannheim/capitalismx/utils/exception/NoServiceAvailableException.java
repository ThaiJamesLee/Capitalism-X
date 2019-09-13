package de.uni.mannheim.capitalismx.utils.exception;

import java.io.IOException;

/**
 * This service should be thrown when no service is available. For instance, no rest service and also no alternative.
 * @author duly
 */
public class NoServiceAvailableException extends IOException {

    public NoServiceAvailableException(String message) {
        super(message);
    }
}
