package de.uni.mannheim.capitalismx.warehouse;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.production.Machinery;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.ProductCategory;
import de.uni.mannheim.capitalismx.production.Production;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class WarehousingTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Warehousing.class);

    @BeforeTest
    public void setUp() {
        Warehousing.getInstance();
    }

    @Test
    public void storeUnitsTestAndCalculateStoredUnitsTest() {
        ArrayList<Component> components = new ArrayList<>();
        LocalDate gameDate = LocalDate.of(1990,1,1);
        Component cpu = new Component(ComponentType.N_CPU_LEVEL_1);
        cpu.setSupplierCategory(SupplierCategory.CHEAP);
        cpu.calculateBaseCost(gameDate);
        components.add(cpu);
        Component displayCase =  new Component(ComponentType.N_DISPLAYCASE_LEVEL_1);
        displayCase.setSupplierCategory(SupplierCategory.CHEAP);
        displayCase.calculateBaseCost(gameDate);
        components.add(displayCase);
        Component powerSupply =  new Component(ComponentType.N_POWERSUPPLY_LEVEL_1);
        powerSupply.setSupplierCategory(SupplierCategory.CHEAP);
        powerSupply.calculateBaseCost(gameDate);
        components.add(powerSupply);
        Component software =  new Component(ComponentType.N_SOFTWARE_LEVEL_1);
        software.setSupplierCategory(SupplierCategory.CHEAP);
        software.calculateBaseCost(gameDate);
        components.add(software);
        Component storage =  new Component(ComponentType.N_STORAGE_LEVEL_1);
        storage.setSupplierCategory(SupplierCategory.CHEAP);
        storage.calculateBaseCost(gameDate);
        components.add(storage);

        Production.getInstance().buyMachinery(new Machinery(gameDate), gameDate);
        Product notebook = new Product("Notebook", ProductCategory.NOTEBOOK, components);
        Production.getInstance().launchProduct(notebook, 10, 200);
        Warehousing.getInstance().storeUnits();
        int numberStoredUnits = 0;
        HashMap<Product, Integer> inventory = new HashMap<>(Warehousing.getInstance().getInventory());
        for(HashMap.Entry<Product, Integer> entry : inventory.entrySet()) {
            numberStoredUnits += entry.getValue();
        }
        Assert.assertEquals(numberStoredUnits, 10);
        Assert.assertEquals(Warehousing.getInstance().calculateStoredUnits(), 10);
    }

    @Test
    public void buildWarehouseTest() {
        Warehousing.getInstance().buildWarehouse(LocalDate.of(1990,1,1));
        Assert.assertEquals(Warehousing.getInstance().getWarehouses().size(), 1);
    }

    @Test
    public void rentWarehouseTest() {
        Warehousing.getInstance().rentWarehouse();
        Assert.assertEquals(Warehousing.getInstance().getWarehouses().size(), 2);
    }

    @Test
    public void sellProductTest() {
        HashMap<Product, Integer> inventory = new HashMap<>(Warehousing.getInstance().getInventory());
        Warehousing.getInstance().sellProducts(inventory);
        inventory = new HashMap<>(Warehousing.getInstance().getInventory());
        int numberStoredUnits = 0;
        for(HashMap.Entry<Product, Integer> entry : inventory.entrySet()) {
            numberStoredUnits += entry.getValue();
        }
        Assert.assertEquals(numberStoredUnits, 0);
    }

    @Test
    public void sellWarehouseTest() {
        ArrayList<Warehouse> warehouses = new ArrayList<>(Warehousing.getInstance().getWarehouses());
        for(Warehouse w : warehouses) {
            if(w.getWarehouseType() == WarehouseType.BUILT) {
                Warehousing.getInstance().sellWarehouse(w);
            }
        }
        Assert.assertEquals(Warehousing.getInstance().getWarehouses().size(), 1);
    }
}
