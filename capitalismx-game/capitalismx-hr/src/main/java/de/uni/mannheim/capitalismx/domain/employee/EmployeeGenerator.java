package de.uni.mannheim.capitalismx.domain.employee;

import de.uni.mannheim.capitalismx.domain.employee.impl.Engineer;
import de.uni.mannheim.capitalismx.domain.employee.impl.SalesPerson;
import de.uni.mannheim.capitalismx.hr.exception.NoDefinedTierException;
import de.uni.mannheim.capitalismx.hr.salary.SalaryGenerator;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use this class to generate employees with random name, and other meta data.
 *
 * @author duly
 */
public class EmployeeGenerator {

    private EmployeeMarketSample employeeMarketSample;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeGenerator.class);

    private static EmployeeGenerator instance;

    private EmployeeGenerator() {
        employeeMarketSample = new EmployeeMarketSample();
    }

    public static EmployeeGenerator getInstance() {
        if(instance == null) {
            instance = new EmployeeGenerator();
        }
        return instance;
    }

    /**
     * Generate an engineer.
     * @param skillLevel generate the engineer with this skill level.
     * @return Returns a randomly generated engineer employee with set skill level
     * and salary randomly generated based of the skill level
     */
    public Employee generateEngineer(int skillLevel) {

        double salary = 0;
        Employee employee = null;
       
        try {
            salary = SalaryGenerator.getInstance().getSalary(skillLevel);

            PersonMeta newPerson = employeeMarketSample.randomChoosing();

            employee = new Engineer(newPerson);
            employee.setSkillLevel(skillLevel);
            employee.setSalary(salary);
            employee.setEmployeeType(EmployeeType.ENGINEER);

        } catch (NoDefinedTierException e) {
            logger.error(e.getMessage());
        }
        return employee;
    }

    /**
     * Generate a salesperson.
     * @param skillLevel generate the engineer with this skill level.
     * @return Returns a randomly generated salesperson employee with set skill level
     * and salary randomly generated based of the skill level
     */
    public Employee generateSalesPeople(int skillLevel) {
        double salary = 0;
        Employee employee = null;

        try {
            salary = SalaryGenerator.getInstance().getSalary(skillLevel);

            PersonMeta newPerson = employeeMarketSample.randomChoosing();

            employee = new SalesPerson(newPerson);
            employee.setSkillLevel(skillLevel);
            employee.setSalary(salary);
            employee.setEmployeeType(EmployeeType.SALESPERSON);

        } catch (NoDefinedTierException e) {
            logger.error(e.getMessage());
        }
        return employee;
    }

    public EmployeeMarketSample getEmployeeMarketSample() {
        return employeeMarketSample;
    }
}
