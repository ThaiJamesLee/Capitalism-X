package de.uni.mannheim.capitalismx.finance.finance;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

/**
 *
 * @author sdupper
 */
public class BankingSystem implements Serializable {
    private static BankingSystem instance;

    private double annualRepayment;
    private Loan loan;
    private double annualPrincipalBalance;
    private double annualInterestRate;
    private double annualLoanRate;
    private LocalDate loanDate;

    public class Loan implements Serializable{
        private String name;
        private double interestRate;
        private double duration;
        private double loanAmount;

        protected Loan(String name, double interestRate, double duration, double loanAmount){
            this.name = name;
            this.interestRate = DecimalRound.round(interestRate, 4);
            this.duration = duration;
            this.loanAmount = DecimalRound.round(loanAmount, 2);
        }

        public double getInterestRate() {
            return this.interestRate;
        }

        public double getDuration() {
            return this.duration;
        }

        public double getLoanAmount() {
            return this.loanAmount;
        }

        public String getName() {
            return this.name;
        }
    }

    protected BankingSystem(){

    }

    public static synchronized BankingSystem getInstance() {
        if(BankingSystem.instance == null) {
            BankingSystem.instance = new BankingSystem();
        }
        return BankingSystem.instance;
    }

    ArrayList<Loan> generateLoanSelection(double loanAmount){
        ArrayList<Loan> loanSelection = new ArrayList<Loan>();
        //short-term
        loanSelection.add(new Loan("Short-term Loan", RandomNumberGenerator.getRandomDouble(0.06, 0.18), RandomNumberGenerator.getRandomInt(1, 12), loanAmount));
        //medium-term
        loanSelection.add(new Loan("Medium-term Loan", RandomNumberGenerator.getRandomDouble(0.03, 0.06), RandomNumberGenerator.getRandomInt(1, 5) * 12, loanAmount));
        //long-term
        loanSelection.add(new Loan("Long-term Loan", RandomNumberGenerator.getRandomDouble(0.01, 0.03), RandomNumberGenerator.getRandomInt(10, 15) * 12, loanAmount));
        return loanSelection;
    }

    void addLoan(Loan loan, LocalDate loanDate){
        this.loan = loan;
        //loanDate starts on first day of following month
        this.loanDate = loanDate.plusMonths(1).withDayOfMonth(1);
        //this.loanDate = loanDate;
    }

    void removeLoan(){
        this.loan = null;
        this.loanDate = null;
    }

    protected double calculateAnnualRepayment(){
        this.annualRepayment = 0;
        if(this.loan != null){
            double durationYears = Math.ceil(this.loan.getDuration() / 12.0);
            this.annualRepayment = this.loan.getLoanAmount() / durationYears;
        }
        return this.annualRepayment;
    }

    protected double calculateAnnualPrincipalBalance(LocalDate gameDate){
        this.annualPrincipalBalance = 0;
        if(this.loan != null){
            int year = Period.between(this.loanDate.plusDays(1), gameDate).getYears();
            this.annualPrincipalBalance = this.loan.getLoanAmount() - (this.calculateAnnualRepayment()  * year);
            //TODO remove loan if principal balance <= 0
        }
        return this.annualPrincipalBalance;
    }

    protected double calculateAnnualInterestRate(LocalDate gameDate){
        this.annualInterestRate = 0;
        if(this.loan != null){
            //int year = Period.between(this.loanDate.plusDays(1), gameDate).getYears();
            this.annualInterestRate = this.calculateAnnualPrincipalBalance(gameDate) * this.loan.getInterestRate();
        }
        return this.annualInterestRate;
    }

    protected double calculateAnnualLoanRate(LocalDate gameDate){
        this.annualLoanRate = 0;
        if(loan != null){
            //int year = Period.between(this.loanDate.plusDays(1), gameDate).getYears();
            this.annualLoanRate = this.calculateAnnualRepayment() + this.calculateAnnualInterestRate(gameDate);
        }
        return this.annualLoanRate;
    }

    protected double calculateMonthlyLoanRate(LocalDate gameDate){
        if(this.loan != null){
            // check if payment required on current day - first payment is one month after loanDate
            if((this.loanDate.getDayOfMonth() == gameDate.getDayOfMonth()) && (this.loanDate.isBefore(gameDate))){
                double amount = this.calculateAnnualLoanRate(gameDate);

                //if the loan duration is not a multiple of 12 months, the monthly payment in the last year is not 1/12 of the annual loan rate
                //instead, the annual loan rate is divided according to the number of months in the last year

                //number of complete years (12months) in loanDuration
                int years = ((int) this.loan.getDuration()) / 12;
                if(gameDate.isAfter(this.loanDate.plusYears(years * 12))){
                    amount = amount / (((int) this.loan.getDuration()) % 12);
                }else{
                    amount /= 12;
                }

                //TODO update GUI
                //check if this is the last payment
                if(this.loanDate.plusMonths((long) this.loan.getDuration()).equals(gameDate)){
                    this.removeLoan();
                }
                return amount;
            }
        }
        return 0.0;
    }

    //TODO Determine year of loan
    public double getAnnualPrincipalBalance() {
        //this.calculateAnnualPrincipalBalance()
        return this.annualPrincipalBalance;
    }

    public Loan getLoan() {
        return this.loan;
    }
}
