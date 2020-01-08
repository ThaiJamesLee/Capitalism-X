package de.uni.mannheim.capitalismx.hr.department.skill;


import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * This class represents the skills that can be unlocked in the HR Department, when leveling up.
 * @author ly
 *
 * @since 1.0.0
 */
public class HRSkill implements DepartmentSkill {

    /* The level requirement for this skill. */
    private int level;
    private int newEmployeeCapacity;

    /* Defines the distribution of probabilities for employee skill level. */
    private Map<String, Double> skillDistribution;

    private static final String LANG_PROPERTIES = "hr-module";
    private static final String DESCRIPTION_PROPERTY_PREFIX = "hr.skill.description.";

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

    public int getNewEmployeeCapacity() {
        return newEmployeeCapacity;
    }

    public Map<String, Double> getSkillDistribution() {
        return skillDistribution;
    }

    public void setSkillDistribution(Map<String, Double> skillDistribution) {
        this.skillDistribution = skillDistribution;
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
