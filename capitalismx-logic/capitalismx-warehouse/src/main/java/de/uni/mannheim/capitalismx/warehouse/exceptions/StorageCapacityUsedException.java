package de.uni.mannheim.capitalismx.warehouse.exceptions;

/**
 * The type Storage capacity used exception.
 *
 * @author dzhao
 */
public class StorageCapacityUsedException extends Exception {

    /**
     * The Free storage.
     */
    public int freeStorage;
    /**
     * The Warehouse capacity.
     */
    public int warehouseCapacity;

    /**
     * Instantiates a new Storage capacity used exception.
     *
     * @param message           the message
     * @param freeStorage       the free storage
     * @param warehouseCapacity the warehouse capacity
     */
    public StorageCapacityUsedException(String message, int freeStorage, int warehouseCapacity) {
        super(message);
        this.freeStorage = freeStorage;
        this.warehouseCapacity = warehouseCapacity;
    }

    /**
     * Gets free storage.
     *
     * @return the free storage
     */
    public int getFreeStorage() {
        return this.freeStorage;
    }

    /**
     * Gets warehouse capacity.
     *
     * @return the warehouse capacity
     */
    public int getWarehouseCapacity() {
        return this.warehouseCapacity;
    }
}
