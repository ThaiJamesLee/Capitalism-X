package de.uni.mannheim.capitalismx.production;

public class NotEnoughFreeStorageException extends Exception {

    private int freeStorage;

    public NotEnoughFreeStorageException(String message, int freeStorage) {
        super(message);
        this.freeStorage = freeStorage;
    }

    public int getFreeStorage() {
        return this.freeStorage;
    }
}
