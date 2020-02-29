package de.uni.mannheim.capitalismx.finance.finance;

import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportInteger;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 * This class defines a loan based on a name, an interest rate, a duration, and an amount.
 * Based on the report p.76-78
 *
 * @author sdupper
 */
public class Loan implements Serializable {
    private String name;
    private double interestRate;
    private int duration;
    private double loanAmount;

    /**
     * The start date of the current loan, relevant e.g., to determine when the loan ends.
     */
    private LocalDate loanDate;
    private PropertyChangeSupportInteger remainingDuration;

    private double annualRepayment;
    private PropertyChangeSupportDouble annualPrincipalBalance;
    private double annualInterestRate;
    private double annualLoanRate;

    protected Loan(String name, double interestRate, int duration, double loanAmount){
        this.name = name;
        this.interestRate = DecimalRound.round(interestRate, 4);
        this.duration = duration;
        this.loanAmount = DecimalRound.round(loanAmount, 2);

        this.annualPrincipalBalance = new PropertyChangeSupportDouble();
        this.annualPrincipalBalance.setValue(this.loanAmount);
        this.annualPrincipalBalance.setPropertyChangedName("annualPrincipalBalance");

        this.remainingDuration = new PropertyChangeSupportInteger();
        this.remainingDuration.setValue(this.duration);
        this.remainingDuration.setPropertyChangedName("remainingDuration");
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public double getInterestRate() {
        return this.interestRate;
    }

    public int getDuration() {
        return this.duration;
    }

    public double getLoanAmount() {
        return this.loanAmount;
    }

    public double getAnnualPrincipalBalance() {
        return annualPrincipalBalance.getValue();
    }

    public int getRemainingDuration() {
        return remainingDuration.getValue();
    }

    public String getName() {
        return this.name;
    }

    /**
     * Calculates the remaining duration of the loan in months.
     * @param gameDate The current date in the game
     * @return Returns the remaining duration of the loan in months.
     */
    public double calculateRemainingDuration(LocalDate gameDate){
        double rDuration;
        if(this.loanDate != null){
            Period p = Period.between(gameDate, this.loanDate.plusMonths((long) this.duration));
            rDuration =  (p.getYears() * 12) + p.getMonths() ;
            //ensures that remaining duration is not larger than starting duration
            rDuration = Math.min(rDuration, this.duration);
        }else{
            rDuration = -1.0;
        }
        this.remainingDuration.setValue((int) rDuration);
        return this.remainingDuration.getValue();
    }

    /**
     * Calculates the annual repayment based on the loan amount and duration according to p.77.
     * @return Returns the annual repayment
     */
    protected double calculateAnnualRepayment(){
        this.annualRepayment = 0;
        double durationYears = Math.ceil(this.duration / 12.0);
        this.annualRepayment = this.loanAmount / durationYears;
        return this.annualRepayment;
    }

    /**
     * Calculates the annual principal balance based on the loan amount, annual repayment, and duration according to p.77.
     * @param gameDate The current date in the game.
     * @return Returns the annual principal balance.
     */
    protected double calculateAnnualPrincipalBalance(LocalDate gameDate){
        double principalBalance = 0;
        int year = Period.between(this.loanDate.plusDays(1), gameDate).getYears();
        principalBalance = this.loanAmount - (this.calculateAnnualRepayment()  * year);
        //TODO remove loan if principal balance <= 0
        this.annualPrincipalBalance.setValue(principalBalance);
        return this.annualPrincipalBalance.getValue();
    }

    /**
     * Calculates the annual interest rate based on the principal balance and interest rate according to p.77.
     * @param gameDate The current date in the game.
     * @return Returns the annual interest rate.
     */
    protected double calculateAnnualInterestRate(LocalDate gameDate){
        this.annualInterestRate = 0;
        //int year = Period.between(this.loanDate.plusDays(1), gameDate).getYears();
        this.annualInterestRate = this.calculateAnnualPrincipalBalance(gameDate) * this.interestRate;
        return this.annualInterestRate;
    }

    /**
     * Calculates the annual loan rate based on the annual repayment and the annual interest rate according to p.77.
     * @param gameDate The current date in the game.
     * @return Returns the annual loan rate.
     */
    protected double calculateAnnualLoanRate(LocalDate gameDate){
        this.annualLoanRate = 0;
        //int year = Period.between(this.loanDate.plusDays(1), gameDate).getYears();
        this.annualLoanRate = this.calculateAnnualRepayment() + this.calculateAnnualInterestRate(gameDate);
        return this.annualLoanRate;
    }

    /**
     * Calculates the monthly loan rate based on the number of months the current annual loan rate has to be distributed
     * across. Moreover, removes the loan after the last payment.
     * @param gameDate The current date in the game.
     * @return Returns the monthly loan rate.
     */
    protected double calculateMonthlyLoanRate(LocalDate gameDate){
        //calculate remaining duration
        this.calculateRemainingDuration(gameDate);

        // check if payment required on current day - first payment is one month after loanDate
        if((this.loanDate.getDayOfMonth() == gameDate.getDayOfMonth()) && (this.loanDate.isBefore(gameDate))){
            double monthlyLoanRate = this.calculateAnnualLoanRate(gameDate);

            //if the loan duration is not a multiple of 12 months, the monthly payment in the last year is not 1/12
            // of the annual loan rate
            //instead, the annual loan rate is divided according to the number of months in the last year

            //number of complete years (12months) in loanDuration
            int years = ((int) this.duration) / 12;
            if(gameDate.isAfter(this.loanDate.plusYears(years * 12))){
                monthlyLoanRate = monthlyLoanRate / (((int) this.duration) % 12);
            }else{
                monthlyLoanRate /= 12;
            }

            //check if this is the last payment
            if(this.loanDate.plusMonths((long) this.duration).equals(gameDate)){
                //Set loan amount to 0.0 -> will be removed later in the banking system
                //this.removeLoan();
                this.loanAmount = 0.0;
                //FinanceDepartment.getInstance().removeLoan();
            }
            return monthlyLoanRate;
        }
        return 0.0;
    }

    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        this.annualPrincipalBalance.addPropertyChangeListener(listener);
        this.remainingDuration.addPropertyChangeListener(listener);
    }
}