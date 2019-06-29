package de.uni.mannheim.capitalismx.hr.employee;

import de.uni.mannheim.capitalismx.hr.domain.EmployeeType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class should hold the teams of this company.
 * @duly
 */
public class Team implements Serializable {

    private List<Employee> team;
    private EmployeeType type;

    public Team(EmployeeType type) {
        if (type == null) {
            throw new NullPointerException("EmployeeType can not be null!");
        }
        this.type = type;
        team = new ArrayList<>();
    }

    public Team(EmployeeType type, List<Employee> team) {
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


    public EmployeeType getType() { return type; }

    public List<Employee> getTeam() {
        return team;
    }

    public void setTeam (List<Employee> team) { this.team = team; }

}
