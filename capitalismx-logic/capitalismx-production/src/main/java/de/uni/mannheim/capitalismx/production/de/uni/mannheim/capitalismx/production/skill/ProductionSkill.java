package de.uni.mannheim.capitalismx.production.de.uni.mannheim.capitalismx.production.skill;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;

import java.util.Locale;

public class ProductionSkill implements DepartmentSkill {

    private int level;
    private int newMachineCapacity;

    private String description;

    public ProductionSkill(int level, int newMachineCapacity) {
        this.level = level;
        this.newMachineCapacity = newMachineCapacity;
    }

    public int getLevel() {
        return level;
    }

    public int getNewMachineCapacity() {
        return newMachineCapacity;
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
