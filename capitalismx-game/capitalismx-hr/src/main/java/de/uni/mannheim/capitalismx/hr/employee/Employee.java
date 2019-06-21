package de.uni.mannheim.capitalismx.hr.employee;

import de.uni.mannheim.capitalismx.utils.data.LocationData;
import de.uni.mannheim.capitalismx.utils.data.PersonMeta;

/**
 * Generic Employee, every employee type must inherit from this class.
 * @author duly
 */
public abstract class Employee implements Person {

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

    private LocationData locationData;

    public Employee(PersonMeta metaData) {
        this.firstName = metaData.getFirstName();
        this.lastName = metaData.getLastName();

        this.title = metaData.getTitle();
        this.gender = metaData.getGender();

        this.eMail = metaData.geteMail();
    }

    public Employee(String firstName, String lastName, int salary, int skillLevel) {
        this.firstName = firstName;
        this.lastName = lastName;

        this.salary = salary;
        this.skillLevel = skillLevel;
    }

    public Employee(String firstName, String lastName, String gender, String title, int salary, int skillLevel) {
        this.firstName = firstName;
        this.lastName = lastName;

        this.title = title;
        this.gender = gender;

        this.salary = salary;
        this.skillLevel = skillLevel;
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

}
