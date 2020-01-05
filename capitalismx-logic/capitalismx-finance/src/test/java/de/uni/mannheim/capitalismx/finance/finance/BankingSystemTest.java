package de.uni.mannheim.capitalismx.finance.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class BankingSystemTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankingSystemTest.class);

    @Test
    public void generateLoanSelectionTest () {
        BankingSystem bankingSystem = new BankingSystem();

        Assert.assertEquals(bankingSystem.generateLoanSelection(100).size(), 3);
    }

    @Test
    public void addLoanTest () {
        BankingSystem bankingSystem = new BankingSystem();

        Assert.assertNull(bankingSystem.getLoan());
        bankingSystem.addLoan(bankingSystem.generateLoanSelection(100).get(0), LocalDate.now());
        Assert.assertNotNull(bankingSystem.getLoan());
        Assert.assertEquals(bankingSystem.getLoan().getLoanAmount(), 100.0);
    }

    @Test
    public void calculateAnnualRepaymentTest () {
        BankingSystem bankingSystem = new BankingSystem();

        Assert.assertEquals(bankingSystem.calculateAnnualRepayment(), 0.0);

        bankingSystem.addLoan(bankingSystem.new Loan("Loan 1", 0.05, 24, 100), LocalDate.now());
        Assert.assertEquals(bankingSystem.calculateAnnualRepayment(), 50.0);

        bankingSystem.addLoan(bankingSystem.new Loan("Loan 2",0.05, 13, 100), LocalDate.now());
        Assert.assertEquals(bankingSystem.calculateAnnualRepayment(), 50.0);

        bankingSystem.addLoan(bankingSystem.new Loan("Loan 3",0.05, 12, 100), LocalDate.now());
        Assert.assertEquals(bankingSystem.calculateAnnualRepayment(), 100.0);
    }

    @Test
    public void calculateAnnualPrincipalBalanceTest () {
        BankingSystem bankingSystem = new BankingSystem();

        Assert.assertEquals(bankingSystem.calculateAnnualPrincipalBalance(LocalDate.now()), 0.0);

        bankingSystem.addLoan(bankingSystem.new Loan("Loan 1",0.05, 24, 100), LocalDate.of(2019, 11, 30));
        Assert.assertEquals(bankingSystem.calculateAnnualPrincipalBalance(LocalDate.of(2020, 11, 30)), 50.0);

        bankingSystem.addLoan(bankingSystem.new Loan("Loan 2",0.05, 24, 100), LocalDate.of(2019, 11, 30));
        Assert.assertEquals(bankingSystem.calculateAnnualPrincipalBalance(LocalDate.of(2021, 11, 30)), 0.0);
    }

    @Test
    public void calculateAnnualInterestRateTest () {
        BankingSystem bankingSystem = new BankingSystem();

        Assert.assertEquals(bankingSystem.calculateAnnualInterestRate(LocalDate.now()), 0.0);

        bankingSystem.addLoan(bankingSystem.new Loan("Loan 1",0.05, 24, 100), LocalDate.of(2019, 11, 30));
        Assert.assertEquals(bankingSystem.calculateAnnualInterestRate(LocalDate.of(2020, 11, 30)), 2.5);
    }

}