package de.uni.mannheim.capitalismx.production.exceptions;

/**
 * The type Product name already in use exception.
 *
 * @author dzhao
 */
public class ProductNameAlreadyInUseException extends Exception{

    private String productName;

    /**
     * Instantiates a new Product name already in use exception.
     *
     * @param message     the message
     * @param productName the product name
     */
    public ProductNameAlreadyInUseException(String message, String productName) {
        super(message);
        this.productName = productName;
    }

    /**
     * Gets product name.
     *
     * @return the product name
     */
    public String getProductName() {
        return this.productName;
    }
}
