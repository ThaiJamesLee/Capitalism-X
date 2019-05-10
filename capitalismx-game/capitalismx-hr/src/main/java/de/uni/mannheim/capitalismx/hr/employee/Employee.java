package de.uni.mannheim.capitalismx.hr.employee;

import de.uni.mannheim.capitalismx.utils.data.PersonMeta;

/**
 * @author duly
 */
public abstract class Employee implements Person {

    private int salary;
    private int skillLevel;

    private int jobSatisfaction;

    private String id;

    private String firstName;
    private String lastName;

    private String title;
    private String gender;

    private String position;

    private String eMail;

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

    public void setJobSatisfaction(int jobSatisfaction) { this.jobSatisfaction = jobSatisfaction; }

    public void setSalary(int salary) { this.salary = salary; }

    public void setSkillLevel(int skillLevel) { this.skillLevel = skillLevel; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setTitle(String title) { this.title = title; }

    public void setGender(String gender) { this.gender = gender; }

    public void seteMail(String eMail) { this.eMail = eMail; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getTitle() { return title; }

    public int getJobSatisfaction() { return jobSatisfaction; }

    public String getPosition() {
        return position;
    }

    /**
     *
     * @return Returns "firstname lastname"
     */
    public String getName(){
        return firstName + " " + lastName;
    }

    public String getID(){
        return id;
    }

    public String getGender() { return gender; }

    public int getSalary() { return salary; }

    public int getSkillLevel() { return skillLevel; }

    public String geteMail() { return eMail; }

}
