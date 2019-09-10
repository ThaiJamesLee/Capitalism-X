package de.uni.mannheim.capitalismx.hr.employee;

import de.uni.mannheim.capitalismx.hr.domain.Salary;
import de.uni.mannheim.capitalismx.hr.exception.NoDefinedTierException;
import de.uni.mannheim.capitalismx.hr.salary.SalaryGenerator;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class caches pre-generated person entities.
 * It is a stack of persons that are pre-generated.
 * The goal is to reduce the number API-calls.
 * @author duly
 */
public class EmployeeMarketSample {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeMarketSample.class);

    private static final int MINIMUM_SAMPLE_SIZE = 500;
    private static final String EMPLOYEE_SAMPLE_FILE_DIR = "data" + File.separator + "employeesample.capx";

    /* Idea: depending on level of progress, the chance of being able to higher employees with higher skill level increases.
    * Therefore, we currently have 4 different distribution of the employee sample */
    private static final double[][] DISTRIBUTION =
            {
                    {50, 24, 21, 3, 2},
                    {50, 22, 20, 5, 3},
                    {45, 20, 15, 6, 4},
                    {40, 15, 20, 15, 10}
            };

    private String filePath;
    private List<Employee> potentialEmployeeSample;


    public EmployeeMarketSample() {
        filePath = System.getProperty("user.dir") + File.separator + EMPLOYEE_SAMPLE_FILE_DIR;
        potentialEmployeeSample = new ArrayList<>();
    }

    /**
     *
     * @param rootDir The parent folder of the data folder, that contains the employeesample.capx file.
     */
    public EmployeeMarketSample(String rootDir) {
        filePath = rootDir + File.separator + EMPLOYEE_SAMPLE_FILE_DIR;
        potentialEmployeeSample = new ArrayList<>();
    }

    /**
     * Creates the engineer sample with size of MINIMUM_SAMPLE_SIZE.
     * @param distribution is the pre-defined distribution index in the DISTRIBUTION array.
     */
    private void createEngineerSample(int distribution) {
        double[] dList = DISTRIBUTION[distribution];
        for (int k = 0; k < dList.length; k++) {

            int size = (int)(MINIMUM_SAMPLE_SIZE * dList[k]/100);

            int skillLevel = RandomNumberGenerator.getRandomInt((int)Salary.values()[k].getLowerLevel(), (int)Salary.values()[k].getUpperLevel());
            for (int i = 0; i < size; i++) {

                try {
                    Employee emp = EmployeeGenerator.getInstance().generateEngineer(skillLevel);
                    emp.setSalary(SalaryGenerator.getInstance().getSalary(skillLevel));
                    potentialEmployeeSample.add(emp);

                    Thread.sleep(500);

                } catch (NoDefinedTierException e) {
                    logger.error(e.getMessage());
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                    Thread.currentThread().interrupt();
                }

            }
        }
        Collections.shuffle(potentialEmployeeSample);
    }

    /**
     * Creates the sales person sample with size of MINIMUM_SAMPLE_SIZE.
     * @param distribution is the pre-defined distribution index in the DISTRIBUTION array.
     */
    private void createSalesPeopleSample(int distribution) {
        double[] dList = DISTRIBUTION[distribution];
        for (int k = 0; k < dList.length; k++) {

            int size = (int)(MINIMUM_SAMPLE_SIZE * dList[k]/100);

            int skillLevel = RandomNumberGenerator.getRandomInt((int)Salary.values()[k].getLowerLevel(), (int)Salary.values()[k].getUpperLevel());
            for (int i = 0; i < size; i++) {

                try {
                    Employee emp = EmployeeGenerator.getInstance().generateSalesPeople(skillLevel);
                    emp.setSalary(SalaryGenerator.getInstance().getSalary(skillLevel));
                    potentialEmployeeSample.add(emp);

                    Thread.sleep(500);

                } catch (NoDefinedTierException e) {
                    logger.error(e.getMessage());
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }
        Collections.shuffle(potentialEmployeeSample);
    }

    public void createSample(int distribution) {
        createEngineerSample(distribution);
        createSalesPeopleSample(distribution);
    }

    /**
     * Load the employee stack file.
     */
    public void loadEmployeeSample() {
        List<Employee> employeesStack = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(filePath)))) {
            employeesStack = (List<Employee>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        potentialEmployeeSample = employeesStack;

        if(employeesStack == null) {
            throw new NullPointerException("Loading was not successful!");
        }
    }

    /**
     *
     * @param stack the pre-generated employee List to be saved.
     */
    public void saveEmployeeSample(List<Employee> stack) {
        File file = new File(filePath);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(stack);

            objectOutputStream.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Chooses one employee from the sample randomly.
     * @return Returns an Employee from the potentialEmployeeSample.
     */
    public Employee randomChoosing() {
        int index = RandomNumberGenerator.getRandomInt(0, potentialEmployeeSample.size()-1);
        Employee e = potentialEmployeeSample.get(index);
        potentialEmployeeSample.remove(index);
        return e;
    }

    public List<Employee> getPotentialEmployeeSample() {
        return potentialEmployeeSample;
    }

    public String getFilePath() {
        return filePath;
    }
}
