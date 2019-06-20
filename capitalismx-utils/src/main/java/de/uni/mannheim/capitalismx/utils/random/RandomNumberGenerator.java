package de.uni.mannheim.capitalismx.utils.random;

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
}
