package de.uni.mannheim.capitalismx.domain.employee.impl;

import de.uni.mannheim.capitalismx.domain.employee.Employee;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;

import java.util.ResourceBundle;

/**
 * The HR Worker has a capacity that limits the amount of employees he can manage.
 * @author duly
 *
 * @since 1.0.0
 */
public class HRWorker extends Employee {

    /**
     * The amount of employees he can manage.
     */
    private int capacity;

    /**
     * The maximum capacity the HR Worker can have. This is fix.
     */
    private int maxCapacity;

    private static final String PROPERTIES_FILE = "domain-defaults";
    private static final String MAX_CAPACITY_PROPERTY = "hr.worker.capacity.max";
    private static final String DEFAULT_CAPACITY_PROPERTY = "hr.worker.capacity.default";

    public HRWorker(PersonMeta metaData) {
        super(metaData);
        init();
    }

    public HRWorker(String firstName, String lastName, String gender, String title, int salary, int skillLevel) {
        super(firstName, lastName, gender, title, salary, skillLevel);
        init();
    }

    /**
     * Initialize default values.
     */
    private void init() {
        ResourceBundle defaultBundle = ResourceBundle.getBundle(PROPERTIES_FILE);

        capacity = Integer.parseInt(defaultBundle.getString(DEFAULT_CAPACITY_PROPERTY));
        maxCapacity = Integer.parseInt(defaultBundle.getString(MAX_CAPACITY_PROPERTY));

    }

    /**
     *
     * @return Returns the current capacity of the HR Worker.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     *
     * @param capacity The new capacity;
     *
     * @throws IllegalArgumentException Throws this exception, when the argument receives a value higher than the maximum capacity value.
     */
    public void setCapacity(int capacity) {
        if (capacity > this.maxCapacity) {
            throw new IllegalArgumentException("The new capacity must be smaller than the maximum capacity!");
        }
        this.capacity = capacity;
    }

    /**
     *
     * @return Returns the maximum capacity of an HR employee.
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }
}
