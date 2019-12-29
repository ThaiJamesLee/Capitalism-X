package de.uni.mannheim.capitalismx.domain.department;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for departments. It implements the {@link Department} interface.
 * Each department has {@link DepartmentSkill} Maps. It maps skills to the levels.
 * Each new level unlocks a new DepartmentSkill.
 * By default departments have a maxLevel 8.
 * @author duly
 */
public abstract class DepartmentImpl implements Department, Serializable {

    private String name;

    private int level;

    private LevelingMechanism levelingMechanism;

    private Map<Integer, DepartmentSkill> skillMap;

    private int maxLevel;


    public DepartmentImpl(String name) {
        this.name = name;
        this.maxLevel = 8;
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
     * Override the maximum level of the department.
     * @param maxLevel the new maxLevel.
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     *  Returns the HashMap of all skills.
     * @return Returns a HashMap of level and skill.
     */
    public Map<Integer, DepartmentSkill> getSkillMap() {
        return skillMap;
    }

    /**
     *  Gets all {@link DepartmentSkill} that are available with the current department level.
     * @return Returns currently available skills.
     */
    public List<DepartmentSkill> getAvailableSkills() {
        List<DepartmentSkill> availableSkills = new ArrayList<>();

        for (Map.Entry<Integer, DepartmentSkill> entry : skillMap.entrySet()) {
            if(entry.getKey() >= level) {
                availableSkills.add(entry.getValue());
            }
        }
        return availableSkills;
    }

    /**
     * Implement how to add a property change listener.
     * @param listener A PropertyChangeListener.
     */
    public abstract void registerPropertyChangeListener(PropertyChangeListener listener);
}
