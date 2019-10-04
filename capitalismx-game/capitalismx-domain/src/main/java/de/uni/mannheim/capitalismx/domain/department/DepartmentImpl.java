package de.uni.mannheim.capitalismx.domain.department;

import java.io.Serializable;

public abstract class DepartmentImpl implements Department, Serializable {

    private String name;

    private int level;

    public DepartmentImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

}
