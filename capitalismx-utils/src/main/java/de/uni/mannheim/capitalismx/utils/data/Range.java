package de.uni.mannheim.capitalismx.utils.data;

import java.io.Serializable;

/**
 * This class is a helper for handling ranges easier.
 * @author duly
 */
public class Range implements Serializable {

    private double lowerBound;
    private double upperBound;

    public Range(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /**
     *
     * @return Returns the lower bound.
     */
    public double getLowerBound() {
        return lowerBound;
    }

    /**
     *
     * @return Returns the upper bound.
     */
    public double getUpperBound() {
        return upperBound;
    }

    /**
     *
     * @param value The value to check.
     * @return Returns true if the value is in the specified range, else returns false.
     */
    public boolean isInRange(double value) {
        return (value <= upperBound) && (value >= lowerBound);
    }
}
