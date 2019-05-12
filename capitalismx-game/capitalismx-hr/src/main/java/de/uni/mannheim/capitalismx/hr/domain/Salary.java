package de.uni.mannheim.capitalismx.hr.domain;

/**
 * This class contains the salary and skill level ranges.
 * See p. 23
 * @author duly
 */
public enum Salary {

    TIER_0 (0, 20, 38000, 45000),
    TIER_1 (21, 40, 45000, 55000),
    TIER_2 (41, 60, 55000, 70000),
    TIER_3 (61, 80, 70000, 100000),
    TIER_4 (81, 100, 100000, 150000);

    private int lowerLevel;
    private int upperLevel;

    private int lowerSalary;
    private int upperSalary;

    /**
     *
     * @param lowerLevel lower bound of skill level
     * @param upperLevel upper bound of skill level
     * @param lowerSalary lower bound of salary for this skill level range
     * @param upperSalary upper bound of salary for this skill level range
     */
    Salary(int lowerLevel, int upperLevel, int lowerSalary, int upperSalary) {
        this.lowerLevel = lowerLevel;
        this.upperLevel = upperLevel;

        this.lowerSalary = lowerSalary;
        this.upperSalary = upperSalary;
    }

    public int getLowerLevel() {
        return lowerLevel;
    }

    public int getUpperLevel() {
        return upperLevel;
    }

    public int getLowerSalary() {
        return lowerSalary;
    }

    public int getUpperSalary() {
        return upperSalary;
    }
}
