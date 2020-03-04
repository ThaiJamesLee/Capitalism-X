package de.uni.mannheim.capitalismx.production.skill;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The type production skill used for the leveling mechanism of the production department.
 * It has the level the new unlocked production slots and the requirements of how many production workers are needed for a specific level.
 *
 * @author dzhao
 */
public class ProductionSkill implements DepartmentSkill {

    private int level;
    private int newProductionSlots;
    private int productionWorkersNeeded;

    private static final String LANG_PROPERTIES = "production-module";
    private static final String DESCRIPTION_PROPERTY_PREFIX = "production.skill.description.";

    /**
     * Instantiates a new production skill.
     *
     * @param level                   the level
     * @param newProductionSlots      the new production slots
     * @param productionWorkersNeeded the production workers needed
     */
    public ProductionSkill(int level, int newProductionSlots, int productionWorkersNeeded) {
        this.level = level;
        this.newProductionSlots = newProductionSlots;
        this.productionWorkersNeeded = productionWorkersNeeded;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Gets new production slots.
     *
     * @return the new production slots
     */
    public int getNewProductionSlots() {
        return newProductionSlots;
    }

    /**
     * Gets production workers needed.
     *
     * @return the production workers needed
     */
    public int getProductionWorkersNeeded() {
        return this.productionWorkersNeeded;
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getDescription(Locale l) {
        ResourceBundle langBundle = ResourceBundle.getBundle(LANG_PROPERTIES, l);
        return langBundle.getString(DESCRIPTION_PROPERTY_PREFIX + level);
    }
}
