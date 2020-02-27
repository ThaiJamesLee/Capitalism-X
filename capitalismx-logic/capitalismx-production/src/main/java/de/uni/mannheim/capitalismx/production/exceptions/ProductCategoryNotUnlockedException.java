package de.uni.mannheim.capitalismx.production.exceptions;

import de.uni.mannheim.capitalismx.production.ProductCategory;

public class ProductCategoryNotUnlockedException extends Exception {

    private ProductCategory productCategory;

    public ProductCategoryNotUnlockedException(String message, ProductCategory productCategory) {
        super(message);
        this.productCategory = productCategory;
    }

    public ProductCategory getProductCategory() {
        return this.productCategory;
    }
}
