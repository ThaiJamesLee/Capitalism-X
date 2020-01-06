package de.uni.mannheim.capitalismx.warehouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class WarehouseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Warehouse.class);

    private Warehouse warehouse;

    @BeforeTest
    public void setUp() {
        this.warehouse = new Warehouse(WarehouseType.BUILT);
    }

    @Test
    public void depreciateWarehouseResaleValueTest() {
        this.warehouse.setBuildDate(LocalDate.of(1990,1,1));
        double resaleValue = 250000 - 25000 * this.warehouse.getDepreciationRateWarehouse() * 2;
        Assert.assertEquals(this.warehouse.depreciateWarehouseResaleValue(LocalDate.of(1992,1,1)), resaleValue);
    }
}
