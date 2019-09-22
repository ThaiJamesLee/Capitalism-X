package de.uni.mannheim.capitalismx.finance.finance;

import de.uni.mannheim.capitalismx.logistic.logistics.InternalFleet;
import de.uni.mannheim.capitalismx.logistic.logistics.Logistics;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.production.Machinery;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.Production;
import de.uni.mannheim.capitalismx.warehouse.Warehousing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class FinanceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceTest.class);

    @Test
    public void generateLoanSelectionTest () {
        Finance finance = new Finance();

        //TODO
        //Assert.assertEquals(finance.generateLoanSelection(100).get(0).getLoanAmount(), 100);
    }

    @Test
    public void generateInvestmentSelectionTest () {
        Finance finance = new Finance();

        Assert.assertNull(finance.generateInvestmentSelection(1000001));

        Assert.assertNotNull(finance.generateInvestmentSelection(100));
    }

    @Test
    public void addInvestmentTest () {
        Finance finance = new Finance();

        finance.addInvestment(new Investment(100, 0.1, 0.5));

        Assert.assertEquals(finance.getInvestments().size(), 1);
        Assert.assertEquals(finance.getCash(), 999900.0);
        Assert.assertEquals(finance.calculateTotalInvestmentAmount(), 100.0);
    }

    @Test
    public void calculateTotalWarehousingValuesTest() {
        Finance finance = new Finance();
        Warehousing.getInstance().buildWarehouse(LocalDate.of(2019,11, 30));
        Assert.assertEquals(finance.calculateTotalWarehousingValues(LocalDate.of(2019,11, 30)), 250000.0);
        Assert.assertEquals(finance.calculateTotalWarehousingValues(LocalDate.of(2020,11, 30)),
                finance.calculateResellPrice(250000.0, 14, 1));
        Assert.assertEquals(finance.calculateTotalWarehousingValues(LocalDate.of(2020,11, 29)), 250000.0);
    }

    @Test
    public void calculateTotalTruckValuesTest() {
        Finance finance = new Finance();
        Logistics.getInstance().addTruckToFleet(new Truck("Test truck", 100.0, 100.0,
                1.0, 1.0), LocalDate.of(2019,11, 30));
        Assert.assertEquals(finance.calculateTotalTruckValues(LocalDate.of(2019,11, 30)), 100000.0);
        Assert.assertEquals(finance.calculateTotalTruckValues(LocalDate.of(2020,11, 30)),
                finance.calculateResellPrice(100000.0, 9, 1));
        Assert.assertEquals(finance.calculateTotalTruckValues(LocalDate.of(2020,11, 29)), 100000.0);
    }

    @Test
    public void calculateTotalMachineValuesTest() {
        Finance finance = new Finance();
        Production.getInstance().buyMachinery(new Machinery(LocalDate.of(2019,11, 30)),
                LocalDate.of(2019,11, 30));
        Assert.assertEquals(finance.calculateTotalMachineValues(LocalDate.of(2019,11, 30)), (100000.0 + 500) * 1.2);
        Assert.assertEquals(finance.calculateTotalMachineValues(LocalDate.of(2020,11, 30)),
                finance.calculateResellPrice((100000.0 + 500) * 1.2, 20, 1));
        Assert.assertEquals(finance.calculateTotalMachineValues(LocalDate.of(2020,11, 29)), (100000.0 + 500) * 1.2);
    }

    @Test
    public void calculateAssetsTest(){
        Finance finance = new Finance();
        Assert.assertEquals(finance.calculateAssets(LocalDate.now()), 0.0);
    }

    @Test
    public  void calculateLiabilitiesTest(){
        Finance finance = new Finance();
        Assert.assertEquals(finance.calculateLiabilities(LocalDate.now()), 0.0);
    }

    @Test
    public  void calculateAssetsSoldTest(){
        Finance finance = new Finance();
        Assert.assertEquals(finance.calculateAssetsSold(LocalDate.now()), 0.0);
    }

    @Test
    public  void calculateTotalHRCostsTest(){
        Finance finance = new Finance();
        Assert.assertEquals(finance.calculateTotalHRCosts(), 0.0);
    }

    @Test
    public  void calculateTotalWarehouseCostsTest(){
        Finance finance = new Finance();
        Assert.assertEquals(finance.calculateTotalWarehouseCosts(), 0.0);
    }

    @Test
    public  void calculateTotalLogisticsCostsTest(){
        Finance finance = new Finance();
        Assert.assertEquals(finance.calculateTotalLogisticsCosts(), 0.0);
    }

    /*@Test
    public  void calculateTotalProductionCostsTest(){
        Finance finance = new Finance();
        Assert.assertEquals(finance.calculateTotalProductionCosts(), 0.0);
    }

    @Test
    public  void calculateEbitTest(){
        Finance finance = new Finance();
        Assert.assertEquals(finance.calculateEbit(), 0.0);
    }

    @Test
    public  void calculateIncomeTaxTest(){
        Finance finance = new Finance();
        Assert.assertEquals(finance.calculateIncomeTax(), 0.0);
    }

    @Test
    public  void calculateNopatTest(){
        Finance finance = new Finance();
        Assert.assertEquals(finance.calculateNopat(), 0.0);
    }

    @Test
    public  void calculateCashTest(){
        Finance finance = new Finance();
        Assert.assertEquals(finance.calculateCash(LocalDate.now()), 1000000.0);
    }

    @Test
    public  void calculateNetWorthTest(){
        Finance finance = new Finance();
        Assert.assertEquals(finance.calculateNetWorth(LocalDate.now()), 1000000);
    }*/

}
