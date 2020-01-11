package de.uni.mannheim.capitalismx.sales.skills;

import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.utils.data.Range;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This defines the skills, that the {@link de.uni.mannheim.capitalismx.sales.department.SalesDepartment} is able to
 * unlock, when leveling the department.
 * @author duly
 *
 * @since 1.0.0
 */
public class SalesDepartmentSkill implements DepartmentSkill {

    private static final String PROPERTIES_FILE = "sales-module";
    private static final String DESCRIPTION_PROPERTY_PREFIX = "sales.skill.description.";

    /**
     * The level required to unlock this skill.
     */
    private int level;

    /**
     * The number of contracts the Sales Department can get.
     */
    private int numContracts;

    /**
     * The prices for the wholesale price gets better, if the sales department has higher level.
     */
    private Range priceFactor;

    /**
     *
     * @param level he level required to unlock this skill.
     * @param numContracts The number of contracts the Sales Department can get.
     * @param priceFactor The prices for the wholesale price gets better, if the sales department has higher level.
     */
    public SalesDepartmentSkill(int level, int numContracts, Range priceFactor) {
        this.level = level;
        this.numContracts = numContracts;
        this.priceFactor = priceFactor;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getDescription(Locale l) {
        return ResourceBundle.getBundle(PROPERTIES_FILE).getString(DESCRIPTION_PROPERTY_PREFIX + level);
    }

    @Override
    public int getLevel() {
        return level;
    }

    /**
     *
     * @return Returns the number of contracts the department can get, after unlocking this skill.
     */
    public int getNumContracts() {
        return numContracts;
    }

    public Range getPriceFactor() {
        return priceFactor;
    }
}
