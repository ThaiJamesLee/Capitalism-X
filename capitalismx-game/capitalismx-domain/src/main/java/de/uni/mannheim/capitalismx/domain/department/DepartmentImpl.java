package de.uni.mannheim.capitalismx.domain.department;

import java.io.Serializable;

/**
 * Abstract class for departments.
 * @author duly
 */
public abstract class DepartmentImpl implements Department, Serializable {

    private String name;

    private int level;

    private LevelingMechanism levelingMechanism;

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

}
