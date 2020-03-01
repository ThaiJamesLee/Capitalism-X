package de.uni.mannheim.capitalismx.production.exceptions;

/**
 * The type Not enough components exception.
 *
 * @author dzhao
 */
public class NotEnoughComponentsException extends Exception {

    private int maxmimumProducable;

    /**
     * Instantiates a new Not enough components exception.
     *
     * @param message            the message
     * @param maxmimumProducable the maxmimum producable
     */
    public NotEnoughComponentsException(String message, int maxmimumProducable) {
        super(message);
        this.maxmimumProducable = maxmimumProducable;
    }

    /**
     * Gets maxmimum producable.
     *
     * @return the maxmimum producable
     */
    public int getMaxmimumProducable() {
        return this.maxmimumProducable;
    }
}
