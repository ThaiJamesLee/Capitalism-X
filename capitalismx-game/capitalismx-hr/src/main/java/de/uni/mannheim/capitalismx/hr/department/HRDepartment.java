package de.uni.mannheim.capitalismx.hr.department;

import de.uni.mannheim.capitalismx.hr.domain.Benefit;
import de.uni.mannheim.capitalismx.hr.domain.BenefitTypes;
import de.uni.mannheim.capitalismx.hr.domain.EmployeeType;
import de.uni.mannheim.capitalismx.hr.domain.JobSatisfaction;
import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.hr.employee.Team;
import de.uni.mannheim.capitalismx.utils.exception.UnsupportedValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Based on the report on p.21 - 28
 * @author duly
 */
public class HRDepartment {

    private static final Logger logger = LoggerFactory.getLogger(HRDepartment.class);

    // base cost for hiring and firing
    private static final int BASE_COST = 5000;

    private BenefitSettings benefitSettings;

    private Map<EmployeeType, Team> teams;

    private static HRDepartment instance = null;

    private HRDepartment () {
        this.benefitSettings = new BenefitSettings();
        this.teams = new EnumMap<>(EmployeeType.class);
    }

    public static HRDepartment getInstance() {
        if (instance == null) {
           instance = new HRDepartment();
        }
        return instance;
    }

    /**
     * See p.24
     * @return Returns the actual hiring cost
     */
    public double getHiringCost() {
        double jss = getTotalJSS()/100;
        return BASE_COST + BASE_COST*(1-jss);
    }

    /**
     * See p.24
     * @return Returns the actual firing cost
     */
    public double getFiringCost() {
        return BASE_COST;
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
        for (Map.Entry<BenefitTypes, Benefit> entry : benefitSettings.getBenefits().entrySet()) {
            originalScale += entry.getValue().getPoints();
        }
        return originalScale;
    }

    /**
     * Update JSS and QoW for each employee
     */
    public void calculateAndUpdateEmployeesMeta () {
        double originalScale = getOriginalScale();
        double totalJSS = getTotalJSS();
        for(Map.Entry<EmployeeType, Team> entry : teams.entrySet()) {
            for (Employee e : entry.getValue().getTeam()) {
                e.setQualityOfWork(totalJSS * 0.5 + e.getSkillLevel() * 0.5);
                e.setJobSatisfaction(originalScale);
            }
        }
    }

    /**
     * The report does not mention how to calculate the overall quality of work KPI.
     * They only defined for single employee. We assume that the average QoW will reflect the total quality of work.
     *
     * It is defined as QoW = JSS * 0.5 + skillLevel * 0.5
     * @return The overall quality of work.
     */
    public double getTotalQualityOfWork () {
        return getTotalJSS() * 0.5 + getAverageSkillLevel() * 0.5;
    }


    public void setBenefitSettings(BenefitSettings benefitSettings) {
        this.benefitSettings = benefitSettings;
    }

    public void setTeams(Map<EmployeeType, Team> teams) {
        this.teams = teams;
    }

    public BenefitSettings getBenefitSettings() {
        return benefitSettings;
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
}
