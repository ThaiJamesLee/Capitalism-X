package de.uni.mannheim.capitalismx.production.exceptions;

public class ProductNameAlreadyInUseException extends Exception{

    private String productName;

    public ProductNameAlreadyInUseException(String message, String productName) {
        super(message);
        this.productName = productName;
    }

    public String getProductName() {
        return this.productName;
    }
}
