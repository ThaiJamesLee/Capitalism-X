package de.uni.mannheim.capitalismx.hr.domain.employee.training;

/**
 * A holder class for the {@link Training} enum data.
 *
 * @author duly
 *
 * @since 1.0.0
 */
public class TrainingData {

    /**
     * The improvement of the skill level after training.
     */
    private int skillLevelImprove;

    /**
     * The increased salary after training.
     */
    private double salaryIncreaseFactor;

    /**
     * The price for the training.
     */
    private int price;

    public TrainingData(int price, int skillLevelImprove, double salaryIncreaseFactor) {
        this.salaryIncreaseFactor = salaryIncreaseFactor;
        this.skillLevelImprove = skillLevelImprove;
        this.price = price;
    }

    /**
     * @return  Returns the increased salary after training.
     */
    public double getSalaryIncreaseFactor() {
        return salaryIncreaseFactor;
    }


    /**
     * @return Returns the improvement of the skill level after training.
     */
    public int getSkillLevelImprove() {
        return skillLevelImprove;
    }

    /**
     *
     * @return Returns the price for the training.
     */
    public int getPrice() {
        return price;
    }
}
