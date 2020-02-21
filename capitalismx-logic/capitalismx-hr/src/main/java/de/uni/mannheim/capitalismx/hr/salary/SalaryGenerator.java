package de.uni.mannheim.capitalismx.hr.salary;

import de.uni.mannheim.capitalismx.hr.domain.SalaryTier;
import de.uni.mannheim.capitalismx.hr.exception.NoDefinedTierException;

import java.security.SecureRandom;


/**
 * The salary generator is used during the employee generation.
 * It randomly generates the salary depending on the skill level.
 * @author duly
 *
 * @since 0.0.1
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
    public double getSalary(int skillLevel) throws NoDefinedTierException {

        SecureRandom random = new SecureRandom();

        SalaryTier[] tiers = SalaryTier.values();

        for (SalaryTier tier : tiers) {
            if (inRange(skillLevel, tier.getLowerLevel(), tier.getUpperLevel())) {
                return random.nextInt((int)tier.getUpperSalary() - (int)tier.getLowerSalary()) + tier.getLowerSalary();

            }
        }
        throw new NoDefinedTierException("The skill level " + skillLevel + " is in none of the defined ranges!");
    }

    private boolean inRange(double val, double lower, double upper) {
        return (val >= lower && val <= upper);
    }
}
