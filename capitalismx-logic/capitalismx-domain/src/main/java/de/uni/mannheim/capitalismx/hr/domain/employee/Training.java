package de.uni.mannheim.capitalismx.hr.domain.employee;

/**
 * Contains the type of trainings.
 * see p. 27 of the original report.
 * @author duly
 */
public enum Training {

    COURSES ("Courses",3000, 1, 1.01),
    WORKSHOP ("Workshop",5500, 2, 1.02);

    private String name;

    private int price;
    private int skillLevelImprove;
    private double salaryIncreaseFactor;

    /**
     * The constructor.
     * @param name The name of the {@link Training}.
     * @param price The price of the training.
     * @param skillLevelImprove The skill level improvement of the employee.
     * @param salaryIncreaseFactor The factor on how much the salary increases.
     */
    Training(String name, int price, int skillLevelImprove, double salaryIncreaseFactor) {
        this.price = price;
        this.skillLevelImprove = skillLevelImprove;
        this.salaryIncreaseFactor = salaryIncreaseFactor;
        this.name = name;
    }

    /**
     * Returns the price of the training.
     * @return The price.
     */
    public int getPrice() {
        return price;
    }

    /**
     * The skill level improvement of the employee.
     * @return Returns how many level the employee will improve.
     */
    public int getSkillLevelImprove() {
        return skillLevelImprove;
    }

    /**
     * Get the factor on how much the salary increases.
     * @return Returns the increased salary.
     */
    public double getSalaryIncreaseFactor() {
        return salaryIncreaseFactor;
    }

    /**
     * The name of the {@link Training}.
     * @return Returns the name of the {@link Training}.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
