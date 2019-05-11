package de.uni.mannheim.capitalismx.hr.domain;

public enum Training {

    COURSES (3000, 1, 1.01),
    WORKSHOP (5500, 2, 1.02);

    private int price;
    private int skillLevelImprove;
    private double salaryIncreaseFactor;

    Training(int price, int skillLevelImprove, double salaryIncreaseFactor) {
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
        switch (this) {
            case COURSES: return "Courses";
            case WORKSHOP: return "Workshop";
            default: return "Training";
        }
    }
}
