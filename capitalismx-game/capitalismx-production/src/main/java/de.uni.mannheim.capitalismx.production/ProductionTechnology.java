package de.uni.mannheim.capitalismx.production;

public enum ProductionTechnology {

    DEPRECIATED ( 1, "Depreciated"),
    OLD (2, "Old"),
    GOOD_CONDITIONS (3, "Good conditions"),
    PURCHASED_MORE_THAN_FIVE_YEARS_AGO (4, "Purchased more than 5 years ago"),
    BRANDNEW (5, "Brand new");

    ProductionTechnology(int range, String description) {
        this.range = range;
        this.description = description;
    }

    private int range;
    private String description;

    public String toString() {
        return this.description;
    }

    public int getRange() {
        return this.range;
    }
}
