package de.uni.mannheim.capitalismx.finance.finance;

import java.io.Serializable;
import java.util.Random;

/**
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

    enum InvestmentType{
        REAL_ESTATE(0.07, 0.2), STOCKS(0.1, 0.3), VENTURE_CAPITAL(0.142, 0.5);

        private double averageYearlyReturn;
        private double yearlyStandardDeviation;

        public double getAverageYearlyReturn() {
            return this.averageYearlyReturn;
        }

        public double getYearlyStandardDeviation() {
            return this.yearlyStandardDeviation;
        }

        private InvestmentType(double averageYearlyReturn, double yearlyStandardDeviation){
            this.averageYearlyReturn = averageYearlyReturn;
            this.yearlyStandardDeviation = yearlyStandardDeviation;
        }
    }

    public Investment(double amount, InvestmentType investmentType){
        this.amount = amount;
        this.averageYearlyReturn = investmentType.getAverageYearlyReturn();
        this.yearlyStandardDeviation = investmentType.getYearlyStandardDeviation();
        this.investmentType = investmentType;
        this.calculateAverageDailyReturn();
        this.calculateDailyStandardDeviation();
    }

    private double calculateAverageDailyReturn(){
        //this.averageDailyReturn = Math.pow((1 + this.averageYearlyReturn) / 1, 1.0/365);
        this.averageDailyReturn = Math.pow((1 + (this.averageYearlyReturn * 100)) / 1, 1.0/365);
        return this.averageDailyReturn;
    }

    private double calculateDailyStandardDeviation(){
        this.dailyStandardDeviation = this.yearlyStandardDeviation / Math.sqrt(365);
        return this.dailyStandardDeviation;
    }

    private double calculateDailyReturn(){
        Random random = new Random();
        this.dailyReturn = random.nextGaussian() * this.dailyStandardDeviation + this.averageDailyReturn;
        return this.dailyReturn;
    }

    protected void updateAmount(){
        //this.amount += (this.calculateDailyReturn() * this.amount);
        this.amount += ((this.calculateDailyReturn() / 100) * this.amount);
    }

    public double getAmount() {
        return this.amount;
    }

    public InvestmentType getInvestmentType() {
        return this.investmentType;
    }
}
