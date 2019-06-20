package de.uni.mannheim.capitalismx.production;

public enum ProductionInvestmentLevel {

    NO_INVESTMENT (1, "No Investment", 0),
    BAD (2, "Bad", 5000),
    NORMAL (3, "Normal", 10000),
    GOOD (4, "Good", 15000),
    VERY_GOOD (5, "Very good", 20000);

    ProductionInvestmentLevel(int level, String description, double investmentPrice) {
        this.level = level;
        this.description = description;
        this.investmentPrice = investmentPrice;
    }

    private int level;
    private String description;
    private double investmentPrice;

    public String toString() {
        return this.description;
    }

    public int getLevel() {
        return this.level;
    }

    public double getInvestmentPrice() {
        return this.investmentPrice;
    }
}