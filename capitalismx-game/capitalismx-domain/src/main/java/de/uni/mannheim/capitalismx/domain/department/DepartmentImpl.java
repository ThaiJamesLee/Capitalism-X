package de.uni.mannheim.capitalismx.domain.department;

import java.io.Serializable;
import java.util.Map;

/**
 * Abstract class for departments.
 * @author duly
 */
public abstract class DepartmentImpl implements Department, Serializable {

    private String name;

    private int level;

    private LevelingMechanism levelingMechanism;

    private Map<Integer, DepartmentSkill> skillMap;


    public DepartmentImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public LevelingMechanism getLevelingMechanism() {
        return levelingMechanism;
    }

    /**
     *
     * @return Returns a HashMap of level and skill.
     */
    public Map<Integer, DepartmentSkill> getSkillMap() {
        return skillMap;
    }

}
