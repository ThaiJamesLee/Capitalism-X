package de.uni.mannheim.capitalismx.warehouse;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.procurement.component.Unit;
import de.uni.mannheim.capitalismx.production.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class WarehousingDepartmentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehousingDepartment.class);

    @BeforeTest
    public void setUp() {
        WarehousingDepartment.getInstance();
    }

    @Test
    public void storeUnitsTestAndCalculateStoredUnitsTest() {
        ArrayList<Component> components = new ArrayList<>();
        LocalDate gameDate = LocalDate.of(1990,1,1);
        Component cpu = new Component(ComponentType.N_CPU_LEVEL_1);
        cpu.setSupplierCategory(SupplierCategory.CHEAP, gameDate);
        components.add(cpu);
        Component displayCase =  new Component(ComponentType.N_DISPLAYCASE_LEVEL_1);
        displayCase.setSupplierCategory(SupplierCategory.CHEAP, gameDate);
        components.add(displayCase);
        Component powerSupply =  new Component(ComponentType.N_POWERSUPPLY_LEVEL_1);
        powerSupply.setSupplierCategory(SupplierCategory.CHEAP, gameDate);
        components.add(powerSupply);
        Component software =  new Component(ComponentType.N_SOFTWARE_LEVEL_1);
        software.setSupplierCategory(SupplierCategory.CHEAP, gameDate);
        components.add(software);
        Component storage =  new Component(ComponentType.N_STORAGE_LEVEL_1);
        storage.setSupplierCategory(SupplierCategory.CHEAP, gameDate);
        components.add(storage);

        try {
            ProductionDepartment.getInstance().buyMachinery(new Machinery(gameDate), gameDate);
        } catch (NoMachinerySlotsAvailableException e) {
            System.out.println(e.getMessage());
        }

        try {
            Product notebook = new Product("Notebook", ProductCategory.NOTEBOOK, components);
            ProductionDepartment.getInstance().launchProduct(notebook, 10, 200);
            WarehousingDepartment.getInstance().storeUnits();
            int numberStoredUnits = 0;
            HashMap<Unit, Integer> inventory = new HashMap<>(WarehousingDepartment.getInstance().getInventory());
            for (HashMap.Entry<Unit, Integer> entry : inventory.entrySet()) {
                numberStoredUnits += entry.getValue();
            }
            Assert.assertEquals(numberStoredUnits, 10);
            Assert.assertEquals(WarehousingDepartment.getInstance().calculateStoredUnits(), 10);
        } catch (InvalidSetOfComponentsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void buildWarehouseTest() {
        try {
            WarehousingDepartment.getInstance().buildWarehouse(LocalDate.of(1990, 1, 1));
            Assert.assertEquals(WarehousingDepartment.getInstance().getWarehouses().size(), 1);
        } catch (NoWarehouseSlotsAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void sellProductTest() {
        HashMap<Unit, Integer> inventory = new HashMap<>(WarehousingDepartment.getInstance().getInventory());
        WarehousingDepartment.getInstance().sellProducts(inventory);
        inventory = new HashMap<>(WarehousingDepartment.getInstance().getInventory());
        int numberStoredUnits = 0;
        for(HashMap.Entry<Unit, Integer> entry : inventory.entrySet()) {
            numberStoredUnits += entry.getValue();
        }
        Assert.assertEquals(numberStoredUnits, 0);
    }

    @Test
    public void sellWarehouseTest() {
        ArrayList<Warehouse> warehouses = new ArrayList<>(WarehousingDepartment.getInstance().getWarehouses());
        try {
            for (Warehouse w : warehouses) {
                if (w.getWarehouseType() == WarehouseType.BUILT) {
                    WarehousingDepartment.getInstance().sellWarehouse(w);
                }
            }
            Assert.assertEquals(WarehousingDepartment.getInstance().getWarehouses().size(), 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void rentWarehouseTest() {
        try {
            WarehousingDepartment.getInstance().rentWarehouse(LocalDate.of(1990, 1, 1));
            Assert.assertEquals(WarehousingDepartment.getInstance().getWarehouses().size(), 1);
        } catch (NoWarehouseSlotsAvailableException e) {
            System.out.println(e.getMessage());
        }
    }
}
