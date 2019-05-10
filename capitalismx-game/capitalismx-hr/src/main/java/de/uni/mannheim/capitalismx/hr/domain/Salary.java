package de.uni.mannheim.capitalismx.hr.domain;

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
