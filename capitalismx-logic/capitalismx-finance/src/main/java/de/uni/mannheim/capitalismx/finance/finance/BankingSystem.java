package de.uni.mannheim.capitalismx.finance.finance;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportBoolean;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

/**
 * This class represents the banking system.
 * It manages all short-, medium-, and long-term loans of the company and the associated variables.
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
    private ArrayList<Loan> loans;

    private double totalAnnualRepayment;
    private double totalAnnualPrincipalBalance;
    private double totalAnnualInterestRate;
    private double totalAnnualLoanRate;

    private static final String LANGUAGE_PROPERTIES_FILE = "finance-module";

    /**
     * Constructor
     */
    protected BankingSystem(){
        this.loans = new ArrayList<>();
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
     * @param locale The Locale object of the desired language.
     * @return Returns an ArrayList of the three different loans that were generated.
     */
    ArrayList<Loan> generateLoanSelection(double loanAmount, Locale locale){
        ArrayList<Loan> loanSelection = new ArrayList<Loan>();
        //short-term
        loanSelection.add(new Loan(this.getLocalisedString("finance.loan.short", locale), RandomNumberGenerator.getRandomDouble(0.06, 0.18), RandomNumberGenerator.getRandomInt(1, 12), loanAmount));
        //medium-term
        loanSelection.add(new Loan(this.getLocalisedString("finance.loan.medium", locale), RandomNumberGenerator.getRandomDouble(0.03, 0.06), RandomNumberGenerator.getRandomInt(1, 5) * 12, loanAmount));
        //long-term
        loanSelection.add(new Loan(this.getLocalisedString("finance.loan.long", locale), RandomNumberGenerator.getRandomDouble(0.01, 0.03), RandomNumberGenerator.getRandomInt(10, 15) * 12, loanAmount));
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
        this.loans.add(loan);
        //init annual principal balance
        loan.calculateAnnualPrincipalBalance(loanDate);
        //init remaining duration
        loan.calculateRemainingDuration(loanDate);
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
        for(Loan loan : loans){
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
        for(Loan loan : loans){
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
        for(Loan loan : loans){
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
        for(Loan loan : loans){
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
        for(Loan loan : loans){
            totalMonthlyLoanRate += loan.calculateMonthlyLoanRate(gameDate);
        }

        //remove loans that were payed.
        Iterator iterator = loans.iterator();
        while(iterator.hasNext()){
            Loan loan = (Loan) iterator.next();
            if(loan.getLoanAmount() <= 0.0){
                iterator.remove();
                FinanceDepartment.getInstance().removeLoan();
            }
        }
        return totalMonthlyLoanRate;
    }

    //TODO Determine year of loan
    public double getTotalAnnualPrincipalBalance() {
        return this.totalAnnualPrincipalBalance;
    }

    public ArrayList<Loan> getLoans() {
        return this.loans;
    }

    public static BankingSystem createInstance() {
        return new BankingSystem();
    }

    public static void setInstance(BankingSystem instance) {
        BankingSystem.instance = instance;
    }

    public String getLocalisedString(String text, Locale locale) {
        ResourceBundle langBundle = ResourceBundle.getBundle(LANGUAGE_PROPERTIES_FILE, locale);
        return langBundle.getString(text);
    }
}
