package de.uni.mannheim.capitalismx.logistic.logistics;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;


public class InternalFleetTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalFleetTest.class);

    @Test
    public void calculateCapacityFleetTest() {
        InternalFleet internalFleet = new InternalFleet();

        Assert.assertEquals(internalFleet.calculateCapacityFleet(), 0);

        internalFleet.addTruckToFleet(new Truck(100, 100, 1.0, 1.0), LocalDate.now());
        Assert.assertEquals(internalFleet.calculateCapacityFleet(), 1000);

        internalFleet.addTruckToFleet(new Truck(100, 100, 1.0, 1.0), LocalDate.now());
        Assert.assertEquals(internalFleet.calculateCapacityFleet(), 2000);

        internalFleet.decreaseCapacityFleetRel(0.1);
        Assert.assertEquals(internalFleet.calculateCapacityFleet(), 1800);
    }

    @Test
    public void calculateEcoIndexFleetTest() {
        InternalFleet internalFleet = new InternalFleet();

        Assert.assertEquals(internalFleet.calculateEcoIndexFleet(), 0.0);

        internalFleet.addTruckToFleet(new Truck(100, 100, 1.0, 1.0), LocalDate.now());
        Assert.assertEquals(internalFleet.calculateEcoIndexFleet(), 100.0);

        internalFleet.addTruckToFleet(new Truck(50, 100, 1.0, 1.0), LocalDate.now());
        Assert.assertEquals(internalFleet.calculateEcoIndexFleet(), 75.0);
    }

    @Test
    public void calculateQualityIndexFleet() {
        InternalFleet internalFleet = new InternalFleet();

        Assert.assertEquals(internalFleet.calculateQualityIndexFleet(), 0.0);

        internalFleet.addTruckToFleet(new Truck(100, 100, 1.0, 1.0), LocalDate.now());
        Assert.assertEquals(internalFleet.calculateQualityIndexFleet(), 100.0);

        internalFleet.addTruckToFleet(new Truck(100, 50, 1.0, 1.0), LocalDate.now());
        Assert.assertEquals(internalFleet.calculateQualityIndexFleet(), 75.0);
    }

}
