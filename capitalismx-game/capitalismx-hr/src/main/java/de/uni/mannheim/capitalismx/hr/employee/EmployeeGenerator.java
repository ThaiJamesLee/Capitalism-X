package de.uni.mannheim.capitalismx.hr.employee;

import de.uni.mannheim.capitalismx.hr.employee.impl.Engineer;
import de.uni.mannheim.capitalismx.hr.exception.NoDefinedTierException;
import de.uni.mannheim.capitalismx.hr.salary.SalaryGenerator;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;
import de.uni.mannheim.capitalismx.utils.namegenerator.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use this class to generate employees with random name, and other meta data.
 *
 * @author duly
 */
public class EmployeeGenerator {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeGenerator.class);

    /**
     *
     * @param skillLevel generate the engineer with this skill level.
     * @return Returns a randomly generated engineer employee with set skill level
     * and salary randomly generated based of the skill level
     */
    public Employee generateEngineer (int skillLevel) {

        int salary = 0;
        Employee employee = null;

        try {
            salary = new SalaryGenerator().getSalary(skillLevel);
            NameGenerator ng = new NameGenerator();

            PersonMeta newPerson = ng.getGeneratedPersonMeta();

            employee = new Engineer(newPerson);
            employee.setSkillLevel(skillLevel);
            employee.setSalary(salary);

        } catch (NoDefinedTierException e) {
            logger.error(e.getMessage());
        }
        return employee;
    }
}
