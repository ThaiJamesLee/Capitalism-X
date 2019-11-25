package de.uni.mannheim.capitalismx.procurement.component;

import java.io.Serializable;

public enum UnitType implements Serializable {

    PRODUCT_UNIT("Product"),
    COMPONENT_UNIT("Component");

    private String type;

    UnitType(String type) {
        this.type = type;
    }

    public String toString() {
        return this.type;
    }
}
