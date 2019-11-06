package de.uni.mannheim.capitalismx.hr.department;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.employee.Employee;
import de.uni.mannheim.capitalismx.domain.employee.Team;
import de.uni.mannheim.capitalismx.domain.employee.impl.Engineer;
import de.uni.mannheim.capitalismx.domain.employee.impl.SalesPerson;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uni.mannheim.capitalismx.hr.domain.Benefit;
import de.uni.mannheim.capitalismx.hr.domain.BenefitType;
import de.uni.mannheim.capitalismx.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.hr.domain.JobSatisfaction;
import de.uni.mannheim.capitalismx.domain.employee.Training;
import de.uni.mannheim.capitalismx.hr.training.EmployeeTraining;
import de.uni.mannheim.capitalismx.utils.exception.UnsupportedValueException;

/**
 * Based on the report on p.21 - 28
 * @author duly
 */
public class HRDepartment extends DepartmentImpl {

    private static final Logger logger = LoggerFactory.getLogger(HRDepartment.class);
    
    // base cost for hiring and firing
    private static final int BASE_COST = 5000;

    private BenefitSettings benefitSettings;

    private Map<EmployeeType, Team> teams;

    // hired and fired employees.

    private PropertyChangeSupportList<Employee> hired;
    private PropertyChangeSupportList<Employee> fired;

    private static HRDepartment instance = null;

    private HRDepartment () {
        super("HR");
        this.benefitSettings = new BenefitSettings();
        this.teams = new EnumMap<>(EmployeeType.class);

        init();
    }

    private void init() {

        hired = new PropertyChangeSupportList<>();
        hired.setAddPropertyName("hireEmployee");
        hired.setRemovePropertyName("fireEmployee");

        fired = new PropertyChangeSupportList<>();
        fired.setAddPropertyName("addFiredList");
        fired.setRemovePropertyName("removeFiredList");

        teams.put(EmployeeType.ENGINEER, new Team(EmployeeType.ENGINEER).addPropertyName("engineerTeamChanged"));
        teams.put(EmployeeType.SALESPERSON, new Team(EmployeeType.SALESPERSON).addPropertyName("salespersonTeamChanged"));
    }

    public static HRDepartment getInstance() {
        if (instance == null) {
           instance = new HRDepartment();
        }
        return instance;
    }

    /**
     * Iterates over all teams and get the skill level score of each employee.
     * Calculate the average skill level.
     *
     * @return the average skill level.
     */
    public double getAverageSkillLevel() {
        int totalEmployee = 0;
        double avgSkill = 0; // total sum of skill level
        for(Map.Entry<EmployeeType, Team> entry : teams.entrySet()) {
            for (Employee e : entry.getValue().getTeam()) {
                totalEmployee++;
                avgSkill += e.getSkillLevel();
            }
        }
        if (totalEmployee > 0) {
            avgSkill /= totalEmployee;
        }
        return avgSkill;
    }

    /**
     * The report does not define the total job satisfaction score
     * @return total job satisfaction score
     */
    public double getTotalJSS() {
        double og = getOriginalScale();
        double totalJss = 0;
        try {
            totalJss = new JobSatisfaction().getJobSatisfactionScore(og);
        } catch (UnsupportedValueException e) {
            logger.error(e.getMessage());
        }
        return totalJss;
    }

    /**
     * The scale is between 0 - 27
     * @return Returns the sum of points of the current benefit settings.
     */
    private double getOriginalScale() {
        double originalScale = 0;
        for (Map.Entry<BenefitType, Benefit> entry : benefitSettings.getBenefits().entrySet()) {
            originalScale += entry.getValue().getPoints();
        }
        return originalScale;
    }

    /**
     * Update JSS and QoW for each employee.
     */
    public void calculateAndUpdateEmployeesMeta () {
        double totalJSS = getTotalJSS();
        for(Map.Entry<EmployeeType, Team> entry : teams.entrySet()) {
            for (Employee e : entry.getValue().getTeam()) {
                e.setQualityOfWork(totalJSS * 0.5 + e.getSkillLevel() * 0.5);
                e.setJobSatisfaction(totalJSS);
            }
        }
    }

    /**
     * The report does not mention how to calculate the overall quality of work KPI.
     * They only defined for single employee. We assume that the average QoW will reflect the total quality of work.
     *
     * It is defined as QoW = JSS * 0.5 + skillLevel * 0.5.
     * @return The overall quality of work.
     */
    public double getTotalQualityOfWork () {
        return getTotalJSS() * 0.5 + getAverageSkillLevel() * 0.5;
    }

    /**
     * Gets the total quality of work by employee type. The score
     * is the sum of quality of work over all employees with the specified employee type.
     * @param employeeType The employee type of interest
     * @return Returns the Quality of Work of the specified team.
     */
    public double getTotalQualityOfWorkByEmployeeType(EmployeeType employeeType) {
        Team team = teams.get(employeeType);
        List<Employee> teamList = team.getTeam();
        double jss = getTotalJSS();
        double totalQoW = 0.0;

        for (Employee e : teamList) {
            totalQoW += 0.5 * e.getSkillLevel() + 0.5 * jss;
        }
        return totalQoW;
    }




    public void setTeams(Map<EmployeeType, Team> teams) {
        this.teams = teams;
    }


    public Map<EmployeeType, Team> getTeams() {
        return teams;
    }

    public Team getEngineerTeam() {
        return teams.get(EmployeeType.ENGINEER);
    }

    public Team getSalesTeam() {
        return teams.get(EmployeeType.SALESPERSON);
    }

    /* Hiring and Firing */

    /**
     *
     * @return Returns list of hired employees.
     */
    public PropertyChangeSupportList<Employee> getHired() {
        return hired;
    }

    /**
     *
     * @return Returns list of fired employees.
     */
    public PropertyChangeSupportList<Employee> getFired() {
        return fired;
    }

    /**
     * Hire the employee. Add the employee to the corresponding team list.
     * @param employee The employee to hire.
     * @return Returns the cost of hiring.
     */
    public double hire(Employee employee) {
        if(employee == null) {
            throw new NullPointerException("Employee must be specified!");
        }
        if(employee instanceof Engineer) {
            teams.get(EmployeeType.ENGINEER).addEmployee(employee);
        } else if (employee instanceof SalesPerson) {
            teams.get(EmployeeType.SALESPERSON).addEmployee(employee);
        }
        hired.add(employee);
        //departmentBean.setHired(hired);
        //TODO hiring cost should also be dependent on the skill level
        return getHiringCost();
    }

    /**
     * Fires the employee.
     * @param employee the employee to fire.
     * @return Returns the cost of the firing.
     */
    public double fire(Employee employee) {
        if(employee == null) {
            throw new NullPointerException("Employee must be specified!");
        }
        if(employee instanceof Engineer) {
            teams.get(EmployeeType.ENGINEER).removeEmployee(employee);

        } else if (employee instanceof SalesPerson) {
            teams.get(EmployeeType.SALESPERSON).removeEmployee(employee);
        }
        //TODO firing cost should be also dependent on skill level
        fired.add(employee);
        return getFiringCost();
    }

    /**
     * See p.24
     * @return Returns the actual hiring cost per employee.
     */
    public double getHiringCost() {
        double jss = getTotalJSS()/100;
        return BASE_COST + BASE_COST*(1 - jss);
    }

    /**
     * See p.24
     * @return Returns the actual firing cost per employee.
     */
    public double getFiringCost() {
        return BASE_COST;
    }

    /* Benefits */

    public void changeBenefitSetting(Benefit benefit) {
        benefitSettings.getBenefits().put(benefit.getType(), benefit);
    }

    public BenefitSettings getBenefitSettings() {
        return benefitSettings;
    }

    public void setBenefitSettings(BenefitSettings benefitSettings) {
        this.benefitSettings = benefitSettings;
    }


    /* Trainings */

    /**
     *
     * @return Returns all possible Trainings.
     */
    public Training[] getAllTrainings() {
        return Training.values();
    }

    /**
     *
     * @param employee The employee to train.
     * @param training The type of training for the employee.
     * @return Returns the cost of the training.
     */
    public double trainEmployee (Employee employee, Training training) {
        return EmployeeTraining.getInstance().trainEmployee(employee, training);
    }

    /**
     *
     * @author sdupper
     */
    public double calculateTotalSalaries(){
        double totalSalaries = 0.0;
        for(Map.Entry<EmployeeType, Team> entry : teams.entrySet()) {
            totalSalaries += entry.getValue().calculateTotalSalaries();
        }
        return totalSalaries;
    }

    /**
     *
     * @author sdupper
     */
    public double calculateTotalTrainingCosts(){
        double totalTrainingCosts = 0.0;
        for(Map.Entry<EmployeeType, Team> entry : teams.entrySet()) {
            totalTrainingCosts += entry.getValue().calculateTotalTrainingCosts();
        }
        return totalTrainingCosts;
    }

    public static void setInstance(HRDepartment instance) {
        HRDepartment.instance = instance;
    }

    /**
     * Register the propertychangelistener to all propertychangesupport objects.
     * @param listener
     */
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        for(Map.Entry<EmployeeType, Team> entry : teams.entrySet()) {
            entry.getValue().addPropertyChangeListener(listener);
        }
    }
}
