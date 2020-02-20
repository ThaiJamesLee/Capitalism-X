package de.uni.mannheim.capitalismx.production;

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
