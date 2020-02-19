package de.uni.mannheim.capitalismx.hr.domain;

import de.uni.mannheim.capitalismx.utils.data.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class contains the salary and skill level ranges.
 * See p. 23
 * @author duly
 */
public enum SalaryTier {

    TIER_0(0), TIER_1(1), TIER_2(2), TIER_3(3), TIER_4(4);

    private static final String PROPERTIES_FILE = "hr-module";

    private static final String SALARY_TIER_LEVEL_UPPER_PREFIX = "salary.tier.level.upper.";
    private static final String SALARY_TIER_LEVEL_LOWER_PREFIX = "salary.tier.level.lower.";
    private static final String SALARY_TIER_SALARY_LOWER_PREFIX = "salary.tier.salary.lower.";
    private static final String SALARY_TIER_SALARY_UPPER_PREFIX = "salary.tier.salary.upper.";

    private int tier;

    private Range salaryRange;
    private Range skillLevelRange;

    /**
     * @param tier The tier of the salary.
     */
    SalaryTier(int tier) {
        this.tier = tier;
        init();
    }

    private void init() {
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE);

        double lowerSalary = Integer.parseInt(bundle.getString(SALARY_TIER_SALARY_LOWER_PREFIX + this.tier));
        double upperSalary = Integer.parseInt(bundle.getString(SALARY_TIER_SALARY_UPPER_PREFIX + this.tier));

        double lowerLevel = Integer.parseInt(bundle.getString(SALARY_TIER_LEVEL_LOWER_PREFIX + this.tier));
        double upperLevel = Integer.parseInt(bundle.getString(SALARY_TIER_LEVEL_UPPER_PREFIX + this.tier));

        this.salaryRange = new Range(lowerSalary, upperSalary);
        this.skillLevelRange = new Range(lowerLevel, upperLevel);

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
