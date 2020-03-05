package de.uni.mannheim.capitalismx.procurement.component;

import java.io.Serializable;

/**
 * The type Unit.
 *
 * @author dzhao
 */
public abstract class Unit implements Serializable {

    private UnitType unitType;

    /**
     * Gets unit type.
     *
     * @return the unit type
     */
    public abstract UnitType getUnitType();

    /**
     * Gets warehouse sales price.
     *
     * @return the warehouse sales price
     */
    public abstract double getWarehouseSalesPrice();
}
