package de.uni.mannheim.capitalismx.hr.domain;

/**
 * Calculate the Job Satisfaction score (JSS)
 * @author duly
 */
public class JobSatisfaction {

    /**
     *
     * @param originalScale
     * @return
     */
    public double getJobSatisfactionScore (int originalScale) {
        return Math.pow(originalScale, 1.0/3.0) * 0.33;
    }
}
