package de.uni.mannheim.capitalismx.logistic.logistics;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Locale;


public class LogisticsDepartmentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsDepartmentTest.class);

    @Test
    public void generateExternalPartnerSelectionTest() {
        LogisticsDepartment logisticsDepartment = LogisticsDepartment.getInstance();

        Assert.assertEquals(logisticsDepartment.generateExternalPartnerSelection().size(), 9);
    }

    @Test
    public void generateTruckSelectionTest() {
        LogisticsDepartment logisticsDepartment = LogisticsDepartment.getInstance();

        Assert.assertEquals(logisticsDepartment.generateTruckSelection(Locale.ENGLISH).size(), 6);
    }
}
