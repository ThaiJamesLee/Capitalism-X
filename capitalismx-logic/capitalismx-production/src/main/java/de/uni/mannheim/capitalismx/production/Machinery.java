package de.uni.mannheim.capitalismx.production;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Machinery implements Serializable {
    private ProductionTechnology productionTechnology;
    /* TODO machinerycapacity not defined*/
    private int machineryCapacity;
    private double machineryPrice;
    private double levelPerPrice;
    private double purchasePrice;
    private double resellPrice;
    private double machineryDepreciation;
    private LocalDate lastInvestmentDate;
    private int yearsSinceLastInvestment;
    private int usefulLife;
    private LocalDate purchaseDate;
    private int level;
    private boolean upgradeable;
    private static final int MAX_LEVEL = 5;

    public Machinery(LocalDate gameDate) {
        this.productionTechnology = ProductionTechnology.BRANDNEW;
        this.machineryPrice = 100000;
        this.levelPerPrice = 20000;
        // TODO fragen ob usefullife nach jedem jahr um 1 runtergezaehlt werden soll
        this.usefulLife = 20;
        this.machineryCapacity = 50;
        this.lastInvestmentDate = gameDate;
        this.yearsSinceLastInvestment = 0;
        this.level = 1;
        this.upgradeable = true;
    }

    public boolean depreciateMachinery(boolean naturalDisaster, LocalDate gameDate) {
        boolean yearIncrease = Period.between(this.lastInvestmentDate, gameDate).getYears() - this.yearsSinceLastInvestment > 0;
        this.yearsSinceLastInvestment = Period.between(this.lastInvestmentDate, gameDate).getYears();
        if(naturalDisaster) {
            switch(this.productionTechnology) {
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
        } else if(yearIncrease && this.yearsSinceLastInvestment % 5 == 0) {
            switch(this.productionTechnology) {
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

    /* costs 2,000cc */
    public double maintainAndRepairMachinery(LocalDate gameDate) {
        switch(this.productionTechnology) {
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
        return 2000;
    }

    /* costs 5,000cc */
    public double upgradeMachinery(LocalDate gameDate) {
        if(this.level < MAX_LEVEL) {
            this.level += 1;
            this.machineryCapacity *= 1.2;
            switch(this.productionTechnology) {
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
            if(this.level == MAX_LEVEL) {
                this.upgradeable = false;
            }
            this.lastInvestmentDate = gameDate;
            return 5000;
        }
        this.upgradeable = false;
        return 0;
    }

    public int getYearsSinceLastInvestment() {
        return this.yearsSinceLastInvestment;
    }

    public double calculatePurchasePrice() {
        this.purchasePrice = (this.machineryPrice + this.machineryCapacity) * 1.2;
        return this.purchasePrice;
    }

    public double getPurchasePrice() {
        return this.purchasePrice;
    }

    public double calculateResellPrice() {
        this.resellPrice = (this.productionTechnology.getRange() * this.levelPerPrice) + this.machineryCapacity;
        return this.resellPrice;
    }

    public double calculateMachineryDepreciation() {
        this.machineryDepreciation = this.machineryPrice - (this.productionTechnology.getRange() * this.levelPerPrice);
        return this.machineryDepreciation;
    }

    public int getMachineryCapacity() {
        return this.machineryCapacity;
    }

    public ProductionTechnology getProductionTechnology() {
        return this.productionTechnology;
    }

    public void setProductionTechnology(ProductionTechnology productionTechnology) {
        this.productionTechnology = productionTechnology;
    }

    public void setMachineryCapacity(int machineryCapacity) {
        this.machineryCapacity = machineryCapacity;
    }

    public int getUsefulLife() {
        return this.usefulLife;
    }

    public int calculateTimeUsed(LocalDate gameDate){
        return Period.between(this.purchaseDate, gameDate).getYears();
    }

    public void setPurchaseDate(LocalDate gameDate) {
        this.purchaseDate = gameDate;
        this.lastInvestmentDate = gameDate;
    }

    public double getMachineryPrice() {
        return this.machineryPrice;
    }

    public double getLevelPerPrice() {
        return this.levelPerPrice;
    }

    public double getResellPrice() {
        return this.resellPrice;
    }

    public double getMachineryDepreciation() {
        return this.machineryDepreciation;
    }

    public LocalDate getLastInvestmentDate() {
        return this.lastInvestmentDate;
    }

    public LocalDate getPurchaseDate() {
        return this.purchaseDate;
    }
}
