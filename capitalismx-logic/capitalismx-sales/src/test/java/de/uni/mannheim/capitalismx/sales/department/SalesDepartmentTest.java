package de.uni.mannheim.capitalismx.sales.department;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.production.*;
import de.uni.mannheim.capitalismx.production.exceptions.NoMachinerySlotsAvailableException;
import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author duly
 */
public class SalesDepartmentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesDepartmentTest.class);

    private ProductionDepartment productionDepartment;
    private LocalDate initDate;

    private Map<Product, Double> demandPercentages;


    @BeforeTest
    public void setUp() {
        initDate = LocalDate.of(1990,11,1);
        demandPercentages = new HashMap<>();
        productionDepartment = ProductionDepartment.getInstance();

        for(int i = 0; i<5; i++) {
            productionDepartment.getLevelingMechanism().levelUp();
        }
        try {
            productionDepartment.buyMachinery(new Machinery(initDate), initDate);
        } catch (NoMachinerySlotsAvailableException e) {
            e.printStackTrace();
        }
        List<Component> components = new ArrayList<>();

        // set supplier category to be able to calculate procurement quality and therefore product quality
        components.add(new Component(ComponentType.G_DISPLAYCASE_LEVEL_1, SupplierCategory.CHEAP, initDate));
        components.add(new Component(ComponentType.G_POWERSUPPLY_LEVEL_1,SupplierCategory.CHEAP, initDate));
        components.add(new Component(ComponentType.G_CPU_LEVEL_1, SupplierCategory.CHEAP, initDate));
        components.add(new Component(ComponentType.G_CONNECTIVITY_LEVEL_1, SupplierCategory.CHEAP, initDate));

        try {
            Product p = new Product("test", ProductCategory.GAME_BOY, components);
            p.setLaunchDate(LocalDate.of(1990, 1, 1));
            System.out.println(productionDepartment.launchProduct(p, LocalDate.of(1990, 1, 1), true));
            String launchInfo = "Cost of product P launch: " + productionDepartment.launchProduct(p, LocalDate.of(1990, 1, 1), true);
            LOGGER.info(launchInfo);

            Product p2 = new Product("test2", ProductCategory.GAME_BOY, components);
            p2.setLaunchDate(LocalDate.of(1990, 1, 1));
            String launchInfo2 = "Cost of product P2 launch: " + productionDepartment.launchProduct(p2, LocalDate.of(1990, 1, 1), true);
            LOGGER.info(launchInfo2);

            demandPercentages.put(p, 0.9);
            demandPercentages.put(p2, 1.2);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Test
    public void levelUpTest() {
        SalesDepartment salesDepartment = SalesDepartment.createInstance();
        for (int i = 1; i<=salesDepartment.getMaxLevel(); i++) {
            salesDepartment.getLevelingMechanism().levelUp();
        }
        Assert.assertEquals(8, salesDepartment.getAvailableSkills().size());
    }

    @Test
    public void salesDepartmentSkillTest() {
        SalesDepartment salesDepartment = SalesDepartment.createInstance();
        System.out.println( productionDepartment.getLaunchedProducts().size());
        salesDepartment.generateContracts(initDate, productionDepartment, demandPercentages);

        String machineCapacity = "Machine Capacity: " + productionDepartment.getDailyMachineCapacity() + " per day";
        LOGGER.info(machineCapacity);

        for(Contract c : salesDepartment.getAvailableContracts().getList()) {
            LOGGER.info(c.toString());
        }
        Assert.assertTrue(salesDepartment.getAvailableContracts().size() > 0);
    }

    @Test
    public void activeContractTest() {
        SalesDepartment salesDepartment = SalesDepartment.createInstance();
        System.out.println( productionDepartment.getLaunchedProducts().size());
        salesDepartment.generateContracts(initDate, productionDepartment, demandPercentages);

        Assert.assertTrue(salesDepartment.getAvailableContracts().size() > 0);

        // set one contract to active.
        salesDepartment.addContractToActive(salesDepartment.getAvailableContracts().get(0), initDate.plusDays(1));

        Assert.assertTrue(salesDepartment.getActiveContracts().size() > 0);

        Contract c = salesDepartment.getActiveContracts().get(0);

        c.setContractDoneDate(c.getContractStart().plusMonths(c.getTimeToFinish()));

        StringBuilder builder = new StringBuilder();
        builder.append("Contract start:");
        builder.append(c.getContractStart().toString());
        builder.append("; done:" );
        builder.append(c.getContractDoneDate());
        builder.append("; timetoFinish in Months:");
        builder.append(c.getTimeToFinish());
        builder.append("; uuid:");
        builder.append(c.getuId());
        String message = builder.toString();
        LOGGER.info(message);

        LocalDate overdueDate = initDate.plusMonths(c.getTimeToFinish()).plusDays(2);
        Assert.assertTrue(c.contractIsDue(overdueDate));
    }
    
}
