package de.uni.mannheim.capitalismx.domain.employee.impl;

import de.uni.mannheim.capitalismx.domain.employee.Employee;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;

/**
 * @author duly
 *
 * @since 1.0.0
 */
public class ProductionWorker extends Employee {

    public ProductionWorker(PersonMeta metaData) {
        super(metaData);
    }

    public ProductionWorker(String firstName, String lastName, String gender, String title, int salary, int skillLevel) {
        super(firstName, lastName, gender, title, salary, skillLevel);
    }
}
