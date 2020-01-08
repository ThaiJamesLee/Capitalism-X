package de.uni.mannheim.capitalismx.production.skill;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;

import java.util.Locale;

public class ProductionSkill implements DepartmentSkill {

    private int level;
    private int newProductionSlots;

    private String description;

    public ProductionSkill(int level, int newProductionSlots) {
        this.level = level;
        this.newProductionSlots = newProductionSlots;
    }

    public int getLevel() {
        return level;
    }

    public int getNewProductionSlots() {
        return newProductionSlots;
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
