package de.uni.mannheim.capitalismx.production.department;

import java.io.Serializable;

/**
 * The enum Production technology.
 *
 * @author dzhao
 */
public enum ProductionTechnology implements Serializable {

    DEPRECIATED ( 1, "Depreciated"),
    OLD (2, "Old"),
    GOOD_CONDITIONS (3, "Good conditions"),
    PURCHASED_MORE_THAN_FIVE_YEARS_AGO (4, "Purchased more than 5 years ago"),
    BRANDNEW (5, "Brand new");

    private int range;
    private String description;

    /**
     * Instantiates the production technology.
     * Initates all variables
     *
     * @param range
     * @param description
     */
    ProductionTechnology(int range, String description) {
        this.range = range;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

    /**
     * Gets range.
     *
     * @return the range
     */
    public int getRange() {
        return this.range;
    }
}
