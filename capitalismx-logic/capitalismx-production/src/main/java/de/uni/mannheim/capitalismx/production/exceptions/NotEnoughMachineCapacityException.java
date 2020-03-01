package de.uni.mannheim.capitalismx.production.exceptions;

/**
 * The type Not enough machine capacity exception.
 *
 * @author dzhao
 */
public class NotEnoughMachineCapacityException extends Exception {

    private int machineCapacity;

    /**
     * Instantiates a new Not enough machine capacity exception.
     *
     * @param message         the message
     * @param machineCapacity the machine capacity
     */
    public NotEnoughMachineCapacityException(String message, int machineCapacity) {
        super(message);
        this.machineCapacity = machineCapacity;
    }

    /**
     * Gets machine capacity.
     *
     * @return the machine capacity
     */
    public int getMachineCapacity() {
        return this.machineCapacity;
    }
}
