package de.uni.mannheim.capitalismx.production;

import java.io.Serializable;

public enum ProductCategory implements Serializable {

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
