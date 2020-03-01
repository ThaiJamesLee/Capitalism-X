package de.uni.mannheim.capitalismx.production.machinery;

import de.uni.mannheim.capitalismx.production.department.ProductionTechnology;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

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
    private static final int MAX_LEVEL = 5;

    private static final double MAINTAIN_AND_REPAIR_COSTS = 2000;
    private static final double UPGRADE_COSTS = 5000;

    /**
     * Instantiates a new Machinery.
     * Initiates all the variables.
     *
     * @param gameDate the game date
     */
    public Machinery(LocalDate gameDate) {
        this.productionTechnology = ProductionTechnology.BRANDNEW;
        this.machineryPrice = 100000;
        this.levelPricePerProductionTechnologyLevel = 20000;
        this.usefulLife = 20;
        this.machineryCapacity = 25;
        this.lastInvestmentDate = gameDate;
        this.yearsSinceLastInvestment = 0;
        this.level = 1;
        this.upgradeable = true;
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
        return MAINTAIN_AND_REPAIR_COSTS;
    }

    /**
     * Upgrades the machinery.
     * It increases the production technology level by 2 and increases the machineryCapacity by 20%.
     *
     * @param gameDate the game date
     * @return the costs of the upgrade if it can still be further upgraded, otherwise return 0
     */
    /* costs 5,000cc */
    public double upgradeMachinery(LocalDate gameDate) {
        if (this.level < MAX_LEVEL) {
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
            if (this.level == MAX_LEVEL) {
                this.upgradeable = false;
            }
            this.lastInvestmentDate = gameDate;
            return UPGRADE_COSTS;
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
}
