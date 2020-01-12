package de.uni.mannheim.capitalismx.production;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Product.class);

    private Product product;

    @BeforeTest
    public void setUp() {
        ArrayList<Component> components = new ArrayList<>();
        LocalDate gameDate = LocalDate.of(1990,1,1);
        Component cpu = new Component(ComponentType.N_CPU_LEVEL_1);
        cpu.setSupplierCategory(SupplierCategory.CHEAP);
        cpu.calculateBaseCost(gameDate);
        components.add(cpu);
        Component displayCase = new Component(ComponentType.N_DISPLAYCASE_LEVEL_1);
        displayCase.setSupplierCategory(SupplierCategory.CHEAP);
        displayCase.calculateBaseCost(gameDate);
        components.add(displayCase);
        Component powerSupply = new Component(ComponentType.N_POWERSUPPLY_LEVEL_1);
        powerSupply.setSupplierCategory(SupplierCategory.CHEAP);
        powerSupply.calculateBaseCost(gameDate);
        components.add(powerSupply);
        Component software = new Component(ComponentType.N_SOFTWARE_LEVEL_1);
        software.setSupplierCategory(SupplierCategory.CHEAP);
        software.calculateBaseCost(gameDate);
        components.add(software);
        Component storage = new Component(ComponentType.N_STORAGE_LEVEL_1);
        storage.setSupplierCategory(SupplierCategory.CHEAP);
        storage.calculateBaseCost(gameDate);
        components.add(storage);
        try {
            this.product = new Product("Notebook", ProductCategory.NOTEBOOK, components);
        } catch (InvalidSetOfComponentsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void calculateTotalVariableCosts() {
        double totalComponentCosts = this.product.getTotalComponentCosts();
        Assert.assertEquals(this.product.calculateTotalVariableCosts(), totalComponentCosts + 30);
    }

    @Test
    public void calculateTotalProcurementQualityTest() {
        ArrayList<Component> components = new ArrayList<>(this.product.getComponents());
        double totalProcurementQualtiy = 0;
        for(Component c : components) {
            totalProcurementQualtiy += (0.4 * c.getSupplierEcoIndex() + 0.6 * c.getSupplierQuality()) * c.getBaseUtility();
        }
        Assert.assertEquals(this.product.calculateTotalProcurementQuality(), totalProcurementQualtiy/ components.size());
    }

    @Test
    public void calculateTotalProductQualityTest() {
        double totalProcurementQuality = this.product.calculateTotalProcurementQuality();
        Assert.assertEquals(this.product.calculateTotalProductQuality(1,1,1), totalProcurementQuality);
    }

    @Test
    public void calculateAverageBaseQualityTest() {
        ArrayList<Component> components = new ArrayList<>(this.product.getComponents());
        double aggregatedComponentSupplierQuality = 0;
        for(Component c : components) {
            aggregatedComponentSupplierQuality += c.getSupplierQuality();
        }
        Assert.assertEquals(this.product.calculateAverageBaseQuality(), aggregatedComponentSupplierQuality/ components.size());
    }
}
