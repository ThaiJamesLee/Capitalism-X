package de.uni.mannheim.capitalismx.finance.finance;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

/**
 * This class represents the banking system.
 * It implements short-, medium-, and long-term loans and the associated variables.
 * Based on the report p.76-78
 *
 * @author sdupper
 */
public class BankingSystem implements Serializable {

    /**
     * The singleton pointer.
     */
    private static BankingSystem instance;

    /**
     * The current loans of the company.
     */
    private TreeMap<LocalDate, Loan> loans;

    private double totalAnnualRepayment;
    private double totalAnnualPrincipalBalance;
    private double totalAnnualInterestRate;
    private double totalAnnualLoanRate;

    /**
     * This class defines a loan based on a name, an interest rate, a duration, and an amount.
     */
    public class Loan implements Serializable{
        private String name;
        private double interestRate;
        private double duration;
        private double loanAmount;

        /**
         * The start date of the current loan, relevant e.g., to determine when the loan ends.
         */
        private LocalDate loanDate;

        private double annualRepayment;
        private double annualPrincipalBalance;
        private double annualInterestRate;
        private double annualLoanRate;

        protected Loan(String name, double interestRate, double duration, double loanAmount){
            this.name = name;
            this.interestRate = DecimalRound.round(interestRate, 4);
            this.duration = duration;
            this.loanAmount = DecimalRound.round(loanAmount, 2);
        }

        public void setLoanDate(LocalDate loanDate) {
            this.loanDate = loanDate;
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
            this.annualPrincipalBalance = 0;
            int year = Period.between(this.loanDate.plusDays(1), gameDate).getYears();
            this.annualPrincipalBalance = this.loanAmount - (this.calculateAnnualRepayment()  * year);
            //TODO remove loan if principal balance <= 0
            return this.annualPrincipalBalance;
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

                //TODO update GUI
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
    }

    /**
     * Constructor
     */
    protected BankingSystem(){
        this.loans = new TreeMap<>();
    }

    /**
     *
     * @return Returns the singleton instance
     */
    public static synchronized BankingSystem getInstance() {
        if(BankingSystem.instance == null) {
            BankingSystem.instance = new BankingSystem();
        }
        return BankingSystem.instance;
    }

    /**
     * Generates a selection of three loan types according to the table on p.76.
     * @param loanAmount The amount of the loan.
     * @return Returns an ArrayList of the three different loans that were generated.
     */
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

    /**
     * Adds a new loan to the company.
     * @param loan The loan object to be added to the company.
     * @param loanDate The date on which the loan was added.
     */
    void addLoan(Loan loan, LocalDate loanDate){
        //loanDate starts on first day of following month
        loan.setLoanDate(loanDate.plusMonths(1).withDayOfMonth(1));
        this.loans.put(loanDate.plusMonths(1).withDayOfMonth(1), loan);
    }

    /**
     * Removes a loan from the company.
     */
    void removeLoan(Loan loan){
        //this.loans.remove(loan);
    }

    /**
     * Calculates the total annual repayment based on the annual repayment of all loans.
     * @return Returns the total annual repayment
     */
    protected double calculateAnnualRepayment(){
        this.totalAnnualRepayment = 0;
        for(Loan loan : loans.values()){
            this.totalAnnualRepayment += loan.calculateAnnualRepayment();
        }
        return this.totalAnnualRepayment;
    }

    /**
     * Calculates the total annual principal balance based on the annual principal balance of all loans.
     * @param gameDate The current date in the game.
     * @return Returns the total annual principal balance.
     */
    protected double calculateAnnualPrincipalBalance(LocalDate gameDate){
        this.totalAnnualPrincipalBalance = 0;
        for(Loan loan : loans.values()){
            this.totalAnnualPrincipalBalance += loan.calculateAnnualPrincipalBalance(gameDate);
        }
        return this.totalAnnualPrincipalBalance;
    }

    /**
     * Calculates the total annual interest rate based on the annual interest rate of all loans.
     * @param gameDate The current date in the game.
     * @return Returns the total annual interest rate.
     */
    protected double calculateAnnualInterestRate(LocalDate gameDate){
        this.totalAnnualInterestRate = 0;
        for(Loan loan : loans.values()){
            this.totalAnnualInterestRate += loan.calculateAnnualInterestRate(gameDate);
        }
        return this.totalAnnualInterestRate;
    }

    /**
     * Calculates the total annual loan rate based on the annual loan rate of all loans.
     * @param gameDate The current date in the game.
     * @return Returns the total annual loan rate.
     */
    protected double calculateAnnualLoanRate(LocalDate gameDate){
        this.totalAnnualLoanRate = 0;
        for(Loan loan : loans.values()){
            this.totalAnnualLoanRate += loan.calculateAnnualLoanRate(gameDate);
        }
        return this.totalAnnualLoanRate;
    }

    /**
     * Calculates the total monthly loan rate based on the monthly loan rate of all loans. Moreover, removes loans
     * after the last payment.
     * @param gameDate The current date in the game.
     * @return Returns the total monthly loan rate.
     */
    protected double calculateMonthlyLoanRate(LocalDate gameDate){
        double totalMonthlyLoanRate = 0.0;
        for(Loan loan : loans.values()){
            totalMonthlyLoanRate += loan.calculateMonthlyLoanRate(gameDate);
        }

        //remove loans that were payed.
        Iterator iterator = loans.values().iterator();
        while(iterator.hasNext()){
            Loan loan = (Loan) iterator.next();
            if(loan.loanAmount <= 0.0){
                FinanceDepartment.getInstance().removeLoan();
                iterator.remove();
            }
        }
        return totalMonthlyLoanRate;
    }

    //TODO Determine year of loan
    public double getAnnualPrincipalBalance() {
        //this.calculateAnnualPrincipalBalance()
        return this.totalAnnualPrincipalBalance;
    }

    public TreeMap<LocalDate, Loan> getLoans() {
        return this.loans;
    }

    public static BankingSystem createInstance() {
        return new BankingSystem();
    }

    public static void setInstance(BankingSystem instance) {
        BankingSystem.instance = instance;
    }
}
