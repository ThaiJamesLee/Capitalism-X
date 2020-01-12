package de.uni.mannheim.capitalismx.hr.domain;

import de.uni.mannheim.capitalismx.utils.data.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the salary and skill level ranges.
 * See p. 23
 * @author duly
 */
public enum SalaryTier {

    TIER_0 (0,0, 20, 38000, 45000),
    TIER_1 (1,21, 40, 45000, 55000),
    TIER_2 (2,41, 60, 55000, 70000),
    TIER_3 (3,61, 80, 70000, 100000),
    TIER_4 (4,81, 100, 100000, 150000);

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
    SalaryTier(int tier, int lowerLevel, int upperLevel, int lowerSalary, int upperSalary) {
        salaryRange = new Range(lowerSalary, upperSalary);
        skillLevelRange = new Range(lowerLevel, upperLevel);
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
    public SalaryTier getSalaryBySkillLevel(int skillLevel) {
        SalaryTier[] salaries = SalaryTier.values();

        for(SalaryTier s : salaries) {
            if (s.getSkillLevelRange().isInRange(skillLevel)) {
                return s;
            }
        }
        return null;
    }

    /**
     *
     * @return Returns the salary with the maximum tier.
     */
    public static SalaryTier getMaxTier() {
        SalaryTier[] salaries = SalaryTier.values();
        SalaryTier maxTier = null;

        for(SalaryTier s : salaries) {
            if(maxTier == null) {
                maxTier = s;
            } else {
                if(maxTier.getTier() < s.getTier()) {
                    maxTier = s;
                }
            }
        }
        return maxTier;
    }

}
