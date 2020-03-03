package de.uni.mannheim.capitalismx.finance.finance;


import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * This class represents the skills that can be unlocked in the Finance Department when leveling up.
 *
 * @author sdupper
 */
public class FinanceSkill implements DepartmentSkill {

    /* The level requirement for this skill. */
    private int level;

    /* The new additional tax reduction. */
    private double newTaxReduction;

    private static final String LANG_PROPERTIES = "finance-module";
    private static final String DESCRIPTION_PROPERTY_PREFIX = "finance.skill.description.";

    /**
     * Constructor
     * Initializes the level and new tax reduction.
     * @param level The required level for this skill.
     * @param newTaxReduction The new tax reduction.
     */
    public FinanceSkill(int level, double newTaxReduction) {
        this.level = level;
        this.newTaxReduction = newTaxReduction;
    }

    /**
     *
     * @return Returns the required department level for this skill.
     */
    public int getLevel() {
        return level;
    }

    public double getNewTaxReduction() {
        return newTaxReduction;
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
