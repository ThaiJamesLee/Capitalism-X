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
        CustomerSatisfaction.getInstance();
    }

    @Test
    public void calculateCustomerSatisfactionTest() {
        Assert.assertNotNull(CustomerSatisfaction.getInstance().calculateCustomerSatisfaction(LocalDate.of(1990,1,1)));
    }
}
