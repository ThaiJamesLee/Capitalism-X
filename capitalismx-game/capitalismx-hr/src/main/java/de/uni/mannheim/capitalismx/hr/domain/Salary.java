package de.uni.mannheim.capitalismx.hr.domain;

import de.uni.mannheim.capitalismx.utils.data.Range;

/**
 * This class contains the salary and skill level ranges.
 * See p. 23
 * @author duly
 */
public enum Salary {

    TIER_0 ("worker", 0, 20, 38000, 45000),
    TIER_1 ("student", 21, 40, 45000, 55000),
    TIER_2 ("graduate", 41, 60, 55000, 70000),
    TIER_3 ("specialist", 61, 80, 70000, 100000),
    TIER_4 ("expert", 81, 100, 100000, 150000);

    private String name;

    private int lowerLevel;
    private int upperLevel;

    private int lowerSalary;
    private int upperSalary;

    private Range salaryRange;
    private Range skillLevelRange;

    /**
     *
     * @param lowerLevel lower bound of skill level
     * @param upperLevel upper bound of skill level
     * @param lowerSalary lower bound of salary for this skill level range
     * @param upperSalary upper bound of salary for this skill level range
     */
    Salary(String name, int lowerLevel, int upperLevel, int lowerSalary, int upperSalary) {
        salaryRange = new Range(lowerSalary, upperSalary);
        skillLevelRange = new Range(lowerLevel, upperLevel);
        this.name = name;
    }

    public Range getSkillLevelRange() { return skillLevelRange; }

    public Range getSalaryRange() { return salaryRange; }

    public double getLowerLevel() { return skillLevelRange.getLowerBound(); }

    public double getUpperLevel() {
        return skillLevelRange.getUpperBound();
    }

    public double getLowerSalary() { return salaryRange.getLowerBound(); }

    public double getUpperSalary() {
        return salaryRange.getUpperBound();
    }

    public String getName() {
        return name;
    }
}
