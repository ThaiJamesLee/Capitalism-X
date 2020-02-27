package de.uni.mannheim.capitalismx.logistic.support.exception;


/**
 * Exception that is thrown if no external support partner is hired and the user tries to provide product support.
 *
 * @author sdupper
 */
public class NoExternalSupportPartnerException extends Exception {

    public NoExternalSupportPartnerException(String message) {
        super(message);
    }
}
