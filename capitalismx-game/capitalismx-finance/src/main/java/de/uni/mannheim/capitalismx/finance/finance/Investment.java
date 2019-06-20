package de.uni.mannheim.capitalismx.finance.finance;

import java.util.Random;

/**
 *
 * @author sdupper
 */
public class Investment {
    private double amount;
    private double yearlyStandardDeviation;
    private double averageYearlyReturn;
    private double averageDailyReturn;
    private double dailyStandardDeviation;
    private double dailyReturn;

    public Investment(double amount, double averageYearlyReturn, double yearlyStandardDeviation){
        this.amount = amount;
        this.averageYearlyReturn = averageYearlyReturn;
        this.yearlyStandardDeviation = yearlyStandardDeviation;
    }

    private double calculateAverageDailyReturn(){
        this.averageDailyReturn = Math.pow((1 + this.averageYearlyReturn) / 1, 1.0/365);
        return this.averageDailyReturn;
    }

    private double calculateDailyStandardDeviation(){
        this.dailyStandardDeviation = this.yearlyStandardDeviation / Math.sqrt(365);
        return this.dailyStandardDeviation;
    }

    private double calculateDailyReturn(){
        Random random = new Random();
        this.dailyReturn = random.nextGaussian() * this.calculateDailyStandardDeviation() + this.calculateAverageDailyReturn();
        return this.dailyReturn;
    }

    //TODO has to be done daily
    private void updateAmount(){
        this.amount += this.dailyReturn;
    }

    public double getAmount() {
        return this.amount;
    }
}
