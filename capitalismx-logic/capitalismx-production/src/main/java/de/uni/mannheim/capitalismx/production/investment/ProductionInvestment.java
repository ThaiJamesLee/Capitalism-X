package de.uni.mannheim.capitalismx.production.investment;

import de.uni.mannheim.capitalismx.production.exceptions.NotEnoughTrainedEngineersException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 * The type Production investment.
 * It is a type of investment that can be invested into in the production department.
 * It has a production investment level that depreciates over time if no further investments on it are done.
 *
 * @author dzhao
 */
public class ProductionInvestment implements Serializable {

    private ProductionInvestmentLevel productionInvestmentLevel;
    private LocalDate lastInvestmentDate;
    private String name;

    /**
     * Instantiates a new Production investment.
     * It sets the last investment date to the date of the game start and starts the production investment level at
     * no investment.
     *
     * @param name the name
     */
    public ProductionInvestment(String name) {
        this.name = name;
        this.lastInvestmentDate = LocalDate.of(1990, 1, 1);
        this.productionInvestmentLevel = ProductionInvestmentLevel.NO_INVESTMENT;
    }

    /**
     * Invest into production investment.
     * Check whether enough engineers are trained when investing into process automation.
     * Increases the production investment level by 1 and sets new last investment date.
     *
     * @param level    the level
     * @param gameDate the game date
     * @return the production investment
     */
    public ProductionInvestment invest(int level, LocalDate gameDate, int numberOfEngineersTrained) throws NotEnoughTrainedEngineersException {
        /* level of investment; 5k, 10k, 15k, 20k respectively for the levels*/
        if (this.name.equals("Process Automation") && numberOfEngineersTrained >= level || !this.name.equals("Process Automation")) {
            switch (level) {
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
        } else if (this.name.equals("Process Automation") && numberOfEngineersTrained < level) {
            throw new NotEnoughTrainedEngineersException("You don't have enough trained engineers to invest in Process Automation. " + level
                    + " trained engineers are needed, but only " + numberOfEngineersTrained + " trained engineers are hired.", level, numberOfEngineersTrained);
        }
        return this;
    }

    /**
     * Update investment production investment.
     * Depreciates the production investment level after certain numbers of years since the last investment level
     *
     * @param gameDate the game date
     * @return the production investment
     */
    public ProductionInvestment updateInvestment(LocalDate gameDate) {
        /* not exactly using the function in the final report as the function was specifically designed for level 5 investments.
           rather use -2 for 1 year, -3 for 2 years, -4 for 5 years
        */
        int yearsSinceLastInvestment = Period.between(this.lastInvestmentDate, gameDate).getYears();
        if (yearsSinceLastInvestment == 1) {
            switch (this.productionInvestmentLevel.getLevel()) {
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
        } else if (1 < yearsSinceLastInvestment && yearsSinceLastInvestment < 5) {
            if (this.productionInvestmentLevel.getLevel() == 3) {
                this.productionInvestmentLevel = ProductionInvestmentLevel.BAD;
            } else {
                    this.productionInvestmentLevel = ProductionInvestmentLevel.NO_INVESTMENT;
            }
        } else if (yearsSinceLastInvestment >= 5) {
            this.productionInvestmentLevel = ProductionInvestmentLevel.NO_INVESTMENT;
        }
        return this;
    }

    /**
     * Decreases level and production investment level.
     *
     * @param level the level
     */
    public void decreaseLevel(int level) {
        int newLevel = this.productionInvestmentLevel.getLevel() - level;
        if (newLevel <= 1) {
            this.productionInvestmentLevel = ProductionInvestmentLevel.NO_INVESTMENT;
        } else {
            switch (newLevel) {
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
                default:
                    break;
            }
        }
    }

    /**
     * Increases level and production ivnestment level.
     *
     * @param level the level
     */
    public void increaseLevel(int level) {
        int newLevel = this.productionInvestmentLevel.getLevel() + level;
        if (newLevel >= 5) {
            this.productionInvestmentLevel = ProductionInvestmentLevel.VERY_GOOD;
        } else {
            switch (newLevel) {
                case 1:
                    this.productionInvestmentLevel = ProductionInvestmentLevel.NO_INVESTMENT;
                    break;
                case 2:
                    this.productionInvestmentLevel = ProductionInvestmentLevel.BAD;
                    break;
                case 3:
                    this.productionInvestmentLevel = ProductionInvestmentLevel.NORMAL;
                    break;
                case 4:
                    this.productionInvestmentLevel = ProductionInvestmentLevel.GOOD;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return this.name + ": " + this.productionInvestmentLevel.toString();
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
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return this.productionInvestmentLevel.getLevel();
    }

    /**
     * Gets investment price.
     *
     * @return the investment price
     */
    public double getInvestmentPrice() {
        return this.productionInvestmentLevel.getInvestmentPrice();
    }
}
