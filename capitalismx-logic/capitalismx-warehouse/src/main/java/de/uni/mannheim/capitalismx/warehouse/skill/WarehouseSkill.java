package de.uni.mannheim.capitalismx.warehouse.skill;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;

import java.util.Locale;

public class WarehouseSkill implements DepartmentSkill {

    private int level;
    private int newWarehouseSlots;

    private String description;

    public WarehouseSkill(int level, int newWarehouseSlots) {
        this.level = level;
        this.newWarehouseSlots = newWarehouseSlots;
    }

    public int getLevel() {
        return level;
    }

    public int getNewWarehouseSlots() {
        return newWarehouseSlots;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getDescription(Locale l) {
        return null;
    }
}
