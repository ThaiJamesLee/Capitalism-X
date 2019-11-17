package de.uni.mannheim.capitalismx.domain.employee;



import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class should hold the teams of this company.
 * @duly
 */
public class Team implements Serializable {

    //private List<Employee> team;
    private EmployeeType type;

    private PropertyChangeSupportList<Employee> team;

    public Team(EmployeeType type) {
        if (type == null) {
            throw new NullPointerException("EmployeeType can not be null!");
        }
        this.type = type;
        team = new PropertyChangeSupportList<>();
    }

    public Team(EmployeeType type, PropertyChangeSupportList<Employee> team) {
        if (type == null || team == null) {
            throw new NullPointerException("Null as parameter input is not allowed!");
        }
        this.type = type;
        this.team = team;
    }

    /**
     *
     * @param employee adds an Employee to the team list.
     */
    public void addEmployee(Employee employee) {
        team.add(employee);
    }

    /**
     *
     * @param employee rmeove an Employee from the team list.
     */
    public void removeEmployee(Employee employee) { team.remove(employee); }

    /**
     *
     * @author sdupper
     */
    public double calculateTotalSalaries(){
        double totalSalaries = 0.0;
        for(Employee employee : team.getList()){
            totalSalaries += employee.getSalary();
        }
        return totalSalaries;
    }

    /**
     *
     * @author sdupper
     */
    public double calculateTotalTrainingCosts(){
        double totalTrainingCosts = 0.0;
        for(Employee employee : team.getList()){
            for(Training training : employee.getTrainingsList()){
                totalTrainingCosts += training.getPrice();
            }
        }
        return totalTrainingCosts;
    }


    public EmployeeType getType() { return type; }

    public List<Employee> getTeam() {
        return team.getList();
    }

    public void setTeam (PropertyChangeSupportList<Employee> team) { this.team = team; }

    /**
     *
     * @param eventName
     * @return Returns this object.
     */
    public Team addPropertyName(String eventName) {
        team.setAddPropertyName(eventName);
        team.setRemovePropertyName(eventName);
        return this;
    }

    /**
     *
     * @param listener the propertyChangeListener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        team.addPropertyChangeListener(listener);
    }
    

}
