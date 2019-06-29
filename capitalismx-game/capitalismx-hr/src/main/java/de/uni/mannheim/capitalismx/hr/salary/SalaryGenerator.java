package de.uni.mannheim.capitalismx.hr.salary;

import de.uni.mannheim.capitalismx.hr.domain.Salary;
import de.uni.mannheim.capitalismx.hr.exception.NoDefinedTierException;

import java.security.SecureRandom;


/**
 * @author duly
 */
public class SalaryGenerator {

    private static SalaryGenerator instance;

    private SalaryGenerator() {}

    public static SalaryGenerator getInstance() {
        if(instance == null) {
            instance = new SalaryGenerator();
        }
        return instance;
    }

    /**
     * Calculates a random salary that is in the predefined ranges.
     * @param skillLevel the skill level of an employee
     * @return Returns a salary based on the skill level
     */
    public int getSalary(int skillLevel) throws NoDefinedTierException {

        SecureRandom random = new SecureRandom();

        Salary[] tiers = Salary.values();

        for (Salary tier : tiers) {

            if (inRange(skillLevel, tier.getLowerLevel(), tier.getUpperLevel())) {

                return random.nextInt(tier.getUpperSalary() - tier.getLowerSalary()) + tier.getLowerSalary();

            }
        }
        throw new NoDefinedTierException("The skill level " + skillLevel + " is in none of the defined ranges!");
    }

    private boolean inRange(int val, int lower, int upper) {
        return (val >= lower && val <= upper);
    }
}
