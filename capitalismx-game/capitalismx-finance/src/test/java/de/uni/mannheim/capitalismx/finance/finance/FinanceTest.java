package de.uni.mannheim.capitalismx.finance.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FinanceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceTest.class);

    @Test
    public void generateLoanSelectionTest () {
        Finance finance = Finance.getInstance();

        //TODO
        //Assert.assertEquals(finance.generateLoanSelection(100).get(0).getLoanAmount(), 100);
    }

    @Test
    public void generateInvestmentSelectionTest () {
        Finance finance = Finance.getInstance();

        Assert.assertNull(finance.generateInvestmentSelection(1000001));

        Assert.assertNotNull(finance.generateInvestmentSelection(100));
    }

    @Test
    public void addInvestmentTest () {
        Finance finance = Finance.getInstance();

        finance.addInvestment(new Investment(100, 0.1, 0.5));

        Assert.assertEquals(finance.getInvestments().size(), 1);
        Assert.assertEquals(finance.getCash(), 999900.0);
        Assert.assertEquals(finance.calculateTotalInvestmentAmount(), 100.0);
    }

}
