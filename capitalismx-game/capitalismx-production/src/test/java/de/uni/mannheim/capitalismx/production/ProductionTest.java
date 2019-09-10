package de.uni.mannheim.capitalismx.production;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentCategory;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Production.class);

    private Machinery machinery;
    private List<Component> components;

    @BeforeTest
    public void setUp() {
        Production.getInstance();
        this.machinery = new Machinery(LocalDate.of(1990,1,1));
        this.components = new ArrayList<>();
        LocalDate gameDate = LocalDate.of(1990,1,1);
        Component cpu = Component.N_CPU_LEVEL_1;
        cpu.setSupplierCategory(SupplierCategory.CHEAP);
        cpu.calculateBaseCost(gameDate);
        components.add(cpu);
        Component displayCase = Component.N_DISPLAYCASE_LEVEL_1;
        displayCase.setSupplierCategory(SupplierCategory.CHEAP);
        displayCase.calculateBaseCost(gameDate);
        components.add(displayCase);
        Component powerSupply = Component.N_POWERSUPPLY_LEVEL_1;
        powerSupply.setSupplierCategory(SupplierCategory.CHEAP);
        powerSupply.calculateBaseCost(gameDate);
        components.add(powerSupply);
        Component software = Component.N_SOFTWARE_LEVEL_1;
        software.setSupplierCategory(SupplierCategory.CHEAP);
        software.calculateBaseCost(gameDate);
        components.add(software);
        Component storage = Component.N_STORAGE_LEVEL_1;
        storage.setSupplierCategory(SupplierCategory.CHEAP);
        storage.calculateBaseCost(gameDate);
        components.add(storage);
    }

    @Test
    public void getAllAvailableComponentsTest() {
        ArrayList<Component> components = new ArrayList<>(Production.getInstance().getAllAvailableComponents(LocalDate.of(1990,1,1)));
        Assert.assertEquals(components.size(), 18);
        components = new ArrayList<>(Production.getInstance().getAllAvailableComponents(LocalDate.of(2019,1,1)));
        Component[] allComponents = Component.values();
        Assert.assertEquals(components.size(), allComponents.length);
    }

    @Test
    public void getAvailableComponentsOfComponentCategoryTest() {
        ArrayList<Component> components = new ArrayList<>(Production.getInstance().getAvailableComponentsOfComponentCategory(LocalDate.of(1990,1,1), ComponentCategory.N_CPU));
        Assert.assertEquals(components.size(), 1);
        components = new ArrayList<>(Production.getInstance().getAvailableComponentsOfComponentCategory(LocalDate.of(2019,1,1), ComponentCategory.N_CPU));
        Assert.assertEquals(components.size(), 8);
    }

    @Test
    public void buyMachineryTest() {
        Production.getInstance().buyMachinery(this.machinery, LocalDate.of(1990,1,1));
        Assert.assertEquals(Production.getInstance().getMachines().size(), 1);
        Assert.assertEquals(Production.getInstance().getMonthlyAvailableMachineCapacity(), 500.0);
    }

    @Test
    public void calculateMachineryResellPricesTest() {
        Map<Machinery, Double> machineryResellPrice = new HashMap<>();
        machineryResellPrice.put(this.machinery, this.machinery.calculateResellPrice());
        Assert.assertEquals(Production.getInstance().calculateMachineryResellPrices(), machineryResellPrice);
    }

    @Test
    public void launchProductTest() {
        Assert.assertEquals(Production.getInstance().launchProduct("Notebook", ProductCategory.NOTEBOOK, this.components, 10, 9), -1.0);
        Production.getInstance().launchProduct("Notebook", ProductCategory.NOTEBOOK, this.components, 10, 10);
        Assert.assertEquals(Production.getInstance().getNumberProducedProducts().size(), 1);
        Assert.assertEquals(Production.getInstance().getNumberUnitsProducedPerMonth(), 10);
    }

    @Test
    public void produceProductTest() {
        Map<Product, Integer> products = Production.getInstance().getNumberProducedProducts();
        for(HashMap.Entry<Product, Integer> p : products.entrySet()) {
            Production.getInstance().produceProduct(p.getKey(), 10, 10);
        }
        Assert.assertEquals(Production.getInstance().getNumberUnitsProducedPerMonth(), 20);
    }

    @Test
    public void calculateProductionTechnologyFactorTest() {
        Assert.assertEquals(Production.getInstance().calculateProductionTechnologyFactor(), 1.2);
    }

    @Test
    public void calculateProductionTechnologyTest() {
        Assert.assertEquals(Production.getInstance().calculateProductionTechnology(), ProductionTechnology.BRANDNEW);
    }



    @Test
    public void sellMachineryTest(){
        Production.getInstance().sellMachinery(this.machinery);
        Assert.assertEquals(Production.getInstance().getMachines().size(), 0);
        Assert.assertEquals(Production.getInstance().getMonthlyAvailableMachineCapacity(), 0.0);
    }
}