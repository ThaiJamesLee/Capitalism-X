package de.uni.mannheim.capitalismx.hr.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duly
 *
 * Implementation of the table on p.25
 */
public enum Benefit {

    SALARY_BELOW_AVERAGE (BenefitTypes.SALARY, 0, 0, "Salary below average"),
    SALARY_ON_AVERAGE (BenefitTypes.SALARY,2, 0, "Salary on average"),
    SALARY_ABOVE_AVERAGE (BenefitTypes.SALARY,4, 0, "Salary above average"),

    WTM_FIXED_MODEL(BenefitTypes.WORKING_TIME_MODEL, 0, 0, "Fixed Model"),
    WTM_FLEXTIME_MODEL(BenefitTypes.WORKING_TIME_MODEL, 1, 0, "Flextime Model"),
    WTM_TB(BenefitTypes.WORKING_TIME_MODEL, 2, 0, "Trust-based"),
    WTM_TB_AND_HO_ONCE_A_WEEK (BenefitTypes.WORKING_TIME_MODEL, 3, 0, "Trust-based + Home Office once a week"),
    WTM_TB_AND_ALWAYS_HO_ALLOWED (BenefitTypes.WORKING_TIME_MODEL, 4, 0, "Trust-based + Home Office always allowed"),

    WT_TEN_HOURS (BenefitTypes.WORKTIME, 0, 0, "10 Hours"),
    WT_EIGHT_HOURS (BenefitTypes.WORKTIME, 3, 0, "8 Hours"),
    WT_SIX_HOURS (BenefitTypes.WORKTIME, 5, -1000, "6 Hours"),

    CC_NOT_OFFERED (BenefitTypes.COMPANY_CAR, 0, 0, "Not offered"),
    CC_MEDIUM_SIZE (BenefitTypes.COMPANY_CAR, 2, -300, "Medium Size"),
    CC_FULL_SIZE (BenefitTypes.COMPANY_CAR, 4, -600, "Full Size"),

    IT_AVERAGE (BenefitTypes.IT_EQUIPMENT, 0, 0, "Average IT Equipment"),
    IT_HIGH_END (BenefitTypes.IT_EQUIPMENT, 2, -50, "High-End IT Equipment"),

    FOOD_AND_COFFEE_FREE (BenefitTypes.FOOD_AND_COFFEE, 4, -100, "Employee Payment"),
    FOOD_AND_COFFEE_NOT_FREE (BenefitTypes.IT_EQUIPMENT, 0, 0, "Offered for free"),

    GYM_NOT_OFFERED (BenefitTypes.GYM_AND_SPORTS, 0, 0, "Not offered"),
    GYM_SUBSIDIZED (BenefitTypes.GYM_AND_SPORTS, 2, -40, "Subsidized"),
    GYM_FREE (BenefitTypes.GYM_AND_SPORTS, 4, -100, "Offered for free");

    private BenefitTypes type;
    private int points;
    private int monetaryImpact;

    private String name;

    Benefit(BenefitTypes type, int points, int monetaryImpact, String name) {
        this.type = type;
        this.points = points;
        this.monetaryImpact = monetaryImpact;
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public int getMonetaryImpact() {
        return monetaryImpact;
    }

    public BenefitTypes getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    /**
     * Gets for each BenefitType the maximum point and sum them up.
     * The report states that the scale is 0 - 28, but actually it is 0 - 27.
     * @return Returns the max job satisfaction score.
     */
    public int getMaxJobSatisfactionScore () {
        int score = 0;

        Benefit[] benefits = Benefit.values();

        List<Benefit> cache = new ArrayList<>();
        BenefitTypes[] types = BenefitTypes.values();

        for (BenefitTypes t : types) {
            Benefit maxB = null;
            for (Benefit b : benefits) {
                if (b.type.equals(t)){
                    if (maxB != null) {
                        if (maxB.points < b.points) {
                            maxB = b;
                        }
                    } else {
                        maxB = b;
                    }
                }
            }
            cache.add(maxB);
        }

        for (Benefit b : cache) {
            score += b.points;
        }
        return score;
    }

}
