package de.uni.mannheim.capitalismx.production.investment;

import java.io.Serializable;

/**
 * The enum Production investment level.
 *
 * @author dzhao
 */
public enum ProductionInvestmentLevel implements Serializable {

    NO_INVESTMENT (1, "No Investment", 0),
    BAD (2, "Bad", 5000),
    NORMAL (3, "Normal", 10000),
    GOOD (4, "Good", 15000),
    VERY_GOOD (5, "Very good", 20000);

    private int level;
    private String description;
    private double investmentPrice;

    /**
     * Instantiates a product investment level.
     * Initiates all variables.
     *
     * @param level
     * @param description
     * @param investmentPrice
     */
    ProductionInvestmentLevel(int level, String description, double investmentPrice) {
        this.level = level;
        this.description = description;
        this.investmentPrice = investmentPrice;
    }

    @Override
    public String toString() {
        return this.description;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Gets investment price.
     *
     * @return the investment price
     */
    public double getInvestmentPrice() {
        return this.investmentPrice;
    }
}
