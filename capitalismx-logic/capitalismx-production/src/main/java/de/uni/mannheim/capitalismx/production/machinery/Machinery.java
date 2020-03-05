package de.uni.mannheim.capitalismx.production.machinery;

import de.uni.mannheim.capitalismx.production.department.ProductionTechnology;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

/**
 * A machinery of the production.
 * It is used for the production of products.
 * It has a machineryCapacity that determines how many products this machinery can produce in a day.
 * Its resell price also depreciates with time.
 *
 * @author dzhao
 */
public class Machinery implements Serializable {

    private ProductionTechnology productionTechnology;
    private int machineryCapacity;
    private double machineryPrice;
    private double levelPricePerProductionTechnologyLevel;
    private double purchasePrice;
    private double resellPrice;
    private double machineryDepreciation;
    private LocalDate lastInvestmentDate;
    private int yearsSinceLastInvestment;
    private int usefulLife;
    private LocalDate purchaseDate;
    private boolean decreasedEcoIndex;

    /**
     * Level of the machine which gets higher each time it gets upgraded.
     */
    private int level;

    /**
     * Flag whether machine is still upgradeable.
     */
    private boolean upgradeable;

    /**
     * Maximum level, defines how often a machine can get upgraded.
     */
    private int maxLevel;

    private double maintainAndRepairCosts;
    private double upgradeCosts;

    private static final String DEFAULTS_PROPERTIES_FILE= "production-defaults";

    /**
     * Instantiates a new Machinery.
     * Initiates all the variables.
     *
     * @param gameDate the game date
     */
    public Machinery(LocalDate gameDate) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULTS_PROPERTIES_FILE);
        this.productionTechnology = ProductionTechnology.BRANDNEW;
        this.machineryPrice = Double.valueOf(resourceBundle.getString("production.machinery.price"));
        this.levelPricePerProductionTechnologyLevel = Double.valueOf(resourceBundle.getString("production.machinery.level.price.per.production.technology.level"));;
        this.usefulLife = Integer.valueOf(resourceBundle.getString("production.machinery.useful.life"));
        this.machineryCapacity = Integer.valueOf(resourceBundle.getString("production.machinery.capacity"));
        this.maxLevel = Integer.valueOf(resourceBundle.getString("production.machinery.max.level"));
        this.maintainAndRepairCosts = Double.valueOf(resourceBundle.getString("production.machinery.maintain.and.repair.costs"));
        this.upgradeCosts = Double.valueOf(resourceBundle.getString("production.machinery.upgrade.costs"));
        this.lastInvestmentDate = gameDate;
        this.yearsSinceLastInvestment = 0;
        this.level = 1;
        this.upgradeable = true;
        this.decreasedEcoIndex = false;
    }

    /**
     * Depreciates the production technology of the machinery.
     * It can either be a depreciation caused by a natural disaster or due to the time since the last investment (upgrade
     * or maintain and repair).
     * It depreciates by 2 production technology levels if caused by a natural disaster and 1 level otherwise.
     *
     * @param naturalDisaster the natural disaster
     * @param gameDate        the game date
     * @return whether the machinery depreciated or not.
     */
    public boolean depreciateMachinery(boolean naturalDisaster, LocalDate gameDate) {
        boolean yearIncrease = Period.between(this.lastInvestmentDate, gameDate).getYears() - this.yearsSinceLastInvestment > 0;
        this.yearsSinceLastInvestment = Period.between(this.lastInvestmentDate, gameDate).getYears();
        if (naturalDisaster) {
            switch (this.productionTechnology) {
                case DEPRECIATED:
                    this.productionTechnology = ProductionTechnology.DEPRECIATED;
                    break;
                case OLD:
                    this.productionTechnology = ProductionTechnology.DEPRECIATED;
                    break;
                case GOOD_CONDITIONS:
                    this.productionTechnology = ProductionTechnology.DEPRECIATED;
                    break;
                case PURCHASED_MORE_THAN_FIVE_YEARS_AGO:
                    this.productionTechnology = ProductionTechnology.OLD;
                    break;
                case BRANDNEW:
                    this.productionTechnology = ProductionTechnology.GOOD_CONDITIONS;
                    break;
                default: // Do nothing
            }
            return true;
        } else if (yearIncrease && this.yearsSinceLastInvestment % 5 == 0) {
            switch (this.productionTechnology) {
                case DEPRECIATED:
                    this.productionTechnology = ProductionTechnology.DEPRECIATED;
                    break;
                case OLD:
                    this.productionTechnology = ProductionTechnology.DEPRECIATED;
                    break;
                case GOOD_CONDITIONS:
                    this.productionTechnology = ProductionTechnology.OLD;
                    break;
                case PURCHASED_MORE_THAN_FIVE_YEARS_AGO:
                    this.productionTechnology = ProductionTechnology.GOOD_CONDITIONS;
                    break;
                case BRANDNEW:
                    this.productionTechnology = ProductionTechnology.PURCHASED_MORE_THAN_FIVE_YEARS_AGO;
                    break;
                default: // Do nothing
            }
            return true;
        }
        return false;
    }

    /**
     * Maintains and repairs the machinery.
     * It increases the production technology level by 1.
     *
     * @param gameDate the game date
     * @return the costs of this operation
     */
    public double maintainAndRepairMachinery(LocalDate gameDate) {
        switch (this.productionTechnology) {
            case DEPRECIATED:
                this.productionTechnology = ProductionTechnology.OLD;
                break;
            case OLD:
                this.productionTechnology = ProductionTechnology.GOOD_CONDITIONS;
                break;
            case GOOD_CONDITIONS:
                this.productionTechnology = ProductionTechnology.PURCHASED_MORE_THAN_FIVE_YEARS_AGO;
                break;
            case PURCHASED_MORE_THAN_FIVE_YEARS_AGO:
                this.productionTechnology = ProductionTechnology.BRANDNEW;
                break;
            case BRANDNEW:
                this.productionTechnology = ProductionTechnology.BRANDNEW;
                break;
            default: // Do nothing
                break;
        }
        this.lastInvestmentDate = gameDate;
        return this.maintainAndRepairCosts;
    }

    /**
     * Upgrades the machinery.
     * It increases the production technology level by 2 and increases the machineryCapacity by 20%.
     *
     * @param gameDate the game date
     * @return the costs of the upgrade if it can still be further upgraded, otherwise return 0
     */
    public double upgradeMachinery(LocalDate gameDate) {
        if (this.level < this.maxLevel) {
            this.level += 1;
            this.machineryCapacity *= 1.2;
            switch (this.productionTechnology) {
                case DEPRECIATED:
                    this.productionTechnology = ProductionTechnology.GOOD_CONDITIONS;
                    break;
                case OLD:
                    this.productionTechnology = ProductionTechnology.PURCHASED_MORE_THAN_FIVE_YEARS_AGO;
                    break;
                case GOOD_CONDITIONS:
                    this.productionTechnology = ProductionTechnology.BRANDNEW;
                    break;
                case PURCHASED_MORE_THAN_FIVE_YEARS_AGO:
                    this.productionTechnology = ProductionTechnology.BRANDNEW;
                    break;
                case BRANDNEW:
                    this.productionTechnology = ProductionTechnology.BRANDNEW;
                    break;
                default: // Do nothing
                    break;
            }
            if (this.level == this.maxLevel) {
                this.upgradeable = false;
            }
            this.lastInvestmentDate = gameDate;
            return this.upgradeCosts;
        }
        this.upgradeable = false;
        return 0;
    }

    /**
     * Gets years since last investment.
     *
     * @return the years since last investment
     */
    public int getYearsSinceLastInvestment() {
        return this.yearsSinceLastInvestment;
    }

    /**
     * Calculates the purchase price.
     * Based on the report of the predecessor group.
     *
     * @return purchase price
     */
    public double calculatePurchasePrice() {
        this.purchasePrice = (this.machineryPrice + this.machineryCapacity) * 1.2;
        return this.purchasePrice;
    }

    /**
     * Gets purchase price.
     *
     * @return the purchase price
     */
    public double getPurchasePrice() {
        return this.purchasePrice;
    }

    /**
     * Calculates the resell price.
     * Based on the report of the predecessor group.
     *
     * @return the resell price
     */
    public double calculateResellPrice() {
        this.resellPrice = (this.productionTechnology.getRange() * this.levelPricePerProductionTechnologyLevel) + this.machineryCapacity;
        return this.resellPrice;
    }

    /**
     * Calculates the machinery depreciation.
     *
     * @return the machinery depreciation
     */
    public double calculateMachineryDepreciation() {
        this.machineryDepreciation = this.machineryPrice - (this.productionTechnology.getRange() * this.levelPricePerProductionTechnologyLevel);
        return this.machineryDepreciation;
    }

    /**
     * Gets machinery capacity.
     *
     * @return the machinery capacity
     */
    public int getMachineryCapacity() {
        return this.machineryCapacity;
    }

    /**
     * Gets production technology.
     *
     * @return the production technology
     */
    public ProductionTechnology getProductionTechnology() {
        return this.productionTechnology;
    }

    /**
     * Sets production technology.
     *
     * @param productionTechnology the production technology
     */
    public void setProductionTechnology(ProductionTechnology productionTechnology) {
        this.productionTechnology = productionTechnology;
    }

    /**
     * Sets machinery capacity.
     *
     * @param machineryCapacity the machinery capacity
     */
    public void setMachineryCapacity(int machineryCapacity) {
        this.machineryCapacity = machineryCapacity;
    }

    /**
     * Gets useful life.
     *
     * @return the useful life
     */
    public int getUsefulLife() {
        return this.usefulLife;
    }

    /**
     * Calculate the time the machine was in use.
     *
     * @param gameDate the game date
     * @return the time the machine was in use
     */
    public int calculateTimeUsed(LocalDate gameDate){
        return Period.between(this.purchaseDate, gameDate).getYears();
    }

    /**
     * Sets purchase date.
     *
     * @param gameDate the game date
     */
    public void setPurchaseDate(LocalDate gameDate) {
        this.purchaseDate = gameDate;
        this.lastInvestmentDate = gameDate;
    }

    /**
     * Gets machinery price.
     *
     * @return the machinery price
     */
    public double getMachineryPrice() {
        return this.machineryPrice;
    }

    /**
     * Gets level per price.
     *
     * @return the level per price
     */
    public double getLevelPerPrice() {
        return this.levelPricePerProductionTechnologyLevel;
    }

    /**
     * Gets resell price.
     *
     * @return the resell price
     */
    public double getResellPrice() {
        return this.resellPrice;
    }

    /**
     * Gets machinery depreciation.
     *
     * @return the machinery depreciation
     */
    public double getMachineryDepreciation() {
        return this.machineryDepreciation;
    }

    /**
     * Gets last investment date.
     *
     * @return the last investment date
     */
    public LocalDate getLastInvestmentDate() {
        return this.lastInvestmentDate;
    }

    /**
     * Gets purchase date.
     *
     * @return the purchase date
     */
    public LocalDate getPurchaseDate() {
        return this.purchaseDate;
    }

    /**
     * Gets upgradeable
     *
     * @return upgradeable boolean
     */
    public boolean isUpgradeable() {
        return this.upgradeable;
    }

    /**
     * Gets the boolean flag whether the machine has already decreased the eco index.
     *
     * @return the decreased eco index boolean flag
     */
    public boolean hasDecreasedEcoIndex() {
        return this.decreasedEcoIndex;
    }

    /**
     * Sets the decreased eco index flag.
     *
     * @param decreasedEcoIndex flag
     */
    public void setDecreasedEcoIndex(boolean decreasedEcoIndex) {
        this.decreasedEcoIndex = decreasedEcoIndex;
    }
}
