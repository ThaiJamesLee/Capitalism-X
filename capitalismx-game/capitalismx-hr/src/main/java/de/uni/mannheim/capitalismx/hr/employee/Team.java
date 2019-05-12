package de.uni.mannheim.capitalismx.hr.employee;

import de.uni.mannheim.capitalismx.hr.domain.EmployeeType;
import de.uni.mannheim.capitalismx.hr.employee.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * This class should hold the teams of this company.
 * @duly
 */
public class Team {

    private List<Employee> team;
    private EmployeeType type;

    public Team(EmployeeType type) {
        this.type = type;
        team = new ArrayList<>();
    }

    public Team(EmployeeType type, List<Employee> team) {
        this.type = type;
        this.team = team;
    }

    public EmployeeType getType() { return type; }

    public List<Employee> getTeam() {
        return team;
    }

    public void setTeam (List<Employee> team) { this.team = team; }

}
