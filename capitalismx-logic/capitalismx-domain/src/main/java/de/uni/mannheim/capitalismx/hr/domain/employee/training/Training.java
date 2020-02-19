package de.uni.mannheim.capitalismx.hr.domain.employee.training;

import java.util.ResourceBundle;

/**
 * Contains the type of trainings.
 * see p. 27 of the original report.
 *
 * See in resources/domain-defaults.properties for the values.
 * @author duly
 */
public enum Training {

    COURSES ("courses"),
    WORKSHOP ("workshop");

    private static final String PROPERTIES_FILE = "domain-defaults";
    private static final String SKILL_LEVEL_IMPROVE_PROPERTY_PREFIX = "training.skill.level.improve.";
    private static final String SALARY_INCREASE_FACTOR_PROPERTY_PREFIX = "training.salary.increase.factor.";
    private static final String COST_PROPERTY_PREFIX = "training.cost.";

    private String name;

    /**
     * Contains the skillLevelImprove and the salaryIncreaseFactor.
     */
    private TrainingData trainingData;

    /**
     * The constructor.
     * @param name The name of the {@link Training}.
     */
    Training(String name) {
        this.name = name;
        init();
    }

    private void init() {
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE);
        int price = Integer.parseInt(bundle.getString(COST_PROPERTY_PREFIX + this.name));
        int skillLevelImprove = Integer.parseInt(bundle.getString(SKILL_LEVEL_IMPROVE_PROPERTY_PREFIX + this.name));
        double salaryIncreaseFactor = Double.parseDouble(bundle.getString(SALARY_INCREASE_FACTOR_PROPERTY_PREFIX + this.name));

        this.trainingData = new TrainingData(price, skillLevelImprove, salaryIncreaseFactor);
    }

    /**
     * Returns the price of the training.
     * @return The price.
     */
    public int getPrice() {
        return trainingData.getPrice();
    }

    /**
     * The skill level improvement of the employee.
     * @return Returns how many level the employee will improve.
     */
    public int getSkillLevelImprove() {
        return trainingData.getSkillLevelImprove();
    }

    /**
     * Get the factor on how much the salary increases.
     * @return Returns the increased salary.
     */
    public double getSalaryIncreaseFactor() {
        return trainingData.getSalaryIncreaseFactor();
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
