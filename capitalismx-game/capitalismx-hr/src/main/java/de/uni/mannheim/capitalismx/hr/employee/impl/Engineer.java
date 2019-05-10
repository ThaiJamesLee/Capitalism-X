package de.uni.mannheim.capitalismx.hr.employee.impl;

import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;

public class Engineer extends Employee {

    public Engineer(String firstName, String lastName, int salary, int skillLevel) {
        super(firstName, lastName, salary, skillLevel);
    }

    public Engineer(PersonMeta metaData) {
        super(metaData);
    }
}
