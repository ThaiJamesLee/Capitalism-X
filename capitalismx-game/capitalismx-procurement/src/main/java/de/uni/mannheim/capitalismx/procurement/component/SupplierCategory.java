package de.uni.mannheim.capitalismx.procurement.component;

import java.io.Serializable;

public enum SupplierCategory implements Serializable {
    PREMIUM("Premium Supplier"),
    REGULAR("Regular Supplier"),
    CHEAP("Cheap Supplier");

    private String category;

    SupplierCategory(String category) {
        this.category = category;
    }

    public String toString() {
        return this.category;
    }
}