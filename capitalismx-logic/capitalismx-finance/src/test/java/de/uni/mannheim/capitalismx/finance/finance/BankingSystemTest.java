package de.uni.mannheim.capitalismx.finance.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Locale;

public class BankingSystemTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankingSystemTest.class);

    @Test
    public void generateLoanSelectionTest () {
        BankingSystem bankingSystem = new BankingSystem();

        Assert.assertEquals(bankingSystem.generateLoanSelection(100, Locale.ENGLISH).size(), 3);
    }

    @Test
    public void addLoanTest () {
        BankingSystem bankingSystem = new BankingSystem();

        Assert.assertEquals(bankingSystem.getLoans().size(), 0);
        bankingSystem.addLoan(bankingSystem.generateLoanSelection(100, Locale.ENGLISH).get(0), LocalDate.now());
        Assert.assertEquals(bankingSystem.getLoans().size(), 1);
        Assert.assertEquals(bankingSystem.getLoans().get(0).getLoanAmount(), 100.0);
    }

    @Test
    public void calculateAnnualRepaymentTest () {
        BankingSystem bankingSystem = new BankingSystem();

        Assert.assertEquals(bankingSystem.calculateAnnualRepayment(), 0.0);

        bankingSystem.addLoan(bankingSystem.new Loan("Loan 1", 0.05, 24, 100), LocalDate.now());
        Assert.assertEquals(bankingSystem.calculateAnnualRepayment(), 50.0);

        bankingSystem.addLoan(bankingSystem.new Loan("Loan 2",0.05, 13, 100), LocalDate.now());
        Assert.assertEquals(bankingSystem.calculateAnnualRepayment(), 100.0);

        bankingSystem.addLoan(bankingSystem.new Loan("Loan 3",0.05, 12, 100), LocalDate.now());
        Assert.assertEquals(bankingSystem.calculateAnnualRepayment(), 200.0);
    }

    @Test
    public void calculateAnnualPrincipalBalanceTest () {
        BankingSystem bankingSystem = new BankingSystem();

        Assert.assertEquals(bankingSystem.calculateAnnualPrincipalBalance(LocalDate.now()), 0.0);

        bankingSystem.addLoan(bankingSystem.new Loan("Loan 1",0.05, 24, 100), LocalDate.of(2019, 11, 30));
        Assert.assertEquals(bankingSystem.calculateAnnualPrincipalBalance(LocalDate.of(2020, 11, 30)), 100.0);

        bankingSystem.addLoan(bankingSystem.new Loan("Loan 2",0.05, 24, 100), LocalDate.of(2019, 11, 30));
        Assert.assertEquals(bankingSystem.calculateAnnualPrincipalBalance(LocalDate.of(2021, 12, 1)), 100.0);
        Assert.assertEquals(bankingSystem.calculateAnnualPrincipalBalance(LocalDate.of(2021, 12, 2)), 0.0);
    }

    @Test
    public void calculateAnnualInterestRateTest () {
        BankingSystem bankingSystem = new BankingSystem();

        Assert.assertEquals(bankingSystem.calculateAnnualInterestRate(LocalDate.now()), 0.0);

        bankingSystem.addLoan(bankingSystem.new Loan("Loan 1",0.05, 24, 100), LocalDate.of(2019, 11, 30));
        Assert.assertEquals(bankingSystem.calculateAnnualInterestRate(LocalDate.of(2020, 11, 30)), 5.0);
        //because loans added on first day of following month
        Assert.assertEquals(bankingSystem.calculateAnnualInterestRate(LocalDate.of(2021, 12, 1)), 2.5);
    }

}