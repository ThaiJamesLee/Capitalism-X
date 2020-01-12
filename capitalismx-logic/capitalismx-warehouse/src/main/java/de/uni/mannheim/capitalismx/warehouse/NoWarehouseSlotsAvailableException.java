package de.uni.mannheim.capitalismx.warehouse;

public class NoWarehouseSlotsAvailableException extends Exception{

    public NoWarehouseSlotsAvailableException(String message) {
        super(message);
    }
}
