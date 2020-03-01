package de.uni.mannheim.capitalismx.production;

import de.uni.mannheim.capitalismx.production.investment.ProductionInvestment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class ProductionDepartmentInvestmentTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductionInvestment.class);

    private ProductionInvestment productionInvestment;
    private LocalDate investmentDate;

    @BeforeTest
    public void setUp() {
        this.productionInvestment = new ProductionInvestment("Process Automation");
        this.investmentDate = LocalDate.of(1990,1,1);
    }

    @Test
    public void investTest() {
        this.productionInvestment.invest(2, this.investmentDate);
        Assert.assertEquals(this.productionInvestment.getLevel(), 2);
    }

    @Test
    public void updateInvestmentTest() {
        this.productionInvestment.invest(4, this.investmentDate);
        this.productionInvestment.updateInvestment(LocalDate.of(1991,1,1));
        Assert.assertEquals(this.productionInvestment.getLevel(), 2);
        this.productionInvestment.invest(3, this.investmentDate);
        this.productionInvestment.updateInvestment(LocalDate.of(1992,1,1));
        Assert.assertEquals(this.productionInvestment.getLevel(), 2);
        this.productionInvestment.invest(2, this.investmentDate);
        this.productionInvestment.updateInvestment(LocalDate.of(1992,1,1));
        Assert.assertEquals(this.productionInvestment.getLevel(), 1);
    }
}
