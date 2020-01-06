package de.uni.mannheim.capitalismx.domain.department;

import de.uni.mannheim.capitalismx.domain.exception.InconsistentLevelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * This class is a handler for leveling for each department.
 * It contains a cost map for each level, which should be defined in the respective department, that holds class.
 * @author duly
 *
 * @since 1.0.0
 */
public class LevelingMechanism implements Serializable {

    private Department department;
    private Map<Integer, Double> levelCostMap;

    private static final Logger LOGGER = LoggerFactory.getLogger(LevelingMechanism.class);

    /**
     *
     * @param department The department that has the levelingMechanism.
     * @param levelCostMap The leveling cost map containing the levels as keys and cost as value.
     */
    public LevelingMechanism(Department department, Map<Integer, Double> levelCostMap) throws InconsistentLevelException{
        if (department.getMaxLevel() != levelCostMap.size()) {
            throw new InconsistentLevelException("The maximum department level must match the size of the level cost map!");
        }

        this.department = department;
        this.levelCostMap = levelCostMap;
    }

    public Department getDepartment() {
        return department;
    }

    public Map<Integer, Double> getLevelCostMap() {
        return levelCostMap;
    }

    /**
     *
     * @return Returns the cost for the next level up of the department. Return null if no next level up possible.
     */
    public Double getNextLevelUpCost() {
        int nextLevel = department.getLevel() + 1;

        Set<Integer> levelKeys = levelCostMap.keySet();
        if (levelKeys.contains(nextLevel)) {
            return levelCostMap.get(nextLevel);
        }
        return null;
    }

    /**
     * Increments the level and returns the cost.
     * @return Returns the cost of a level up. Otherwise return null.
     */
    public Double levelUp() {
        int currentLevel = department.getLevel();
        Double cost = getNextLevelUpCost();

        if(cost != null && cost > 0) {
            department.setLevel(currentLevel + 1);
            return cost;
        } else {
            LOGGER.error("No Level Up possible.");
            return null;
        }
    }
}
