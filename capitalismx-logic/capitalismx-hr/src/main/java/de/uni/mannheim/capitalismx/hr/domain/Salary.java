package de.uni.mannheim.capitalismx.hr.domain;

import de.uni.mannheim.capitalismx.utils.data.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the salary and skill level ranges.
 * See p. 23
 * @author duly
 */
public enum Salary {

    TIER_0 ("worker", 0,0, 20, 38000, 45000),
    TIER_1 ("student", 1,21, 40, 45000, 55000),
    TIER_2 ("graduate", 2,41, 60, 55000, 70000),
    TIER_3 ("specialist", 3,61, 80, 70000, 100000),
    TIER_4 ("expert", 4,81, 100, 100000, 150000);

    private String name;

    private int tier;

    private Range salaryRange;
    private Range skillLevelRange;

    /**
     *
     * @param lowerLevel lower bound of skill level
     * @param upperLevel upper bound of skill level
     * @param lowerSalary lower bound of salary for this skill level range
     * @param upperSalary upper bound of salary for this skill level range
     */
    Salary(String name, int tier, int lowerLevel, int upperLevel, int lowerSalary, int upperSalary) {
        salaryRange = new Range(lowerSalary, upperSalary);
        skillLevelRange = new Range(lowerLevel, upperLevel);
        this.name = name;
        this.tier = tier;
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

    public int getTier() {
        return tier;
    }

    /**
     *
     * @param skillLevel The skill level.
     * @return Returns the salary by the specified skill level. Returns null if not exists.
     */
    public Salary getSalaryBySkillLevel(int skillLevel) {
        Salary[] salaries = Salary.values();

        for(Salary s : salaries) {
            if (s.getSkillLevelRange().isInRange(skillLevel)) {
                return s;
            }
        }
        return null;
    }

    /**
     *
     * @param name The skill level name.
     * @return Returns the salary by the specified skill level name. Returns null if not exists.
     */
    public Salary getSalaryByName(String name) {
        Salary[] salaries = Salary.values();

        for(Salary s : salaries) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    /**
     * The list is sorted by the enum definition order.
     * @return Returns a sorted list of all skill level names.
     */
    public static List<String> getSkillLevelNames() {
        Salary[] salaries = Salary.values();
        List<String> names = new ArrayList<>();

        for(Salary s : salaries) {
            names.add(s.getName());
        }
        return names;
    }

    public String getName() {
        return name;
    }
}
