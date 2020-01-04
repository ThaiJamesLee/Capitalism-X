package de.uni.mannheim.capitalismx.domain.exception;

/**
 *
 * This Exception is called when there are inconsistencies in leveling definition.
 * @author duly
 *
 * @since 1.0.0
 */
public class InconsistentLevelException extends Exception {

    public InconsistentLevelException (String message) {
        super(message);
    }
}
