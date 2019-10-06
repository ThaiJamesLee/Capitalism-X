package de.uni.mannheim.capitalismx.domain.employee;

/**
 * @author duly
 */
public enum EmployeeType {

    ENGINEER("Engineer"),
    SALESPERSON("Salesperson"),
    PRODUCTION_WORKER("Production Worker");

    private String type;

    EmployeeType (String type) {
        this.type = type;
    }

    @Override
    public String toString () {
        return type;
    }
}
