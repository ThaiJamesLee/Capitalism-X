package de.uni.mannheim.capitalismx.hr.domain;

/**
 * See p. 25
 * @author duly
 */
public enum BenefitType {

    /**
     * The benefit associated to the salary.
     */
    SALARY ("Salary"),

    /**
     * The benefit associated with the working time model.
     */
    WORKING_TIME_MODEL ("Working Time Model"),

    /**
     * The benefit associated with the work time.
     */
    WORKTIME ("Worktime"),

    /**
     * The benefit associated with the company car.
     */
    COMPANY_CAR ("Company Car"),

    /**
     * The benefit associated with the IT equipment.
     */
    IT_EQUIPMENT ("IT Equipment"),

    /**
     * The benefit associated with the food and coffee.
     */
    FOOD_AND_COFFEE ("Food / Coffee"),

    /**
     * The benefit associated with the gym and sports.
     */
    GYM_AND_SPORTS ("Gym / Sports");

    private String type;

    BenefitType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
