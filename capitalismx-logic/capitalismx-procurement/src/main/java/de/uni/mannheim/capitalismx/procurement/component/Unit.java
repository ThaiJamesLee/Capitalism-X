package de.uni.mannheim.capitalismx.procurement.component;

import java.io.Serializable;

public abstract class Unit implements Serializable {

    private UnitType unitType;

    public abstract UnitType getUnitType();
    public abstract double getWarehouseSalesPrice();

}
