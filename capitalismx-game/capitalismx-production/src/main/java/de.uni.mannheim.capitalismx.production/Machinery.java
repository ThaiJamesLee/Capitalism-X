package de.uni.mannheim.capitalismx.production;

public class Machinery {

    public Machinery(ProductionTechnology productionTechnology) {
        this.productionTechnology = productionTechnology;
    }

    private ProductionTechnology productionTechnology;
    private double machineCapacity;
    private double machinePrice = 100000;
    private double levelPerPrice = 20000;
    private double purchasePrice;
    private double resellPrice;
    private double machineryDepreciation;

    public void depreciateMachinery(int levelOfDepreciation) {
        switch(this.productionTechnology) {
            case DEPRECIATED:
                this.productionTechnology = ProductionTechnology.DEPRECIATED;
                break;
            case OLD:
                this.productionTechnology = ProductionTechnology.DEPRECIATED;
                break;
            case GOOD_CONDITIONS:
                if(levelOfDepreciation == 2) {
                    this.productionTechnology = ProductionTechnology.DEPRECIATED;
                } else if (levelOfDepreciation == 1) {
                    this.productionTechnology = ProductionTechnology.OLD;
                }
                break;
            case PURCHASED_MORE_THAN_FIVE_YEARS_AGO:
                if(levelOfDepreciation == 2) {
                    this.productionTechnology = ProductionTechnology.OLD;
                } else if (levelOfDepreciation == 1) {
                    this.productionTechnology = ProductionTechnology.GOOD_CONDITIONS;
                }
                break;
            case BRANDNEW:
                if(levelOfDepreciation == 2) {
                    this.productionTechnology = ProductionTechnology.GOOD_CONDITIONS;
                } else if (levelOfDepreciation == 1) {
                    this.productionTechnology = ProductionTechnology.PURCHASED_MORE_THAN_FIVE_YEARS_AGO;
                }
                break;
            default: // Do nothing
                break;
        }
    }

    /* costs 2,000cc */
    public void maintainMachine() {
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
    }

    /* costs 2,000cc */
    /* not too sure whether we should just merge this method with maintainMachine, it is basically the same method */
    public void repairMachine() {
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
    }

    /* costs 5,000cc */
    public void upgradeMachine() {
        this.machineCapacity *= 1.2;
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
    }

    public void calculatePurchasePrice() {
        this.purchasePrice = (this.machinePrice + this.machineCapacity) * 1.2;
    }

    public void calculateResellPrice() {
        this.resellPrice = (this.productionTechnology.getRange() * this.levelPerPrice) + this.machineCapacity;
    }

    public void calculateMachineryDepreciation() {
        this.machineryDepreciation = this.machinePrice - (this.productionTechnology.getRange() * this.levelPerPrice);
    }
}
