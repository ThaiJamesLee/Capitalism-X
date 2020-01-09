package de.uni.mannheim.capitalismx.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class CustomerSatisfactionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSatisfaction.class);

    @BeforeTest
    public void setUp() {

    }

    @Test
    public void calculateCustomerSatisfactionTest() {
        CustomerSatisfaction customerSatisfaction = CustomerSatisfaction.createInstance();
        double custSatistfactionScore = customerSatisfaction.calculateCustomerSatisfaction(LocalDate.of(1990,1,1));
        String message = "CustomerSatisfaction: " +custSatistfactionScore;

        LOGGER.info(message);
        Assert.assertNotNull(custSatistfactionScore);
    }
}
