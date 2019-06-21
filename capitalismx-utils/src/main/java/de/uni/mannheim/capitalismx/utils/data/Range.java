package de.uni.mannheim.capitalismx.utils.data;

/**
 * @author duly
 */
public class Range {

    private double lowerBound;
    private double upperBound;

    public Range(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }
}
