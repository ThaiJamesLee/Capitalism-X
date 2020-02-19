package de.uni.mannheim.capitalismx.finance.finance;

import java.io.Serializable;
import java.util.Random;

/**
 * This class represents the investments the company can invest in.
 * The company can invest in real estate, stocks, and venture capital. The three investment types differ in their
 * expected return and risk.
 * Based on the report p.78-79
 *
 * @author sdupper
 */
public class Investment implements Serializable {
    private double amount;
    private double yearlyStandardDeviation;
    private double averageYearlyReturn;
    private InvestmentType investmentType;
    private double averageDailyReturn;
    private double dailyStandardDeviation;
    private double dailyReturn;

    /**
     * The different possible investment types: real estate, stocks, and venture capital. They have different average
     * yearly returns and yearly standard deviations according to p.78-79.
     */
    public enum InvestmentType{
        REAL_ESTATE(0.07, 0.2), STOCKS(0.1, 0.3), VENTURE_CAPITAL(0.142, 0.5);

        private double averageYearlyReturn;
        private double yearlyStandardDeviation;

        public double getAverageYearlyReturn() {
            return this.averageYearlyReturn;
        }

        public double getYearlyStandardDeviation() {
            return this.yearlyStandardDeviation;
        }

        InvestmentType(double averageYearlyReturn, double yearlyStandardDeviation){
            this.averageYearlyReturn = averageYearlyReturn;
            this.yearlyStandardDeviation = yearlyStandardDeviation;
        }
    }

    /**
     * Constructor
     * @param amount The amount to be invested.
     * @param investmentType The type of investment that should be invested in.
     */
    public Investment(double amount, InvestmentType investmentType){
        this.amount = amount;
        this.averageYearlyReturn = investmentType.getAverageYearlyReturn();
        this.yearlyStandardDeviation = investmentType.getYearlyStandardDeviation();
        this.investmentType = investmentType;
        this.calculateAverageDailyReturn();
        this.calculateDailyStandardDeviation();
    }

    /**
     * Calculates the average daily return from the average yearly return according to p.79. Note that the formula used
     * here is slightly different, as a mistake in the report's formula was corrected.
     * @return Returns the average daily return of the investment.
     */
    private double calculateAverageDailyReturn(){
        //this.averageDailyReturn = Math.pow((1 + this.averageYearlyReturn) / 1, 1.0/365);
        //this.averageDailyReturn = Math.pow((1 + (this.averageYearlyReturn * 100)) / 1, 1.0/365);
        this.averageDailyReturn = Math.pow((1 + this.averageYearlyReturn), 1.0/365) - 1;
        return this.averageDailyReturn;
    }

    /**
     * Calculates the daily standard deviation from the yearly standard deviation according to p.79.
     * @return Returns the daily standard deviation of the investment.
     */
    private double calculateDailyStandardDeviation(){
        this.dailyStandardDeviation = this.yearlyStandardDeviation / Math.sqrt(365);
        return this.dailyStandardDeviation;
    }

    /**
     * Calculates the daily return of the investment using a Gaussian distribution according to p.79.
     * @return Returns the daily return of the investment.
     */
    private double calculateDailyReturn(){
        Random random = new Random();
        this.dailyReturn = random.nextGaussian() * this.dailyStandardDeviation + this.averageDailyReturn;
        return this.dailyReturn;
    }

    /**
     * Updates the investment amount according to the daily return of the investment. Has to be updated daily.
     */
    protected void updateAmount(){
        //this.amount += (this.calculateDailyReturn() * this.amount);
        this.amount += ((this.calculateDailyReturn() / 100) * this.amount);
    }

    /**
     * Increases the amount invested into this investment by the specified amount.
     * @param amount The additional amount to be invested.
     */
    public void increaseAmount(double amount){
        this.amount += amount;
    }

    /**
     * Decreases the amount invested into this investment by the specified amount.
     * @param amount The amount to be disinvested.
     */
    public void decreaseAmount(double amount){
        this.amount -= amount;
    }

    public double getAmount() {
        return this.amount;
    }

    public InvestmentType getInvestmentType() {
        return this.investmentType;
    }
}
