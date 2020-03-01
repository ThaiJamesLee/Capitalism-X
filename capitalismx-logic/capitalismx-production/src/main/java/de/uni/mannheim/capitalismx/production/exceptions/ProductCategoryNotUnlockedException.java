package de.uni.mannheim.capitalismx.production.exceptions;

import de.uni.mannheim.capitalismx.production.product.ProductCategory;

/**
 * The type Product category not unlocked exception.
 *
 * @author dzhao
 */
public class ProductCategoryNotUnlockedException extends Exception {

    private ProductCategory productCategory;

    /**
     * Instantiates a new Product category not unlocked exception.
     *
     * @param message         the message
     * @param productCategory the product category
     */
    public ProductCategoryNotUnlockedException(String message, ProductCategory productCategory) {
        super(message);
        this.productCategory = productCategory;
    }

    /**
     * Gets product category.
     *
     * @return the product category
     */
    public ProductCategory getProductCategory() {
        return this.productCategory;
    }
}
