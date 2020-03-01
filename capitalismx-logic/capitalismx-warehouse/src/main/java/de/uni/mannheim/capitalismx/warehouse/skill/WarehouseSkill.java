package de.uni.mannheim.capitalismx.warehouse.skill;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;

import java.util.Locale;

/**
 * The warehouse skill for the leveling mechanism of the warehouse department.
 * It defines how many new warehouse slots are unlocked for a level.
 *
 * @author dzhao
 */
public class WarehouseSkill implements DepartmentSkill {

    private int level;
    private int newWarehouseSlots;

    //TODO
    private String description;

    /**
     * Instantiates a new Warehouse skill.
     *
     * @param level             the level
     * @param newWarehouseSlots the new warehouse slots
     */
    public WarehouseSkill(int level, int newWarehouseSlots) {
        this.level = level;
        this.newWarehouseSlots = newWarehouseSlots;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Gets new warehouse slots.
     *
     * @return the new warehouse slots
     */
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
