package de.uni.mannheim.capitalismx.hr.domain;

public enum BenefitTypes {

    SALARY ("Salary"),
    WORKING_TIME_MODEL ("Working Time Model"),
    WORKTIME ("Worktime"),
    COMPANY_CAR ("Company Car"),
    IT_EQUIPMENT ("IT Equipment"),
    FOOD_AND_COFFEE ("Food / Coffee"),
    GYM_AND_SPORTS ("Gym / Sports");

    private String type;

    BenefitTypes(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
