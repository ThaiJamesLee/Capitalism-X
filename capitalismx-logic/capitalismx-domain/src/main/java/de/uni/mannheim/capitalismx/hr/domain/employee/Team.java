package de.uni.mannheim.capitalismx.hr.domain.employee;



import de.uni.mannheim.capitalismx.hr.domain.employee.training.Training;
import de.uni.mannheim.capitalismx.hr.domain.employee.training.TrainingEntry;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.List;

/**
 * This class should hold the teams of this company.
 * @author duly
 *
 * @since 1.0.0
 */
public class Team implements Serializable {

    /**
     * Each team is associated with an {@link EmployeeType}.
     */
    private EmployeeType type;

    /**
     * The team containing the list of {@link Employee} corresponding {@link EmployeeType}.
     */
    private PropertyChangeSupportList<Employee> team;

    /**
     *  The constructor.
     * @param type The {@link EmployeeType}.
     *
     * @throws IllegalArgumentException Thrown when parameter is null.
     */
    public Team(EmployeeType type) {
        if (type == null) {
            throw new IllegalArgumentException("EmployeeType can not be null!");
        }
        this.type = type;
        team = new PropertyChangeSupportList<>();
    }

    /**
     * The constructor.
     * @param type The {@link EmployeeType}.
     * @param team The list of Employees.
     *
     * @throws IllegalArgumentException Thrown when parameter is null.
     */
    public Team(EmployeeType type, PropertyChangeSupportList<Employee> team) {
        if (type == null || team == null) {
            throw new IllegalArgumentException("Null as parameter input is not allowed!");
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
     * Calculates the total training costs of a team.
     */
    public double calculateTotalTrainingCosts(){
        double totalTrainingCosts = 0.0;
        for(Employee employee : team.getList()){
            for(TrainingEntry training : employee.getTrainingsList()){
                totalTrainingCosts += training.getTraining().getPrice();
            }
        }
        return totalTrainingCosts;
    }

    /**
     *
     * @return Returns the {@link EmployeeType} of this team.
     */
    public EmployeeType getType() { return type; }

    /**
     *
     * @return Returns the team list.
     */
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

    /**
     *
     * @return Returns the average jss score for the team.
     */
    public double getAverageJobSatisfactionScore() {
        double totalSum = 0.0;
        if(team.size() == 0) {
        	return 0.0;
        }
        for(Employee employee : team.getList()){
            totalSum += employee.getJobSatisfaction();
        }
        return totalSum / team.size();
    }

}
