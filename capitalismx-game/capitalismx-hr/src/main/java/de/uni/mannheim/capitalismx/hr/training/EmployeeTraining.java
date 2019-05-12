package de.uni.mannheim.capitalismx.hr.training;

import de.uni.mannheim.capitalismx.hr.domain.Training;
import de.uni.mannheim.capitalismx.hr.employee.Employee;

/**
 * Handles training an employee.
 *
 * @author  duly
 */
public class EmployeeTraining {

    /**
     *
     * @param e employee to train to the next level.
     * @return price for the chosen training.
     */
    public int trainEmployee(Employee e, Training t) {
        int skillLevel = e.getSkillLevel() + t.getSkillLevelImprove();
        double salary = e.getSalary() * t.getSalaryIncreaseFactor();

        e.setSalary(salary);
        e.setSkillLevel(skillLevel);

        // TODO balance the price by increasing accoring to skill level. Currently price is the same for all.
        return t.getPrice();
    }
}
