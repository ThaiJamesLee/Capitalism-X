package de.uni.mannheim.capitalismx.production;

public class NotEnoughComponentsException extends Exception {

    private int maxmimumProducable;

    public NotEnoughComponentsException(String message, int maxmimumProducable) {
        super(message);
        this.maxmimumProducable = maxmimumProducable;
    }

    public int getMaxmimumProducable() {
        return this.maxmimumProducable;
    }
}
