package de.uni.mannheim.capitalismx.utils.random;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author sdupper
 */
public class RandomNumberGenerator {

    public static int getRandomInt(int min, int max){
        return (int)(Math.random() * (max - min + 1) + min);
    }

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
