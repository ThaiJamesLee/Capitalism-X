package de.uni.mannheim.capitalismx.hr.domain.employee;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Defines Employee types available.
 * @author duly
 */
public enum EmployeeType {

    /**
     * The {@link de.uni.mannheim.capitalismx.hr.domain.employee.impl.Engineer}, contains the {@link ResourceBundle} property key and also the team key for {@link java.beans.PropertyChangeSupport}.
     */
    ENGINEER("Engineer", "name.engineer", "engineerTeamChanged"),

    /**
     * The {@link de.uni.mannheim.capitalismx.hr.domain.employee.impl.SalesPerson}, contains the {@link ResourceBundle} property key and also the team key for {@link java.beans.PropertyChangeSupport}.
     */
    SALESPERSON("Salesperson", "name.salesperson", "salespersonTeamChanged"),

    /**
     * The {@link de.uni.mannheim.capitalismx.hr.domain.employee.impl.ProductionWorker}, contains the {@link ResourceBundle} property key and also the team key for {@link java.beans.PropertyChangeSupport}.
     */
    PRODUCTION_WORKER("Production Worker", "name.productionworker", "productionworkerTeamChanged"),

    /**
     * The {@link de.uni.mannheim.capitalismx.hr.domain.employee.impl.HRWorker}, contains the {@link ResourceBundle} property key and also the team key for {@link java.beans.PropertyChangeSupport}.
     */
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

    /**
     * This the key used for changes in Teams in the HRDepartment.
     * @return Returns the team event property key.
     */
    public String getTeamEventPropertyChangedKey() {
        return teamEventPropertyChangedKey;
    }
}
