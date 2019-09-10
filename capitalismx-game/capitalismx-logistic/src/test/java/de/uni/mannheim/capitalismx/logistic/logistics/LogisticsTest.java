package de.uni.mannheim.capitalismx.logistic.logistics;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LogisticsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsTest.class);

    @Test
    public void generateExternalPartnerSelectionTest() {
        Logistics logistics = Logistics.getInstance();

        Assert.assertEquals(logistics.generateExternalPartnerSelection().size(), 9);
    }

    @Test
    public void generateTruckSelectionTest() {
        Logistics logistics = Logistics.getInstance();

        Assert.assertEquals(logistics.generateTruckSelection().size(), 6);
    }
}
