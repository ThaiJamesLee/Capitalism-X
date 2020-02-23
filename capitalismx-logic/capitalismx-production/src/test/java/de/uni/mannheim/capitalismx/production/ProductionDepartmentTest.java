package de.uni.mannheim.capitalismx.production;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.ComponentCategory;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.production.exceptions.NoMachinerySlotsAvailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductionDepartmentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductionDepartment.class);

    private Machinery machinery;
    private List<Component> components;

    @BeforeTest
    public void setUp() {
        ProductionDepartment.getInstance();
        this.machinery = new Machinery(LocalDate.of(1990,1,1));
        this.components = new ArrayList<>();
        LocalDate gameDate = LocalDate.of(1990,1,1);
        Component cpu = new Component(ComponentType.N_CPU_LEVEL_1);
        cpu.setSupplierCategory(SupplierCategory.CHEAP, gameDate);
        components.add(cpu);
        Component displayCase = new Component(ComponentType.N_DISPLAYCASE_LEVEL_1);
        displayCase.setSupplierCategory(SupplierCategory.CHEAP, gameDate);
        components.add(displayCase);
        Component powerSupply = new Component(ComponentType.N_POWERSUPPLY_LEVEL_1);
        powerSupply.setSupplierCategory(SupplierCategory.CHEAP, gameDate);
        components.add(powerSupply);
        Component software = new Component(ComponentType.N_SOFTWARE_LEVEL_1);
        software.setSupplierCategory(SupplierCategory.CHEAP, gameDate);
        components.add(software);
        Component storage = new Component(ComponentType.N_STORAGE_LEVEL_1);
        storage.setSupplierCategory(SupplierCategory.CHEAP, gameDate);
        components.add(storage);
    }

    @Test
    public void getAllAvailableComponentsTest() {
        ArrayList<ComponentType> components = new ArrayList<>(ProductionDepartment.getInstance().getAllAvailableComponents(LocalDate.of(1990,1,1)));
        Assert.assertEquals(components.size(), 19);
        components = new ArrayList<>(ProductionDepartment.getInstance().getAllAvailableComponents(LocalDate.of(2019,1,1)));
        ComponentType[] allComponents = ComponentType.values();
        Assert.assertEquals(components.size(), allComponents.length);
    }

    @Test
    public void getAvailableComponentsOfComponentCategoryTest() {
        ArrayList<ComponentType> components = new ArrayList<>(ProductionDepartment.getInstance().getAvailableComponentsOfComponentCategory(LocalDate.of(1990,1,1), ComponentCategory.N_CPU));
        Assert.assertEquals(components.size(), 1);
        components = new ArrayList<>(ProductionDepartment.getInstance().getAvailableComponentsOfComponentCategory(LocalDate.of(2019,1,1), ComponentCategory.N_CPU));
        Assert.assertEquals(components.size(), 8);
    }

    @Test
    public void buyMachineryTest() {
        try {
            ProductionDepartment.getInstance().buyMachinery(this.machinery, LocalDate.of(1990, 1, 1));
            Assert.assertEquals(ProductionDepartment.getInstance().getMachines().size(), 1);
            Assert.assertEquals(ProductionDepartment.getInstance().getMonthlyAvailableMachineCapacity(), 50.0);
        } catch (NoMachinerySlotsAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void calculateMachineryResellPricesTest() {
        Map<Machinery, Double> machineryResellPrice = new HashMap<>();
        machineryResellPrice.put(this.machinery, this.machinery.calculateResellPrice());
        Assert.assertEquals(ProductionDepartment.getInstance().calculateMachineryResellPrices(), machineryResellPrice);
    }

    @Test
    public void launchProductTest() {
        try {
            Product notebook = new Product("Notebook", ProductCategory.NOTEBOOK, this.components);
            Assert.assertEquals(ProductionDepartment.getInstance().launchProduct(notebook, LocalDate.of(1990, 1, 1), true), 10000.0);
            ProductionDepartment.getInstance().launchProduct(notebook, LocalDate.of(1990, 1, 1), true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void produceProductTest() {
        Map<Product, Integer> products = ProductionDepartment.getInstance().getNumberProducedProducts();
        try {
            for (HashMap.Entry<Product, Integer> p : products.entrySet()) {
                ProductionDepartment.getInstance().produceProduct(p.getKey(), 10, 10, true);
            }
            //Assert.assertEquals(ProductionDepartment.getInstance().getNumberUnitsProducedPerMonth(), 10);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void calculateProductionTechnologyFactorTest() {
        Assert.assertEquals(ProductionDepartment.getInstance().calculateProductionTechnologyFactor(), 1.2);
    }

    @Test
    public void calculateProductionTechnologyTest() {
        Assert.assertEquals(ProductionDepartment.getInstance().calculateProductionTechnology(), ProductionTechnology.BRANDNEW);
    }



    @Test
    public void sellMachineryTest(){
        ProductionDepartment.getInstance().sellMachinery(this.machinery);
        Assert.assertEquals(ProductionDepartment.getInstance().getMachines().size(), 0);
        Assert.assertEquals(ProductionDepartment.getInstance().getMonthlyAvailableMachineCapacity(), 0.0);
    }
}
