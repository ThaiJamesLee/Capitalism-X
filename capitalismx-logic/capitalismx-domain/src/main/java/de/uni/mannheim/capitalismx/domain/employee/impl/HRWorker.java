package de.uni.mannheim.capitalismx.domain.employee.impl;

import de.uni.mannheim.capitalismx.domain.employee.Employee;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;

/**
 * The HR Worker has a capacity that limits the amount of employees he can manage.
 * @author duly
 */
public class HRWorker extends Employee {

    private int capacity;

    public HRWorker(PersonMeta metaData) {
        super(metaData);
        capacity = 10;
    }

    public HRWorker(String firstName, String lastName, String gender, String title, int salary, int skillLevel) {
        super(firstName, lastName, gender, title, salary, skillLevel);
        capacity = 10;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
