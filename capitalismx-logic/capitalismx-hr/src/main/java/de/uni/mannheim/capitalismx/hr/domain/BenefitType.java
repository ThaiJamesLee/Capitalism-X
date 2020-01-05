package de.uni.mannheim.capitalismx.hr.domain;

/**
 * See p. 25
 * @author duly
 */
public enum BenefitType {

    SALARY ("Salary"),
    WORKING_TIME_MODEL ("Working Time Model"),
    WORKTIME ("Worktime"),
    COMPANY_CAR ("Company Car"),
    IT_EQUIPMENT ("IT Equipment"),
    FOOD_AND_COFFEE ("Food / Coffee"),
    GYM_AND_SPORTS ("Gym / Sports");

    private String type;

    BenefitType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
