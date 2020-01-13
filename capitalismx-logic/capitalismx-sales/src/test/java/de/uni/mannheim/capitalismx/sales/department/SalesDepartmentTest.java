package de.uni.mannheim.capitalismx.sales.department;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.production.*;
import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author duly
 */
public class SalesDepartmentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesDepartmentTest.class);

    private ProductionDepartment productionDepartment;
    private LocalDate initDate;


    @BeforeTest
    public void setUp() {
        initDate = LocalDate.of(1990,11,1);
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
            System.out.println(productionDepartment.launchProduct(p, 100, 200));

            Product p2 = new Product("test2", ProductCategory.GAME_BOY, components);
            p2.setLaunchDate(LocalDate.of(1990, 1, 1));
            System.out.println(productionDepartment.launchProduct(p2, 100, 200));
        } catch (InvalidSetOfComponentsException e) {
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
        salesDepartment.generateContracts(initDate, productionDepartment, 1);

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
        salesDepartment.generateContracts(initDate, productionDepartment, 1);

        Assert.assertTrue(salesDepartment.getAvailableContracts().size() > 0);

        // set one contract to active.
        salesDepartment.addContractToActive(salesDepartment.getAvailableContracts().get(0), initDate.plusDays(1));

        Assert.assertTrue(salesDepartment.getActiveContracts().size() > 0);

    }
    
}
