package de.uni.mannheim.capitalismx.domain.employee;

import de.uni.mannheim.capitalismx.utils.data.LocationData;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic Employee, every employee type must inherit from this class.
 * @author duly
 */
public abstract class Employee implements Person, Serializable {

    private double salary;
    private int skillLevel;

    private double jobSatisfaction;
    private double qualityOfWork;

    private String id;

    private String firstName;
    private String lastName;
    private String title;
    private String gender;

    private String position;

    private String eMail;
    private EmployeeType employeeType;
    private LocationData locationData;

    private List<Training> trainingsList;

    public Employee(PersonMeta metaData) {
        this.firstName = metaData.getFirstName();
        this.lastName = metaData.getLastName();

        this.title = metaData.getTitle();
        this.gender = metaData.getGender();

        this.eMail = metaData.geteMail();
        this.trainingsList = new ArrayList<>();
    }

    public Employee(String firstName, String lastName, int salary, int skillLevel) {
        this.firstName = firstName;
        this.lastName = lastName;

        this.salary = salary;
        this.skillLevel = skillLevel;
        this.trainingsList = new ArrayList<>();

    }

    public Employee(String firstName, String lastName, String gender, String title, int salary, int skillLevel) {
        this.firstName = firstName;
        this.lastName = lastName;

        this.title = title;
        this.gender = gender;

        this.salary = salary;
        this.skillLevel = skillLevel;
        this.trainingsList = new ArrayList<>();

    }

    public void addTraining(Training t) {
        trainingsList.add(t);
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setJobSatisfaction(double jobSatisfaction) { this.jobSatisfaction = jobSatisfaction; }

    public void setSalary(double salary) { this.salary = salary; }

    public void setSkillLevel(int skillLevel) { this.skillLevel = skillLevel; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setTitle(String title) { this.title = title; }

    public void setGender(String gender) { this.gender = gender; }

    public void seteMail(String eMail) { this.eMail = eMail; }

    public void setQualityOfWork(double qualityOfWork) {
        this.qualityOfWork = qualityOfWork;
    }

    public void setLocationData(LocationData locationData) {
        this.locationData = locationData;
    }

    public void setTrainingsList(List<Training> trainingsList) {
        this.trainingsList = trainingsList;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public LocationData getLocationData() {
        return locationData;
    }

    public double getQualityOfWork() {
        return qualityOfWork;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getTitle() { return title; }

    public double getJobSatisfaction() { return jobSatisfaction; }

    public String getPosition() {
        return position;
    }

    /**
     * @return Returns "firstname lastname"
     */
    public String getName(){
        return firstName + " " + lastName;
    }

    public String getID(){
        return id;
    }

    public String getGender() { return gender; }

    public double getSalary() { return salary; }

    public int getSkillLevel() { return skillLevel; }

    public String geteMail() { return eMail; }

    public List<Training> getTrainingsList() {
        return trainingsList;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    @Override
    public String toString() { return getName() + "; " + getPosition(); }
}
