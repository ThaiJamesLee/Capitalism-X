package de.uni.mannheim.capitalismx.hr.domain;

public class JobSatisfaction {

    public double getJobSatisfactionScore (int originalScale) {
        return Math.pow(originalScale, 1.0/3.0) * 0.33;
    }
}
