package de.uni.mannheim.capitalismx.domain.department;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract class for departments. It implements the {@link Department} interface.
 * Each department has {@link DepartmentSkill} Maps. It maps skills to the levels.
 * Each new level unlocks a new DepartmentSkill.
 * By default departments have a maxLevel 8.
 * @author duly
 *
 * @since 1.0.0
 */
public abstract class DepartmentImpl implements Department, Serializable {

    /**
     * The name of the department.
     */
    private String name;

    /**
     * The level of the department. Initial value is 0.
     */
    private int level;

    /**
     * Handles the leveling. Must be initialized with a cost map.
     */
    private LevelingMechanism levelingMechanism;

    /**
     * A skill map. The key is the level, and the value is the corresponding skill that is being unlocked.
     */
    protected Map<Integer, DepartmentSkill> skillMap;

    /**
     * The maximum level the department can be leveled.
     */
    private int maxLevel;


    public DepartmentImpl(String name) {
        this.name = name;

        this.level = 0;
        this.maxLevel = 8;

        this.skillMap = new ConcurrentHashMap<>();
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
     * Set the  {@link LevelingMechanism} to be able to level up and get the cost of level ups.
     * @param levelingMechanism The leveling mechanism contains the function for level up.
     */
    public void setLevelingMechanism(LevelingMechanism levelingMechanism) {
        this.levelingMechanism = levelingMechanism;
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
            if(level >= entry.getKey()) {
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
