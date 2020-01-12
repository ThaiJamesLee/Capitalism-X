package de.uni.mannheim.capitalismx.production;

public class NotEnoughMachineCapacityException extends Exception {

    private int machineCapacity;

    public NotEnoughMachineCapacityException(String message, int machineCapacity) {
        super(message);
        this.machineCapacity = machineCapacity;
    }

    public int getMachineCapacity() {
        return this.machineCapacity;
    }
}
