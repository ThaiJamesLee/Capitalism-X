package de.uni.mannheim.capitalismx.hr.domain.employee;

/**
 * see p. 27
 * @author duly
 */
public enum Training {

    COURSES ("Courses",3000, 1, 1.01),
    WORKSHOP ("Workshop",5500, 2, 1.02);

    private String name;

    private int price;
    private int skillLevelImprove;
    private double salaryIncreaseFactor;

    Training(String name, int price, int skillLevelImprove, double salaryIncreaseFactor) {
        this.price = price;
        this.skillLevelImprove = skillLevelImprove;
        this.salaryIncreaseFactor = salaryIncreaseFactor;
    }

    public int getPrice() {
        return price;
    }

    public int getSkillLevelImprove() {
        return skillLevelImprove;
    }

    public double getSalaryIncreaseFactor() {
        return salaryIncreaseFactor;
    }

    @Override
    public String toString() {
        return name;
    }
}
