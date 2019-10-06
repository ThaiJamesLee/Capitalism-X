package de.uni.mannheim.capitalismx.domain.employee.impl;

import de.uni.mannheim.capitalismx.domain.employee.Employee;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;

/**
 * @author duly
 */
public class Engineer extends Employee {

    public Engineer(String firstName, String lastName, String gender, String title, int salary, int skillLevel) {
        super(firstName, lastName, gender, title, salary, skillLevel);
    }

    public Engineer(PersonMeta metaData) {
        super(metaData);
    }
}