package de.uni.mannheim.capitalismx.procurement.component;

import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

import java.time.LocalDate;

public class Component {

    private ComponentCategory componentCategory;
    private String componentName;
    private int componentLevel;
    private double initialComponentPrice;
    private int baseUtility;
    private int availabilityDate;
    private SupplierCategory supplierCategory;
    private double supplierCostMultiplicator;
    private int supplierQuality;
    private int supplierEcoIndex;
    private double baseCost;
    private ComponentType componentType;

    public Component(ComponentType componentType) {
        this.componentCategory = componentType.getComponentCategory();
        this.componentName = componentType.getComponentName();
        this.componentLevel = componentType.getComponentLevel();
        this.initialComponentPrice = componentType.getInitialComponentPrice();
        this.baseUtility = componentType.getBaseUtility();
        this.availabilityDate = componentType.getAvailabilityDate();
        this.componentType = componentType;
    }

    public Component(ComponentType componentType, SupplierCategory supplierCategory) {
        this.componentCategory = componentType.getComponentCategory();
        this.componentName = componentType.getComponentName();
        this.componentLevel = componentType.getComponentLevel();
        this.initialComponentPrice = componentType.getInitialComponentPrice();
        this.baseUtility = componentType.getBaseUtility();
        this.availabilityDate = componentType.getAvailabilityDate();
        this.componentType = componentType;
        this.supplierCategory = supplierCategory;
        switch(supplierCategory) {
            case CHEAP:
                this.supplierCostMultiplicator = RandomNumberGenerator.getRandomDouble(1.1, 1.5);
                this.supplierQuality = RandomNumberGenerator.getRandomInt(80, 100);
                this.supplierEcoIndex = RandomNumberGenerator.getRandomInt(80, 100);
                break;
            case REGULAR:
                this.supplierCostMultiplicator = RandomNumberGenerator.getRandomDouble(0.85, 1.2);
                this.supplierQuality = RandomNumberGenerator.getRandomInt(55, 85);
                this.supplierEcoIndex = RandomNumberGenerator.getRandomInt(55, 85);
                break;
            case PREMIUM:
                this.supplierCostMultiplicator = RandomNumberGenerator.getRandomDouble(0.7, 1.0);
                this.supplierQuality = RandomNumberGenerator.getRandomInt(10, 60);
                this.supplierEcoIndex = RandomNumberGenerator.getRandomInt(10, 60);
        }
    }

    public void setSupplierCategory(SupplierCategory supplierCategory) {
        this.supplierCategory = supplierCategory;
        switch(supplierCategory) {
            case PREMIUM:
                this.supplierCostMultiplicator = RandomNumberGenerator.getRandomDouble(1.1, 1.5);
                this.supplierQuality = RandomNumberGenerator.getRandomInt(80, 100);
                this.supplierEcoIndex = RandomNumberGenerator.getRandomInt(80, 100);
                break;
            case REGULAR:
                this.supplierCostMultiplicator = RandomNumberGenerator.getRandomDouble(0.85, 1.2);
                this.supplierQuality = RandomNumberGenerator.getRandomInt(55, 85);
                this.supplierEcoIndex = RandomNumberGenerator.getRandomInt(55, 85);
                break;
            case CHEAP:
                this.supplierCostMultiplicator = RandomNumberGenerator.getRandomDouble(0.7, 1.0);
                this.supplierQuality = RandomNumberGenerator.getRandomInt(10, 60);
                this.supplierEcoIndex = RandomNumberGenerator.getRandomInt(10, 60);
        }
    }

    public ComponentCategory getComponentCategory() {
        return this.componentCategory;
    }

    public String getComponentName() {
        return this.componentName;
    }

    public int getComponentLevel() {
        return this.componentLevel;
    }

    public double getInitialComponentPrice() {
        return this.initialComponentPrice;
    }

    public int getBaseUtility() {
        return this.baseUtility;
    }

    public int getAvailabilityDate() {
        return this.availabilityDate;
    }

    public SupplierCategory getSupplierCategory() {
        return this.supplierCategory;
    }

    public double getSupplierCostMultiplicator() {
        return this.supplierCostMultiplicator;
    }

    public int getSupplierQuality() {
        return this.supplierQuality;
    }

    public int getSupplierEcoIndex() {
        return this.supplierEcoIndex;
    }

    public ComponentType getComponentType() {
        return this.componentType;
    }

    public double getBaseCost() {
        return this.baseCost;
    }

    public double calculateBaseCost(LocalDate gameDate) {
        int gameYear = gameDate.getYear();
        double tBPM = 0.0001 * Math.pow((gameYear - this.availabilityDate + 1), 5)
                - 0.0112 * Math.pow((gameYear - this.availabilityDate + 1), 4)
                - 0.4239 * Math.pow((gameYear - this.availabilityDate + 1), 3)
                + 7.3219 * Math.pow((gameYear - this.availabilityDate + 1), 2)
                - 49.698 * (gameYear - this.availabilityDate + 1)
                + 142.7889;
        double tBCP = this.initialComponentPrice * (tBPM / 100);
        this.baseCost = tBCP * this.supplierCostMultiplicator;
        return this.baseCost;
    }
}
