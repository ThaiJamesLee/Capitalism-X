package de.uni.mannheim.capitalismx.production.exceptions;

/**
 * The type Not enough free storage exception.
 *
 * @author dzhao
 */
public class NotEnoughFreeStorageException extends Exception {

    private int freeStorage;

    /**
     * Instantiates a new Not enough free storage exception.
     *
     * @param message     the message
     * @param freeStorage the free storage
     */
    public NotEnoughFreeStorageException(String message, int freeStorage) {
        super(message);
        this.freeStorage = freeStorage;
    }

    /**
     * Gets free storage.
     *
     * @return the free storage
     */
    public int getFreeStorage() {
        return this.freeStorage;
    }
}
