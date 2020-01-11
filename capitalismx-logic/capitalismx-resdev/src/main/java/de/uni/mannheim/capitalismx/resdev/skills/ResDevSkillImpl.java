package de.uni.mannheim.capitalismx.resdev.skills;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;

/**
 * A class to define the basic body for a Research and Development skill.
 * @author duly
 */
public abstract class ResDevSkillImpl implements DepartmentSkill {

    /**
     * The cost of unlocking this skill.
     * This is only necessary, when you are not using the {@link de.uni.mannheim.capitalismx.domain.department.LevelingMechanism}
     * to unlock skills.
     */
    private double cost;

    public ResDevSkillImpl(double cost) {
        this.cost = cost;
    }

    /**
     * Get the cost of unlocking this skill.
     * This is only necessary, when you are not using the {@link de.uni.mannheim.capitalismx.domain.department.LevelingMechanism}
     * to unlock skills.
     *
     */
    public double getCost() {
        return cost;
    }
}
