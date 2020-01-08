package de.uni.mannheim.capitalismx.domain.employee;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Defines Employee types available.
 * @author duly
 */
public enum EmployeeType {

    ENGINEER("Engineer", "name.engineer", "engineerTeamChanged"),
    SALESPERSON("Salesperson", "name.salesperson", "salespersonTeamChanged"),
    PRODUCTION_WORKER("Production Worker", "name.productionworker", "productionworkerTeamChanged"),
    HR_WORKER("HR Worker", "name.hrworker", "hrworkerTeamChanged");

    private String type;
    private String propertyKey;

    private String teamEventPropertyChangedKey;

    EmployeeType (String type, String propertyKey, String teamEventPropertyChangedKey) {

        this.type = type;
        this.propertyKey = propertyKey;
        this.teamEventPropertyChangedKey = teamEventPropertyChangedKey;
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

    public String getTeamEventPropertyChangedKey() {
        return teamEventPropertyChangedKey;
    }
}
