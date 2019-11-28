package de.uni.mannheim.capitalismx.domain.department;

/**
 * The interface that all departments implement from.
 * @author duly
 */
public interface Department {

    /**
     *
     * @return Returns the name of the department.
     */
    String getName();

    /**
     *
     * @return Returns the current level of the department.
     */
    int getLevel();

    /**
     * Sets the level of the department.
     * @param level the new level.
     */
    void setLevel(int level);

    LevelingMechanism getLevelingMechanism();

    /**
     * Each department has a max level. They can not have a higher level than the defined max level.
     * @return Returns the max level of the department.
     */
    int getMaxLevel();
}
