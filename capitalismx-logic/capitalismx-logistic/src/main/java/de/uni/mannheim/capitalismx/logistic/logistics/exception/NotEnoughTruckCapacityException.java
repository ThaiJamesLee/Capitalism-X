package de.uni.mannheim.capitalismx.logistic.logistics.exception;

/**
 * Exception that is thrown if there is not enough capacity for additional trucks in the internal fleet.
 *
 * @author sdupper
 */
public class NotEnoughTruckCapacityException extends Exception {

    public NotEnoughTruckCapacityException(String message) {
        super(message);
    }
}
