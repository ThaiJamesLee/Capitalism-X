package de.uni.mannheim.capitalismx.logistic.logistics;

import de.uni.mannheim.capitalismx.logistic.logistics.exception.NotEnoughTruckCapacityException;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
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

    @Test
    public void calculateCostsLogisticsTest() {
        LogisticsDepartment logisticsDepartment = LogisticsDepartment.getInstance();

        Truck truck = new Truck("Truck 1", 100, 100, 1, 1);
        try {
            logisticsDepartment.addTruckToFleet(truck, LocalDate.of(1990,1,1));
        } catch (NotEnoughTruckCapacityException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(logisticsDepartment.calculateCostsLogistics(), DecimalRound.round((100000 * 0.01) / 12, 2));
    }

    @Test
    public void calculateTotalDeliveryCostsTest() {
        LogisticsDepartment logisticsDepartment = LogisticsDepartment.createInstance();

        logisticsDepartment.setDeliveredProducts(100);

        Truck truck = new Truck("Truck 1", 100, 100, 1, 1);
        try {
            logisticsDepartment.addTruckToFleet(truck, LocalDate.of(1990,1,1));
        } catch (NotEnoughTruckCapacityException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(logisticsDepartment.calculateTotalDeliveryCosts(), Math.ceil(100 / 1000.0) * DecimalRound.round(200 * 1, 2) + (100 * 2));

        logisticsDepartment.setDeliveredProducts(1100);
        Assert.assertEquals(logisticsDepartment.calculateTotalDeliveryCosts(), 1 * DecimalRound.round(200 * 1, 2) + (1 * 1000 * 2) + (100 * 15));

        logisticsDepartment.addExternalPartner(new ExternalPartner("Partner 1", 100, 100, 100, 1, 1));
        Assert.assertEquals(logisticsDepartment.calculateTotalDeliveryCosts(), 1 * DecimalRound.round(200 * 1, 2) + (1 * 1000 * 2) + 100 * DecimalRound.round(5 * 1,2));
    }
}
