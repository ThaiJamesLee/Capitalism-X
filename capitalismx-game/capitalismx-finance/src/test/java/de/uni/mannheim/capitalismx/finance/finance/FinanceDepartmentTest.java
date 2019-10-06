package de.uni.mannheim.capitalismx.finance.finance;

import de.uni.mannheim.capitalismx.logistic.logistics.LogisticsDepartment;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.production.Machinery;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class FinanceDepartmentTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceDepartmentTest.class);

    @Test
    public void generateLoanSelectionTest () {
        FinanceDepartment financeDepartment = new FinanceDepartment();

        //TODO
        //Assert.assertEquals(finance.generateLoanSelection(100).get(0).getLoanAmount(), 100);
    }

    @Test
    public void generateInvestmentSelectionTest () {
        FinanceDepartment financeDepartment = new FinanceDepartment();

        Assert.assertNull(financeDepartment.generateInvestmentSelection(1000001));

        Assert.assertNotNull(financeDepartment.generateInvestmentSelection(100));
    }

    @Test
    public void addInvestmentTest () {
        FinanceDepartment financeDepartment = new FinanceDepartment();

        financeDepartment.addInvestment(new Investment(100, 0.1, 0.5));

        Assert.assertEquals(financeDepartment.getInvestments().size(), 1);
        Assert.assertEquals(financeDepartment.getCash(), 999900.0);
        Assert.assertEquals(financeDepartment.calculateTotalInvestmentAmount(), 100.0);
    }

    @Test
    public void calculateTotalWarehousingValuesTest() {
        FinanceDepartment financeDepartment = new FinanceDepartment();
        WarehousingDepartment.getInstance().buildWarehouse(LocalDate.of(2019,11, 30));
        Assert.assertEquals(financeDepartment.calculateTotalWarehousingValues(LocalDate.of(2019,11, 30)), 250000.0);
        Assert.assertEquals(financeDepartment.calculateTotalWarehousingValues(LocalDate.of(2020,11, 30)),
                financeDepartment.calculateResellPrice(250000.0, 14, 1));
        Assert.assertEquals(financeDepartment.calculateTotalWarehousingValues(LocalDate.of(2020,11, 29)), 250000.0);
    }

    @Test
    public void calculateTotalTruckValuesTest() {
        FinanceDepartment financeDepartment = new FinanceDepartment();
        LogisticsDepartment.getInstance().addTruckToFleet(new Truck("Test truck", 100.0, 100.0,
                1.0, 1.0), LocalDate.of(2019,11, 30));
        Assert.assertEquals(financeDepartment.calculateTotalTruckValues(LocalDate.of(2019,11, 30)), 100000.0);
        Assert.assertEquals(financeDepartment.calculateTotalTruckValues(LocalDate.of(2020,11, 30)),
                financeDepartment.calculateResellPrice(100000.0, 9, 1));
        Assert.assertEquals(financeDepartment.calculateTotalTruckValues(LocalDate.of(2020,11, 29)), 100000.0);
    }

    @Test
    public void calculateTotalMachineValuesTest() {
        FinanceDepartment financeDepartment = new FinanceDepartment();
        ProductionDepartment.getInstance().buyMachinery(new Machinery(LocalDate.of(2019,11, 30)),
                LocalDate.of(2019,11, 30));
        Assert.assertEquals(financeDepartment.calculateTotalMachineValues(LocalDate.of(2019,11, 30)), (100000.0 + 500) * 1.2);
        Assert.assertEquals(financeDepartment.calculateTotalMachineValues(LocalDate.of(2020,11, 30)),
                financeDepartment.calculateResellPrice((100000.0 + 500) * 1.2, 20, 1));
        Assert.assertEquals(financeDepartment.calculateTotalMachineValues(LocalDate.of(2020,11, 29)), (100000.0 + 500) * 1.2);
    }

    @Test
    public void calculateAssetsTest(){
        FinanceDepartment financeDepartment = new FinanceDepartment();
        Assert.assertEquals(financeDepartment.calculateAssets(LocalDate.now()), 0.0);
    }

    @Test
    public  void calculateLiabilitiesTest(){
        FinanceDepartment financeDepartment = new FinanceDepartment();
        Assert.assertEquals(financeDepartment.calculateLiabilities(LocalDate.now()), 0.0);
    }

    @Test
    public  void calculateAssetsSoldTest(){
        FinanceDepartment financeDepartment = new FinanceDepartment();
        Assert.assertEquals(financeDepartment.calculateAssetsSold(LocalDate.now()), 0.0);
    }

    @Test
    public  void calculateTotalHRCostsTest(){
        FinanceDepartment financeDepartment = new FinanceDepartment();
        Assert.assertEquals(financeDepartment.calculateTotalHRCosts(), 0.0);
    }

    @Test
    public  void calculateTotalWarehouseCostsTest(){
        FinanceDepartment financeDepartment = new FinanceDepartment();
        Assert.assertEquals(financeDepartment.calculateTotalWarehouseCosts(), 0.0);
    }

    @Test
    public  void calculateTotalLogisticsCostsTest(){
        FinanceDepartment financeDepartment = new FinanceDepartment();
        Assert.assertEquals(financeDepartment.calculateTotalLogisticsCosts(), 0.0);
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
