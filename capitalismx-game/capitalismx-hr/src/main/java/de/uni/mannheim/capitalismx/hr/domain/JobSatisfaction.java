package de.uni.mannheim.capitalismx.hr.domain;

/**
 * Calculate the Job Satisfaction score (JSS)
 * @author duly
 */
public class JobSatisfaction {

    /**
     * See p.26
     * Take the 3rd root of the original scale and multiply by 0.33 (?)
     *
     * @param originalScale the original scale that was composed of different factors that influences the
     *                      job satisfaction
     * @return The job satisfaction score based on the original scale
     */
    public double getJobSatisfactionScore (double originalScale) {
        return Math.pow(originalScale, 1.0/3.0) * 0.33;
    }
}
