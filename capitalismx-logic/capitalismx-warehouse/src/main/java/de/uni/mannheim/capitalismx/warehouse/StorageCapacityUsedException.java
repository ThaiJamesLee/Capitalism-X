package de.uni.mannheim.capitalismx.warehouse;

public class StorageCapacityUsedException extends Exception {

    public int freeStorage;
    public int warehouseCapacity;

    public StorageCapacityUsedException(String message, int freeStorage, int warehouseCapacity) {
        super(message);
        this.freeStorage = freeStorage;
        this.warehouseCapacity = warehouseCapacity;
    }

    public int getFreeStorage() {
        return this.freeStorage;
    }

    public int getWarehouseCapacity() {
        return this.warehouseCapacity;
    }
}
