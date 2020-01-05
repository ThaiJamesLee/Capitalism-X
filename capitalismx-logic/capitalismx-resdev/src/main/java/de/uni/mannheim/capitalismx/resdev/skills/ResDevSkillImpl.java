package de.uni.mannheim.capitalismx.resdev.skills;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;

/**
 * A class to define the basic body for a Research and Development skill.
 * TODO: implement skills, unlockCategories, unlockComponents
 * @author duly
 */
public abstract class ResDevSkillImpl implements DepartmentSkill {

    private int level;
    private int year;


    public ResDevSkillImpl(int level, int year) {
        this.level = level;
        this.year = year;
    }


    public int getLevel() {
        return level;
    }

    public int getYear() {
        return year;
    }
}
