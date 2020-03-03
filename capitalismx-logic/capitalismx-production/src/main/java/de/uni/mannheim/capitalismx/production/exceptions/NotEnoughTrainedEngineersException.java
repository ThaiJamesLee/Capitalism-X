package de.uni.mannheim.capitalismx.production.exceptions;

/**
 * Not enough trained engineers exception.
 * Used if not enough trained engineers hired when investing into process automation.
 *
 * @author dzhao
 */
public class NotEnoughTrainedEngineersException extends Exception {

    private String message;
    private int numberOfNeededTrainedEngineers;
    private int numberOfTrainedEngineers;

    /**
     * Constructor of the not enough trained engineers exception.
     *
     * @param message
     * @param numberOfNeededTrainedEngineers
     * @param numberOfTrainedEngineers
     */
    public NotEnoughTrainedEngineersException(String message, int numberOfNeededTrainedEngineers, int numberOfTrainedEngineers) {
        super(message);
        this.numberOfNeededTrainedEngineers = numberOfNeededTrainedEngineers;
        this.numberOfTrainedEngineers = numberOfTrainedEngineers;
    }

    /**
     * Gets number of needed trained engineers.
     *
     * @return the number of needed trained engineers
     */
    public int getNumberOfNeededTrainedEngineers() {
        return this.numberOfNeededTrainedEngineers;
    }

    /**
     * Gets number of trained engineers.
     *
     * @return the number of trained engineers
     */
    public int getNumberOfTrainedEngineers() {
        return this.numberOfTrainedEngineers;
    }
}
