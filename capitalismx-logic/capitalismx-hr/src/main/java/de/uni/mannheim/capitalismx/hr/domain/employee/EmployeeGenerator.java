package de.uni.mannheim.capitalismx.hr.domain.employee;

import de.uni.mannheim.capitalismx.hr.domain.employee.impl.Engineer;
import de.uni.mannheim.capitalismx.hr.domain.employee.impl.HRWorker;
import de.uni.mannheim.capitalismx.hr.domain.employee.impl.SalesPerson;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.hr.domain.EmployeeTier;
import de.uni.mannheim.capitalismx.hr.exception.NoDefinedTierException;
import de.uni.mannheim.capitalismx.hr.salary.SalaryGenerator;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Use this class to generate employees with random name, and other meta data.
 *
 * @author duly
 */
public class EmployeeGenerator {

    private EmployeeMarketSample employeeMarketSample;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeGenerator.class);

    private static EmployeeGenerator instance;

    private HRDepartment department;

    private EmployeeGenerator() {
        employeeMarketSample = new EmployeeMarketSample();
    }

    public static EmployeeGenerator getInstance() {
        if(instance == null) {
            instance = new EmployeeGenerator();
        }
        return instance;
    }

    public static EmployeeGenerator createInstance() {
        return new EmployeeGenerator();
    }

    public void setDepartment(HRDepartment department) {
        this.department = department;
    }

    /**
     * Create an employee randomly. The skill level is dependent on the distribution of the given skill level of the
     * HR Department.
     *
     * @param type The employee type.
     * @return Returns a random employee by type.
     */
    public Employee createRandomEmployee(EmployeeType type) {
        if(department == null) {
            throw new NullPointerException("You must set a HRDepartment that contains a distribution first!");
        }
        Map<String, Double> distribution = department.getCurrentEmployeeDistribution();
        int skillLevel = getRandomSkillLevel(distribution);
        return generateEmployee(skillLevel, type);
    }

    /**
     * Creates a pot according to the distribution to draw randomly a skill level.
     * @param distribution The employee distribution.
     * @return Returns the lower-bound skill level.
     */
    private int getRandomSkillLevel(Map<String, Double> distribution) {
        List<EmployeeTier> randomPot = new ArrayList<>();
        for(Map.Entry<String, Double> entry : distribution.entrySet()) {
            int number = (int) (entry.getValue() * 100);
            for(int i=0; i<number; i++) {
                randomPot.add(EmployeeTier.getEmployeeTierByName(entry.getKey()));
            }
        }
        Collections.shuffle(randomPot);
        int drawNumber = RandomNumberGenerator.getRandomInt(0, randomPot.size() - 1);
        EmployeeTier draw = randomPot.get(drawNumber);

        return draw.getInitLevel();
}


    /**
     * This methods creates an employee using the {@link EmployeeFactory} and adds skill level and salary afterwards.
     * @param skillLevel the skill level of the employee that is to be generated.
     * @param type the employee type.
     * @return Returns an employee with given skill level and employee type.
     */
    private Employee generateEmployee(int skillLevel, EmployeeType type) {
        double salary = 0;
        Employee employee = null;

        try {
            salary = SalaryGenerator.getInstance().getSalary(skillLevel);

            // retrieves randomly generated information
            PersonMeta newPerson = employeeMarketSample.randomChoosing();

            employee = EmployeeFactory.getEmployee(type, newPerson);
            employee.setSkillLevel(skillLevel);
            employee.setSalary(salary);
            employee.setEmployeeType(type);

            if(type.equals(EmployeeType.HR_WORKER)) {
                HRWorker hrWorker = (HRWorker) employee;
                double newCapacity = skillLevel/2.0;
                hrWorker.setCapacity((int) newCapacity);
            }

        } catch (NoDefinedTierException e) {
            logger.error(e.getMessage());
        }
        return employee;

    }

    /**
     * Generate an engineer.
     * @deprecated Use {@link EmployeeGenerator#createRandomEmployee(EmployeeType)} instead.
     *
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
     * @deprecated Use {@link EmployeeGenerator#createRandomEmployee(EmployeeType)} instead.
     *
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
}
