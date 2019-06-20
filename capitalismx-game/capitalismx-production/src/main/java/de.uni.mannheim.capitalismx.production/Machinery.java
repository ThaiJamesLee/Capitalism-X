package de.uni.mannheim.capitalismx.production;

import java.time.LocalDate;
import java.time.Period;

public class Machinery {

    public Machinery(ProductionTechnology productionTechnology) {
        this.productionTechnology = productionTechnology;
        this.machineryPrice = 10000;
        this.levelPerPrice = 20000;
        // this.lastInvestmentDate = gameDate
    }

    private ProductionTechnology productionTechnology;
    private double machineryCapacity;
    private double machineryPrice;
    private double levelPerPrice;
    private double purchasePrice;
    private double resellPrice;
    private double machineryDepreciation;
    private LocalDate lastInvestmentDate;
    private int yearsSinceLastInvestment;

    public boolean depreciateMachinery(boolean naturalDisaster) {
        LocalDate gameDate = LocalDate.now();
        boolean yearIncrease = Period.between(this.lastInvestmentDate, gameDate).getYears() - this.yearsSinceLastInvestment > 1;
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
    public double maintainAndRepairMachinery() {
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
        // this.lastInvestmentDate = gameDate
        return 2000;
    }

    /* costs 5,000cc */
    public double upgradeMachinery() {
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
        // this.lastInvestmentDate = gameDate
        return 5000;
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

    public double getMachineryCapacity() {
        return this.machineryCapacity;
    }

    public ProductionTechnology getProductionTechnology() {
        return this.productionTechnology;
    }

    public void setProductionTechnology(ProductionTechnology productionTechnology) {
        this.productionTechnology = productionTechnology;
    }

    public void setMachineryCapacity(double machineryCapacity) {
        this.machineryCapacity = machineryCapacity;
    }
}
