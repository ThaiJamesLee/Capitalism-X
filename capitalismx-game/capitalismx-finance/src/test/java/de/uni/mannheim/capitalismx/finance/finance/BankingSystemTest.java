package de.uni.mannheim.capitalismx.finance.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        bankingSystem.addLoan(bankingSystem.generateLoanSelection(100).get(0));
        Assert.assertNotNull(bankingSystem.getLoan());
        Assert.assertEquals(bankingSystem.getLoan().getLoanAmount(), 100.0);
    }

    @Test
    public void calculateAnnualRepaymentTest () {
        BankingSystem bankingSystem = new BankingSystem();

        Assert.assertEquals(bankingSystem.calculateAnnualRepayment(), 0.0);

        bankingSystem.addLoan(bankingSystem.new Loan(0.05, 24, 100));
        Assert.assertEquals(bankingSystem.calculateAnnualRepayment(), 50.0);

        bankingSystem.addLoan(bankingSystem.new Loan(0.05, 13, 100));
        Assert.assertEquals(bankingSystem.calculateAnnualRepayment(), 50.0);

        bankingSystem.addLoan(bankingSystem.new Loan(0.05, 12, 100));
        Assert.assertEquals(bankingSystem.calculateAnnualRepayment(), 100.0);
    }

    @Test
    public void calculateAnnualPrincipalBalanceTest () {
        BankingSystem bankingSystem = new BankingSystem();

        Assert.assertEquals(bankingSystem.calculateAnnualPrincipalBalance(1), 0.0);

        bankingSystem.addLoan(bankingSystem.new Loan(0.05, 24, 100));
        Assert.assertEquals(bankingSystem.calculateAnnualPrincipalBalance(1), 50.0);

        bankingSystem.addLoan(bankingSystem.new Loan(0.05, 24, 100));
        Assert.assertEquals(bankingSystem.calculateAnnualPrincipalBalance(2), 0.0);
    }

    @Test
    public void calculateAnnualInterestRateTest () {
        BankingSystem bankingSystem = new BankingSystem();

        Assert.assertEquals(bankingSystem.calculateAnnualInterestRate(1), 0.0);

        bankingSystem.addLoan(bankingSystem.new Loan(0.05, 24, 100));
        Assert.assertEquals(bankingSystem.calculateAnnualInterestRate(1), 2.5);
    }

}