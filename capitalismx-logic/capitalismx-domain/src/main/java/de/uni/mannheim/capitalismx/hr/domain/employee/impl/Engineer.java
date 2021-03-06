package de.uni.mannheim.capitalismx.hr.domain.employee.impl;

import de.uni.mannheim.capitalismx.hr.domain.employee.Employee;
import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;

/**
 * This is the engineer employee class.
 * @author duly
 */
public class Engineer extends Employee {

    public Engineer(String firstName, String lastName, String gender, String title, int salary, int skillLevel) {
        super(firstName, lastName, gender, title, salary, skillLevel);
        super.setEmployeeType(EmployeeType.ENGINEER);
    }

    public Engineer(PersonMeta metaData) {
        super(metaData);
        super.setEmployeeType(EmployeeType.ENGINEER);
    }
}