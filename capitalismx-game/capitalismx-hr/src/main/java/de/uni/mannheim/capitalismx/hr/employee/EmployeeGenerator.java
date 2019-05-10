package de.uni.mannheim.capitalismx.hr.employee;

import de.uni.mannheim.capitalismx.hr.employee.impl.Engineer;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;
import de.uni.mannheim.capitalismx.utils.namegenerator.NameGenerator;

/**
 * Use this class to generate employees with random name, and other meta data.
 */
public class EmployeeGenerator {

    public Employee generateEngineer () {
        NameGenerator ng = new NameGenerator();

        PersonMeta newPerson = ng.getGeneratedPersonMeta();

        return new Engineer(newPerson);
    }
}
