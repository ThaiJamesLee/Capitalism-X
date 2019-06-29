package de.uni.mannheim.capitalismx.finance.finance;

import java.util.ArrayList;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

/**
 *
 * @author sdupper
 */
public class BankingSystem {
    private static BankingSystem instance;

    private double annualRepayment;
    private Loan loan;
    private double annualPrincipalBalance;
    private double annualInterestRate;
    private double annualLoanRate;

    public class Loan{
        private double interestRate;
        private double duration;
        private double loanAmount;

        private Loan(double interestRate, double duration, double loanAmount){
            this.interestRate = interestRate;
            this.duration = duration;
            this.loanAmount = loanAmount;
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
    }

    private BankingSystem(){

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
        loanSelection.add(new Loan(RandomNumberGenerator.getRandomDouble(0.06, 0.18), RandomNumberGenerator.getRandomInt(1, 12), loanAmount));
        //medium-term
        loanSelection.add(new Loan(RandomNumberGenerator.getRandomDouble(0.03, 0.06), RandomNumberGenerator.getRandomInt(1, 5) * 12, loanAmount));
        //long-term
        loanSelection.add(new Loan(RandomNumberGenerator.getRandomDouble(0.01, 0.03), RandomNumberGenerator.getRandomInt(10, 15) * 12, loanAmount));
        return loanSelection;
    }

    void addLoan(Loan loan){
        this.loan = loan;
    }

    private double calculateAnnualRepayment(){
        this.annualRepayment = 0;
        if(this.loan != null){
            this.annualRepayment = this.loan.getLoanAmount() / this.loan.getDuration();
        }
        return this.annualRepayment;
    }

    private double calculateAnnualPrincipalBalance(int year){
        this.annualPrincipalBalance = 0;
        if(this.loan != null){
            this.annualPrincipalBalance = this.loan.getLoanAmount() - (this.calculateAnnualRepayment()  * year);
        }
        return this.annualPrincipalBalance;
    }

    private double calculateAnnualInterestRate(Loan loan, int year){
        this.annualInterestRate = 0;
        if(this.loan != null){
            this.annualInterestRate = this.calculateAnnualPrincipalBalance(year) * loan.getInterestRate();
        }
        return this.annualInterestRate;
    }

    private double calculateAnnualLoanRate(int year){
        this.annualLoanRate = 0;
        if(loan != null){
            this.annualLoanRate = this.calculateAnnualRepayment() + this.calculateAnnualInterestRate(loan, year);
        }
        return this.annualLoanRate;
    }

    //TODO Determine year of loan
    public double getAnnualPrincipalBalance() {
        //this.calculateAnnualPrincipalBalance()
        return this.annualPrincipalBalance;
    }
}
