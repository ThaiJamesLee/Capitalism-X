package de.uni.mannheim.capitalismx.resdev.skills;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;

/**
 * A class to define the basic body for a Research and Development skill.
 * @author duly
 */
public abstract class ResDevSkillImpl implements DepartmentSkill {

    private int level;
    private int year;

    private double cost;

    public ResDevSkillImpl(int level, int year, double cost) {
        this.level = level;
        this.year = year;
        this.cost = cost;
    }


    public int getLevel() {
        return level;
    }

    public int getYear() {
        return year;
    }

    public double getCost() {
        return cost;
    }
}
