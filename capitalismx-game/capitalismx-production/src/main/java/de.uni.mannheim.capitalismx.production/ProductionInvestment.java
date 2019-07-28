package de.uni.mannheim.capitalismx.production;

import jdk.vm.ci.meta.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class ProductionInvestment implements Serializable {

    ProductionInvestment(String name) {
        this.name = name;
        this.productionInvestmentLevel = ProductionInvestmentLevel.NO_INVESTMENT;
    }

    private ProductionInvestmentLevel productionInvestmentLevel;
    private LocalDate lastInvestmentDate;
    private String name;

    /* level of investment; 5k, 10k, 15k, 20k respectively for the levels*/
    public ProductionInvestment invest(int level, LocalDate gameDate) {
        /* placeholder */
        int numberProductionEngineersTrained = 5;
        if(this.name.equals("Process Automation") && numberProductionEngineersTrained >= level || !this.name.equals("ProcessAutomation")) {
            switch(level) {
                case 2:
                    this.productionInvestmentLevel = ProductionInvestmentLevel.BAD;
                    break;
                case 3:
                    this.productionInvestmentLevel = ProductionInvestmentLevel.NORMAL;
                    break;
                case 4:
                    this.productionInvestmentLevel = ProductionInvestmentLevel.GOOD;
                    break;
                case 5:
                    this.productionInvestmentLevel = ProductionInvestmentLevel.VERY_GOOD;
                    break;
                default: // Do nothing
                    break;
            }
            this.lastInvestmentDate = gameDate;
        } else {
            // TODO throw not enough production engineers exception
        }
        return this;
    }

    /* not exactly using the function in the final report as the function was specifically designed for level 5 investments.
       rather use - 2 for 1 year, -3 for 2 years, -4 for 5 years
     */
    public ProductionInvestment updateInvestment(LocalDate gameDate) {
        int yearsSinceLastInvestment = Period.between(gameDate, this.lastInvestmentDate).getYears();
        if(yearsSinceLastInvestment == 1) {
            switch(this.productionInvestmentLevel.getLevel()) {
                case 5:
                    this.productionInvestmentLevel = ProductionInvestmentLevel.NORMAL;
                    break;
                case 4:
                    this.productionInvestmentLevel = ProductionInvestmentLevel.BAD;
                    break;
                default:
                    this.productionInvestmentLevel = ProductionInvestmentLevel.NO_INVESTMENT;
                    break;
            }
        } else if(1 < yearsSinceLastInvestment && yearsSinceLastInvestment < 5) {
            if(this.productionInvestmentLevel.getLevel() == 3) {
                this.productionInvestmentLevel = ProductionInvestmentLevel.BAD;
            } else {
                    this.productionInvestmentLevel = ProductionInvestmentLevel.NO_INVESTMENT;
            }
        } else if(yearsSinceLastInvestment > 5) {
            this.productionInvestmentLevel = ProductionInvestmentLevel.NO_INVESTMENT;
        }
        return this;
    }

    public String toString() {
        return this.name + ": " + this.productionInvestmentLevel.toString();
    }

    public LocalDate getLastInvestmentDate() {
        return this.lastInvestmentDate;
    }

    public int getLevel() {
        return this.productionInvestmentLevel.getLevel();
    }

    public double getInvestmentPrice() {
        return this.productionInvestmentLevel.getInvestmentPrice();
    }
}
