package de.uni.mannheim.capitalismx.production.exceptions;

import de.uni.mannheim.capitalismx.procurement.component.Component;

import java.util.List;

/**
 * The type Component locked exception.
 *
 * @author dzhao
 */
public class ComponentLockedException extends Exception{

    /**
     * Instantiates a new Component locked exception.
     *
     * @param message the message
     */
    public ComponentLockedException(String message) {
        super(message);
    }
}
