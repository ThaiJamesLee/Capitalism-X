package de.uni.mannheim.capitalismx.hr.teams;

import de.uni.mannheim.capitalismx.hr.employee.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * This class should hold the teams of this company.
 * @duly
 */
public abstract class Team {

    private List<Employee> team;

    public Team() {
        team = new ArrayList<>();
    }

    public Team(List<Employee> team) {
        this.team = team;
    }

    public List<Employee> getTeam() {
        return team;
    }

}
