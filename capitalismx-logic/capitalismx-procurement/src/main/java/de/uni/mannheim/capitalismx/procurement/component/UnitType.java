package de.uni.mannheim.capitalismx.procurement.component;

import java.io.Serializable;

/**
 * The enum Unit type.
 *
 * @author dzhao
 */
public enum UnitType implements Serializable {

    PRODUCT_UNIT("Product"),
    COMPONENT_UNIT("Component");

    private String type;

    /**
     * Instantiates a unit type.
     *
     * @param type
     */
    UnitType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
