package de.uni.mannheim.capitalismx.production;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class MachineryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Machinery.class);

    private Machinery machinery;

    @BeforeTest
    public void setUp() {
        this.machinery = new Machinery(LocalDate.of(1990,1,1));
    }

    @Test
    public void depreciateMachineryTest() {
        this.machinery.depreciateMachinery(true, LocalDate.of(1990,1,1));
        Assert.assertEquals(this.machinery.getProductionTechnology(), ProductionTechnology.GOOD_CONDITIONS);
        this.machinery = new Machinery(LocalDate.of(1990,1,1));
        this.machinery.depreciateMachinery(false, LocalDate.of(1995,1,1));
        Assert.assertEquals(this.machinery.getProductionTechnology(), ProductionTechnology.PURCHASED_MORE_THAN_FIVE_YEARS_AGO);
    }

    @Test
    public void maintainAndRepairMachineryTest() {
        this.machinery = new Machinery(LocalDate.of(1990,1,1));
        this.machinery.depreciateMachinery(true,LocalDate.of(1990,1,1));
        this.machinery.maintainAndRepairMachinery(LocalDate.of(1990,1,1));
        Assert.assertEquals(this.machinery.getProductionTechnology(), ProductionTechnology.PURCHASED_MORE_THAN_FIVE_YEARS_AGO);
    }

    @Test
    public void upgradeMachineryTest() {
        this.machinery = new Machinery(LocalDate.of(1990,1,1));
        this.machinery.depreciateMachinery(true,LocalDate.of(1990,1,1));
        this.machinery.upgradeMachinery(LocalDate.of(1990,1,1));
        Assert.assertEquals(this.machinery.getProductionTechnology(), ProductionTechnology.BRANDNEW);
        Assert.assertEquals(this.machinery.getMachineryCapacity(), 600);
    }

    @Test
    public void calculateResellPrice() {
        this.machinery = new Machinery(LocalDate.of(1990,1,1));
        Assert.assertEquals(this.machinery.calculateResellPrice(), 100500.0);
    }
}
