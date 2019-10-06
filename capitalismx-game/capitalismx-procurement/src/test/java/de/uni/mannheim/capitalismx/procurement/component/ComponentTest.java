package de.uni.mannheim.capitalismx.procurement.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class ComponentTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentType.class);

    private LocalDate gameDate;
    private ComponentType c;

    @BeforeTest
    public void setUp() {
        this.gameDate = LocalDate.of(1990, 1, 1);
        this.c = ComponentType.N_CPU_LEVEL_1;
    }

    // TODO NEW COMPONENTTEST
    /*
    @Test
    public void setSupplierCategoryTest() {
        this.c.setSupplierCategory(SupplierCategory.PREMIUM);
        Assert.assertTrue(c.getSupplierCostMultiplicator() >= 1.1);
        Assert.assertTrue(c.getSupplierCostMultiplicator() <= 1.5);
        Assert.assertTrue(c.getSupplierQuality() >= 80);
        Assert.assertTrue(c.getSupplierQuality() <= 100);
        Assert.assertTrue(c.getSupplierEcoIndex() >= 80);
        Assert.assertTrue(c.getSupplierEcoIndex() <= 100);
    }

    @Test
    public void calculateBaseCostTest() {
        this.c.setSupplierCategory(SupplierCategory.PREMIUM);
        double baseCost = c.calculateBaseCost(gameDate);
        Assert.assertTrue(baseCost <= 549.8779000000001 * 1.5);
        Assert.assertTrue(baseCost >= 549.8779000000001 * 1.1);
    }*/


}
