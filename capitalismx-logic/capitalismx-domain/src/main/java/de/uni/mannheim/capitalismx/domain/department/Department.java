package de.uni.mannheim.capitalismx.domain.department;

import de.uni.mannheim.capitalismx.domain.exception.LevelingRequirementNotFulFilledException;

import java.io.Serializable;

/**
 * The interface that all departments implement from.
 * @author duly
 *
 * @since 1.0.0
 */
public interface Department extends Serializable {

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
    void setLevel(int level) throws LevelingRequirementNotFulFilledException;

    /**
     * Use this to access the leveling mechanism of the department.
     * This class handles the level up. It also returns costs when leveling up.
     *
     * @return The leveling mechanism of the department.
     */
    LevelingMechanism getLevelingMechanism();

    /**
     * Each department has a max level. They can not have a higher level than the defined max level.
     * @return Returns the max level of the department.
     */
    int getMaxLevel();
}
