package de.uni.mannheim.capitalismx.hr.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author duly
 *
 * Implementation of the table on p.25
 */
public enum Benefit {

    /**
     * Salary below average.
     */
    SALARY_0(BenefitType.SALARY, 0,0, 0, "Salary below average"),

    /**
     * SSalary on average.
     */
    SALARY_1(BenefitType.SALARY, 1, 2, 0, "Salary on average"),

    /**
     * Salary above average.
     */
    SALARY_2(BenefitType.SALARY,2,4, 0, "Salary above average"),


    /**
     * Working Time Model - Fixed Model.
     */
    WTM_0(BenefitType.WORKING_TIME_MODEL, 0,0, 0, "Fixed Model"),

    /**
     * Working Time Model - Flextime Model.
     */
    WTM_1(BenefitType.WORKING_TIME_MODEL, 1,1, 0, "Flextime Model"),

    /**
     * Working Time Model - Trust-based.
     */
    WTM_2(BenefitType.WORKING_TIME_MODEL, 2,2, 0, "Trust-based"),

    /**
     * Working Time Model - rust-based + Home Office once a week.
     */
    WTM_3(BenefitType.WORKING_TIME_MODEL,3, 3, 0, "Trust-based + Home Office once a week"),

    /**
     * Working Time Model - Trust-based + Home Office always allowed.
     */
    WTM_4(BenefitType.WORKING_TIME_MODEL, 4,4, 0, "Trust-based + Home Office always allowed"),


    /**
     * Work Time - 10 Hours.
     */
    WORK_TIME_0(BenefitType.WORKTIME, 0,0, 0, "10 Hours"),

    /**
     * Work Time - 8 Hours.
     */
    WORK_TIME_1(BenefitType.WORKTIME, 1,3, 0, "8 Hours"),

    /**
     * Work Time - 6 Hours.
     */
    WORK_TIME_2(BenefitType.WORKTIME, 2,5, 1000, "6 Hours"),


    /**
     * Company Car - Not offered.
     */
    COMPANY_CAR_0(BenefitType.COMPANY_CAR, 0,0, 0, "Not offered"),

    /**
     * Company Car - Medium Size.
     */
    COMPANY_CAR_1(BenefitType.COMPANY_CAR, 1,2, 300, "Medium Size"),

    /**
     * Company Car - Full Size.
     */
    COMPANY_CAR_2(BenefitType.COMPANY_CAR, 2,5, 600, "Full Size"),


    /**
     * IT Equipment - Average IT Equipment.
     */
    IT_EQUIPMENT_0(BenefitType.IT_EQUIPMENT, 0,0, 0, "Average IT Equipment"),

    /**
     * IT Equipment - High-End IT Equipment.
     */
    IT_EQUIPMENT_1(BenefitType.IT_EQUIPMENT, 1,2, 50, "High-End IT Equipment"),


    /**
     * Food and Coffee - Offered for free.
     */
    FOOD_AND_COFFEE_1(BenefitType.FOOD_AND_COFFEE, 1,4, 100, "Offered for free"),

    /**
     * Food and Coffee - Employee Payment.
     */
    FOOD_AND_COFFEE_0(BenefitType.FOOD_AND_COFFEE, 0,0, 0, "Employee Payment"),

    /**
     * Gym and Sports - Not offered.
     */
    GYM_AND_SPORTS_0(BenefitType.GYM_AND_SPORTS, 0,0, 0, "Not offered"),

    /**
     * Gym and Sports - Subsidized.
     */
    GYM_AND_SPORTS_1(BenefitType.GYM_AND_SPORTS, 1,2, 40, "Subsidized"),

    /**
     * Gym and Sports - Offered for free.
     */
    GYM_AND_SPORTS_2(BenefitType.GYM_AND_SPORTS, 2,4, 100, "Offered for free");

    private BenefitType type;
    private int points;
    private int monetaryImpact;

    private int tier;

    private String name;

    /**
     *  Constructor
     * @param type the BenefitType enum
     * @param tier benefit tier, the higher, the better the benefit
     * @param points the points for the benefit, usually also the higher the tier the higher the points
     * @param monetaryImpact cost for company when enabling this benefit
     * @param name name of the benefit as a string
     */
    Benefit(BenefitType type, int tier, int points, int monetaryImpact, String name) {
        this.type = type;
        this.points = points;
        this.monetaryImpact = monetaryImpact;
        this.name = name;
        this.tier = tier;
    }

    /**
     * The points are used to compute the original scale of the job satisfaction.
     * @return Returns the points for the benefit.
     */
    public int getPoints() {
        return points;
    }

    /**
     * The cost for this benefit.
     * @return Returns the cost of the benefit.
     */
    public int getMonetaryImpact() {
        return monetaryImpact;
    }

    /**
     * The tier of the benefit. The higher, the higher the points.
     * @return Returns the tier of the benefit.
     */
    public int getTier() { return tier; }

    /**
     * See in {@link BenefitType}.
     * @return Returns the type of the benefit.
     */
    public BenefitType getType() {
        return type;
    }

    /**
     *
     * @return Returns the benefit name.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param benefitType The benefit type of interest.
     * @return Returns the benefit with the specified benefit type that has the highest tier.
     */
    public static Benefit getMaxTierBenefitByType(BenefitType benefitType) {
        Benefit[] benefits = Benefit.values();

        List<Benefit> benefitList = new ArrayList<>();


        for(Benefit b : benefits) {
            if(b.getType().equals(benefitType)) {
                benefitList.add(b);
            }
        }
        /* Sort benefit list by tier in descending order. */
        benefitList.sort(new Comparator<Benefit>() {
            @Override
            public int compare(Benefit o1, Benefit o2) {
                return (o1.getTier() > o2.getTier()) ? -1 : (o1.getTier() == o2.getTier())? 0 : 1;
            }
        });

        return benefitList.get(0);
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
        BenefitType[] types = BenefitType.values();

        for (BenefitType t : types) {
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
