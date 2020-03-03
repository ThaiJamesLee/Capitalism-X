package de.uni.mannheim.capitalismx.logistic.logistics;


import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * This class represents the skills that can be unlocked in the Logistics Department when leveling up.
 *
 * @author sdupper
 *
 * @since 1.0.0
 */
public class LogisticsSkill implements DepartmentSkill {

    /* The level requirement for this skill. */
    private int level;

    /* The new capacity for trucks in the internal fleet. */
    private int newFleetCapacity;

    private static final String LANG_PROPERTIES = "logistics-module";
    private static final String DESCRIPTION_PROPERTY_PREFIX = "logistics.skill.description.";

    /**
     * Constructor
     * Initializes the level and new fleet capacity.
     * @param level The required level for this skill.
     * @param newFleetCapacity The new capacity for trucks in the internal fleet.
     */
    public LogisticsSkill(int level, int newFleetCapacity) {
        this.level = level;
        this.newFleetCapacity = newFleetCapacity;
    }

    /**
     *
     * @return Returns the required department level for this skill.
     */
    public int getLevel() {
        return level;
    }

    public int getNewFleetCapacity() {
        return newFleetCapacity;
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getDescription(Locale l) {
        ResourceBundle langBundle = ResourceBundle.getBundle(LANG_PROPERTIES, l);
        return langBundle.getString(DESCRIPTION_PROPERTY_PREFIX + level);
    }
}
