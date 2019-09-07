package de.uni.mannheim.capitalismx.hr.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * This class caches pre-generated person entities.
 * It is a stack of persons that are pre-generated.
 * The goal is to reduce the number API-calls.
 * @author duly
 */
public class EmployeeMarketStack {

    public static final int MINIMUM_STACK_SIZE = 100;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeMarketStack.class);
    private static final String EMPLOYEE_STACK_FILE_DIR = "data" + File.separator + "employeestack.capx";

    private String filePath;
    private List<Employee> potentialEmployeeStack;


    public EmployeeMarketStack() {
        filePath = System.getProperty("user.dir") + File.separator + EMPLOYEE_STACK_FILE_DIR;
    }

    /**
     *
     * @param rootDir The parent folder of the data folder, that contains the employeestack.capx file.
     */
    public EmployeeMarketStack(String rootDir) {
        filePath = rootDir + File.separator + EMPLOYEE_STACK_FILE_DIR;
    }

    /**
     * Load the employee stack file.
     */
    public List<Employee> loadEmployeeStack() {
        List<Employee> employeesStack = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(filePath)))) {
            employeesStack = (List<Employee>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        return employeesStack;
    }

    /**
     *
     * @param stack the pre-generated employee List to be saved.
     */
    public void saveEmployeeStack(List<Employee> stack) {
        File file = new File(filePath);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(stack);

            objectOutputStream.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String getFilePath() {
        return filePath;
    }



}
