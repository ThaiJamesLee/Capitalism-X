package de.uni.mannheim.capitalismx.domain.employee.impl;

import de.uni.mannheim.capitalismx.domain.employee.Employee;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;

/**
 * @author duly
 */
public class SalesPerson extends Employee {

    public SalesPerson(PersonMeta metaData) {
        super(metaData);
    }

    public SalesPerson(String firstName, String lastName, String gender, String title, int salary, int skillLevel) {
        super(firstName, lastName, gender, title, salary, skillLevel);
    }
}
