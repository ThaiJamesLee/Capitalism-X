package de.uni.mannheim.capitalismx.domain.employee;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author duly
 */
public enum EmployeeType {

    ENGINEER("Engineer", "name.engineer"),
    SALESPERSON("Salesperson", "name.salesperson"),
    PRODUCTION_WORKER("Production Worker", "name.productionworker"),
    HR_WORKER("HR Worker", "name.hrworker");

    private String type;
    private String propertyKey;

    EmployeeType (String type, String propertyKey) {

        this.type = type;
        this.propertyKey = propertyKey;
    }

    @Override
    public String toString () {
        return type;
    }

    /**
     * Reads the properties file.
     * @param locale the locale of the properties file (English or German).
     * @return Returns the name of the employee type.
     */
    public String getName(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("domain-module", locale);
        return bundle.getString(propertyKey);
    }
}
