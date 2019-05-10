package de.uni.mannheim.capitalismx.hr.employee;

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

    private String position;

    public Employee(String firstName, String lastName,int salary, int skillLevel) {
        this.firstName = firstName;
        this.lastName = lastName;

        this.salary = salary;
        this.skillLevel = skillLevel;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setJobSatisfaction(int jobSatisfaction) { this.jobSatisfaction = jobSatisfaction; }

    public int getJobSatisfaction() { return jobSatisfaction; }

    public String getPosition() {
        return position;
    }

    public String getName(){
        return firstName + " " + lastName;
    }

    public String getID(){
        return id;
    }

    public int getSalary() { return salary; }

    public int getSkillLevel() { return skillLevel; }

}
