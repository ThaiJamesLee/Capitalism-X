package de.uni.mannheim.capitalismx.hr.domain.employee.impl;

import de.uni.mannheim.capitalismx.hr.domain.employee.Employee;
import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;

/**
 * @author duly
 *
 * @since 1.0.0
 */
public class SalesPerson extends Employee {

    public SalesPerson(PersonMeta metaData) {
        super(metaData);
    }

    public SalesPerson(String firstName, String lastName, String gender, String title, int salary, int skillLevel) {
        super(firstName, lastName, gender, title, salary, skillLevel);
        super.setEmployeeType(EmployeeType.SALESPERSON);
    }
}
