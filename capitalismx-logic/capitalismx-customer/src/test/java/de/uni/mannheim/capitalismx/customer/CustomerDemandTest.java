package de.uni.mannheim.capitalismx.customer;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;

/**
 * @author duly
 */
public class CustomerDemandTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDemand.class);

    @BeforeTest
    public void setUp() {
        CustomerDemand.getInstance();
    }

    @Test
    public void calculateSalesFiguresTest() {
        CustomerDemand.getInstance().calculateSalesFigures(100, LocalDate.of(1990,1,1));
        // TODO
    }
}
