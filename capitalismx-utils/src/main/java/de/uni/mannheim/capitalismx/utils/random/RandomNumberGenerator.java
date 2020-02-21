package de.uni.mannheim.capitalismx.utils.random;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Generates random numbers (integer or double) in a specified interval.
 *
 * @author sdupper
 */
public class RandomNumberGenerator {

    /**
     * Generates a random integer between min and max.
     * @param min The lower bound of the random integer.
     * @param max The upper bound of the random integer.
     * @return Returns a random integer between min and max.
     */
    public static int getRandomInt(int min, int max){
        return (int)(Math.random() * (max - min + 1) + min);
    }

    /**
     * Generates a random double between min and max.
     * @param min The lower bound of the random double.
     * @param max The upper bound of the random double.
     * @return Returns a random double between min and max.
     */
    public static double getRandomDouble(double min, double max){
        return (Math.random() * (max - min) + min);
    }

    /*public static double getRandomDouble(double min, double max, int decimalPlaces){
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat)nf;
        df.setMaximumFractionDigits(decimalPlaces);
        return Double.valueOf(df.format(Math.random() * (max - min) + min));
    }*/
}
