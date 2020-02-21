package de.uni.mannheim.capitalismx.hr.department.skill;


import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * This class represents the skills that can be unlocked in the HR Department, when leveling up.
 * It increases the employee capacity on level up.
 * It changes the distribution of probabilities to get better skill level employees on higher skill level.
 * @author ly
 *
 * @since 1.0.0
 */
public class HRSkill implements DepartmentSkill {

    /* The level requirement for this skill. */
    private int level;

    /* The new capacity to hire HRWorkers */
    private int newEmployeeCapacity;

    /* Defines the distribution of probabilities for employee skill level. */
    private Map<String, Double> skillDistribution;

    private static final String LANG_PROPERTIES = "hr-module";
    private static final String DESCRIPTION_PROPERTY_PREFIX = "hr.skill.description.";

    /**
     * The constructor to initialize a skill.
     * @param level The level of the skill.
     * @param newEmployeeCapacity The employeee capacity to increase.
     * @param skillDistribution The new employee distribution by skill level.
     */
    public HRSkill(int level, int newEmployeeCapacity, Map<String, Double> skillDistribution) {
        this.level = level;
        this.newEmployeeCapacity = newEmployeeCapacity;
        this.skillDistribution = skillDistribution;
    }

    /**
     *
     * @return Returns the required department level for this skill.
     */
    public int getLevel() {
        return level;
    }

    /**
     *  The new employee capacity when unlocking this skill.
     * @return Returns the new employee capacity.
     */
    public int getNewEmployeeCapacity() {
        return newEmployeeCapacity;
    }

    /**
     * The new employee distribution when unlocking this skill.
     * The player has higher chances to roll employees with higher skill level for recruiting.
     * @return Returns the distribution.
     */
    public Map<String, Double> getSkillDistribution() {
        return skillDistribution;
    }

    public void setSkillDistribution(Map<String, Double> skillDistribution) {
        this.skillDistribution = skillDistribution;
    }

    @Override
    public String getDescription() {
        ResourceBundle langBundle = ResourceBundle.getBundle(LANG_PROPERTIES);
        return langBundle.getString(DESCRIPTION_PROPERTY_PREFIX + level);
    }

    @Override
    public String getDescription(Locale l) {
        ResourceBundle langBundle = ResourceBundle.getBundle(LANG_PROPERTIES, l);
        return langBundle.getString(DESCRIPTION_PROPERTY_PREFIX + level);
    }
}
