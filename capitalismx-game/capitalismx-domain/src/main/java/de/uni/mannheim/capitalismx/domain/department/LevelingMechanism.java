package de.uni.mannheim.capitalismx.domain.department;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * This class is a holder for leveling for each department.
 * @author duly
 */
public class LevelingMechanism implements Serializable {

    private Department department;
    private Map<Integer, Integer> levelCostMap;

    private static final Logger LOGGER = LoggerFactory.getLogger(LevelingMechanism.class);

    public LevelingMechanism(Department department, Map<Integer, Integer> levelCostMap) {
        this.department = department;
        this.levelCostMap = levelCostMap;
    }

    public Department getDepartment() {
        return department;
    }

    public Map<Integer, Integer> getLevelCostMap() {
        return levelCostMap;
    }

    /**
     *
     * @return Returns the cost for the next level up of the department.
     */
    public int getNextLevelUpCost() {
        int nextLevel = department.getLevel() + 1;

        Set<Integer> levelKeys = levelCostMap.keySet();
        if (levelKeys.contains(nextLevel)) {
            return levelCostMap.get(nextLevel);
        }
        return -1;
    }

    public int levelUp() {
        int currentLevel = department.getLevel();
        int cost = getNextLevelUpCost();

        if(cost > 0) {
            department.setLevel(currentLevel + 1);
        } else {
            LOGGER.info("No Level Up possible.");
        }
        return cost;
    }
}
