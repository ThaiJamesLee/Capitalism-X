package de.uni.mannheim.capitalismxhr.employee;

public abstract class Employee implements Person{

    private int salary;
    private int skillLevel;

    private String id;

    private String name;

    public Employee(String name, int salary, int skillLevel) {
        this.name = name;
        this.salary = salary;
        this.skillLevel = skillLevel;
    }

    public String getName(){
        return name;
    }

    public String getID(){
        return id;
    }

}
