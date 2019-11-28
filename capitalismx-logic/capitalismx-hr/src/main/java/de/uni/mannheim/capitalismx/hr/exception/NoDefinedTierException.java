package de.uni.mannheim.capitalismx.hr.exception;

/**
 * Use this exception to throw when a value is not in the predefined ranges.
 * @author duly
 */
public class NoDefinedTierException extends Exception {

    public NoDefinedTierException(String message){
        super(message);
    }
}
