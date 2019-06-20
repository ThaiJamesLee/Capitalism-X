package de.uni.mannheim.capitalismx.production;

public enum ProductCategory {

    NOTEBOOK("Notebook"),
    PHONE("Phone"),
    GAME_BOY("Game Boy"),
    TELEVISION("Television");

    ProductCategory(String productName) {
        this.productName = productName;
    }

    private String productName;

    public String toString() {
        return this.productName;
    }
}