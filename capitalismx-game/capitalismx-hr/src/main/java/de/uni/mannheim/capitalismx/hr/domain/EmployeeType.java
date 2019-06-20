package de.uni.mannheim.capitalismx.hr.domain;

/**
 * @author duly
 */
public enum EmployeeType {

    ENGINEER("Engineer"),
    SALESPERSON("Salesperson");

    private String type;

    EmployeeType (String type) {
        this.type = type;
    }

    @Override
    public String toString () {
        return type;
    }
}
