package de.uni.mannheim.capitalismx.hr.training;

import de.uni.mannheim.capitalismx.hr.domain.employee.Employee;
import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.hr.domain.employee.training.Training;
import de.uni.mannheim.capitalismx.hr.domain.employee.impl.HRWorker;
import de.uni.mannheim.capitalismx.hr.domain.SalaryTier;
import de.uni.mannheim.capitalismx.hr.domain.employee.training.TrainingEntry;

import java.time.LocalDate;

/**
 * Handles training an employee.
 *
 * @author  duly
 */
public class EmployeeTraining {

    private static EmployeeTraining instance;

    private EmployeeTraining() {}

    public static EmployeeTraining getInstance() {
        if(instance == null) {
            instance = new EmployeeTraining();
        }
        return instance;
    }

    /**
     * If the employee is already max level, then you pay his salary as punishment.
     * @param e employee to train to the next level.
     * @param t the training.
     * @param gameDate The current game date.
     *
     * @return price for the chosen training.
     */
    public Double trainEmployee(Employee e, Training t, LocalDate gameDate) {
        if(e.getSkillLevel() < SalaryTier.getMaxTier().getUpperLevel()) {
            int skillLevel = e.getSkillLevel() + t.getSkillLevelImprove();
            e.setSkillLevel(skillLevel);

            double salary = e.getSalary() * t.getSalaryIncreaseFactor();
            e.setSalary(salary);

            e.addTraining(t, gameDate);

            // increases the capacity of the HRWorker, if the object is an instance of HRWorker
            increaseHRCapacity(e);
            return (double)t.getPrice();
        }
        // TODO balance the price by increasing according to skill level. Currently price is the same for all.
        return e.getSalary();
    }

    /**
     * Increases the capacity if the Employee is of the type HRWorker and
     * the maximum capacity is not reached.
     *
     * @param e {@link Employee} The HRWorker.
     */
    private void increaseHRCapacity(Employee e) {
        if(e.getEmployeeType().equals(EmployeeType.HR_WORKER)) {
            HRWorker hrWorker = (HRWorker)e;
            int currentCapacity = hrWorker.getCapacity();

            if(currentCapacity < hrWorker.getMaxCapacity()) {
                double newCapacity = e.getSkillLevel()/2.0;
                hrWorker.setCapacity((int) newCapacity);
            }
        }
    }
}
