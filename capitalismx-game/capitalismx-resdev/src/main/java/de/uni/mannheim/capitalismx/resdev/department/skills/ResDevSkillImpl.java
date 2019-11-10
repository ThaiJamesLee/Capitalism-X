package de.uni.mannheim.capitalismx.resdev.department.skills;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;

import java.util.ArrayList;
import java.util.List;

/**
 * An Abstract class to define the basic body for a Research and Development skill.
 * @author duly
 */
public abstract class ResDevSkillImpl implements DepartmentSkill {

    private List<ComponentType> unlockedComponents;
    private int level;
    private int year;


    public ResDevSkillImpl(int level, int year) {
        this.level = level;
        this.year = year;
        this.unlockedComponents = new ArrayList<>();
    }

    /**
     * Initialize the unlockedComponents List based on level and year of this skill.
     */
    abstract void initializeUnlockedComponent();


    public int getLevel() {
        return level;
    }

    public int getYear() {
        return year;
    }

    public List<ComponentType> getUnlockedComponents() {
        return unlockedComponents;
    }

    public void setUnlockedComponents(List<ComponentType> unlockedComponents) {
        this.unlockedComponents = unlockedComponents;
    }
}
