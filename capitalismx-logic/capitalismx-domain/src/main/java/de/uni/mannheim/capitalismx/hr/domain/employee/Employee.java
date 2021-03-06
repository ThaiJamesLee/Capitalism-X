package de.uni.mannheim.capitalismx.hr.domain.employee;

import de.uni.mannheim.capitalismx.hr.domain.employee.training.Training;
import de.uni.mannheim.capitalismx.hr.domain.employee.training.TrainingEntry;
import de.uni.mannheim.capitalismx.utils.data.LocationData;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic Employee, every employee type must inherit from this class.
 * @author duly
 */
public abstract class Employee implements Person, Serializable {

    /**
     * The current salary of the employee.
     * The salary depends on the skill level.
     */
    private double salary;

    /**
     * The skill level of the employee.
     */
    private int skillLevel;

    /**
     * The job satisfaction score for the employee.
     */
    private double jobSatisfaction;

    /**
     * The quality of work.
     */
    private double qualityOfWork;

    /**
     * The unique id. Currently not supported.
     */
    private String id;

    /**
     * first name.
     */
    private String firstName;

    /**
     * last name.
     */
    private String lastName;

    /**
     * Title are like mr oder mrs.
     */
    private String title;
    private String gender;

    private String position;

    private String eMail;

    /**
     * The employee type which must correspond to the class that inherits this class.
     */
    private EmployeeType employeeType;

    /**
     * The location data contains address of the employee.
     * In the current generation, this is not set.
     */
    private LocationData locationData;

    /**
     * The trainings this employee has received.
     */
    private List<TrainingEntry> trainingsList;

    /**
     * Constructor that takes a {@link PersonMeta} object as an argument.
     * @param metaData Use {@link PersonMeta} data for creating an employee.
     */
    public Employee(PersonMeta metaData) {
        this.firstName = metaData.getFirstName();
        this.lastName = metaData.getLastName();

        this.title = metaData.getTitle();
        this.gender = metaData.getGender();

        this.eMail = metaData.geteMail();
        this.trainingsList = new ArrayList<>();
    }

    /**
     * Only use the constructor if you do not need the other attributes.
     * @param firstName
     * @param lastName
     * @param salary
     * @param skillLevel
     */
    public Employee(String firstName, String lastName, int salary, int skillLevel) {
        this.firstName = firstName;
        this.lastName = lastName;

        this.salary = salary;
        this.skillLevel = skillLevel;
        this.trainingsList = new ArrayList<>();

    }

    /**
     * The constructor where one can set every attribute.
     * @param firstName The first name.
     * @param lastName The last name.
     * @param gender The gender.
     * @param title The title, like mr, mrs, ...
     * @param salary The salary.
     * @param skillLevel The skill level.
     */
    public Employee(String firstName, String lastName, String gender, String title, int salary, int skillLevel) {
        this.firstName = firstName;
        this.lastName = lastName;

        this.title = title;
        this.gender = gender;

        this.salary = salary;
        this.skillLevel = skillLevel;
        this.trainingsList = new ArrayList<>();

    }

    /**
     * Add the training to the list of trainings this employee did.
     * @param t The training that this employee has done.
     */
    public void addTraining(Training t, LocalDate date) {
        trainingsList.add(new TrainingEntry(t, date));
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

    public void setTrainingsList(List<TrainingEntry> trainingsList) {
        this.trainingsList = trainingsList;
    }

    public Employee setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
        return this;
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
     * @return Returns "<firstname> <lastname>"
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

    public List<TrainingEntry> getTrainingsList() {
        return trainingsList;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    /**
     *
     * @return Returns the String "{@link Employee#getName()}; {@link EmployeeType}".
     */
    @Override
    public String toString() { return getName() + "; " + getEmployeeType().toString(); }
}
