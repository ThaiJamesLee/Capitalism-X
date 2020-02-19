package de.uni.mannheim.capitalismx.gamecontroller.ecoindex;

import de.uni.mannheim.capitalismx.production.Machinery;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;

import java.io.Serializable;
import java.util.List;
import java.time.LocalDate;

/**
 * This class represents the company ecoIndex.
 * It implements the ecoIndex, factors that change the eco index, and their impacts.
 * Based on the report p.28-30
 *
 * @author sdupper
 */
public class CompanyEcoIndex implements Serializable {

    /**
     * The singleton pointer.
     */
    private static CompanyEcoIndex instance;

    /**
     * The ecoIndex of the company, implemented as an enum.
     */
    private EcoIndex ecoIndex;

    /**
     * The ecoFlatTax is a fixed tax required to calculate the ecoTax.
     */
    private final int ECO_FLAT_TAX = 10000;

    /**
     * All possible values of the company ecoIndex, see p.28
     */
    public enum EcoIndex implements Serializable{
        GOOD(5, 80, 100),
        MODERATE(4, 60, 79),
        UNHEALTHY(3, 40, 59),
        VERY_UNHEALTHY(2, 20, 39),
        HAZARDOUS(1, 0, 19);

        private int index;
        private int min;
        private int max;
        private int points;

        EcoIndex(int index, int min, int max){
            this.index = index;
            this.min = min;
            this.max = max;
        }

        public void setPoints(int points){
            this.points = points;
        }

        public int getIndex() {
            return this.index;
        }

        public int getMax() {
            return this.max;
        }

        public int getMin() {
            return this.min;
        }

        public int getPoints() {
            return this.points;
        }
    }

    /**
     * Constructor
     * Initializes ecoIndex and calculates ecoTax and ecoCosts.
     */
    protected CompanyEcoIndex(){
        //TODO determine initial values
        this.ecoIndex = EcoIndex.GOOD;
        this.ecoIndex.setPoints(100);
        this.calculateAll();
    }

    /**
     *
     * @return Returns the singleton instance
     */
    public static synchronized CompanyEcoIndex getInstance() {
        if(CompanyEcoIndex.instance == null) {
            CompanyEcoIndex.instance = new CompanyEcoIndex();
        }
        return CompanyEcoIndex.instance;
    }

    /**
     * Calculates the ecoTax and ecoCosts
     */
    public void calculateAll(){
        this.calculateEcoTax();
        this.calculateEcoCosts();
    }

    private void checkMachinery(LocalDate gameDate){
        //TODO

        List<Machinery> machines = ProductionDepartment.getInstance().getMachines();
        for(Machinery machinery : machines){
            machinery.depreciateMachinery(false, gameDate);
            if(machinery.getYearsSinceLastInvestment() > 5){
                this.decreaseEcoIndex(1);
            }
        }
    }

    private void checkVehicles(){
        //TODO
        /**if(Logistics.getInstance().getInternalFleet().calculateExoIndexFleet() < 3){
            this.decreaseEcoIndex(1);
        }**/
    }

    //TODO
    private void checkRegulatoryLaws(){

    }

    //TODO
    private void checkEnvironmentalDisaster(){
        this.decreaseEcoIndex(2);
    }

    //TODO Marketing to increase ecoIndex

    /**
     * Calculates the ecoTax based on the ecoFlattax and additionalEcoTax, see p.30
     * @return the ecoTax
     */
    //TODO Determine suitable additionalEcoTax values
    private double calculateEcoTax(){
        double additionalEcoTax = 0;
        if(this.ecoIndex.getIndex() ==4){
            additionalEcoTax = this.ECO_FLAT_TAX * 0.01;
        }else if(this.ecoIndex.getIndex() == 3){
            additionalEcoTax = this.ECO_FLAT_TAX * 0.03;
        }else if(this.ecoIndex.getIndex() == 2){
            additionalEcoTax = this.ECO_FLAT_TAX * 0.06;
        }
        return this.ECO_FLAT_TAX + additionalEcoTax;
    }

    //TODO
    private double calculateEcoCosts(){
        //return this.calculateEcoTax() - (Production.getInstance().getProductionTechnology().getRange() + component eL) * 1000;
        return 0.0;
    }

    /**
     * Decreases the points by the specified number of points and calculates the resulting ecoIndex, see table 3.4 on p.28
     * @param points The number of points to subtract from the current number.
     */
    protected void decreaseEcoIndex(int points){
        int newPoints = this.ecoIndex.getPoints() - points;
        if(newPoints < this.ecoIndex.getMin()){
            if(this.ecoIndex.getIndex() > 2){
                this.ecoIndex = EcoIndex.values()[this.ecoIndex.ordinal() + 1];
            }else{
                //TODO Game Over event if below 10 points
                this.ecoIndex = EcoIndex.values()[this.ecoIndex.ordinal() + 1];
            }
        }
        this.ecoIndex.setPoints(newPoints);
    }

    /**
     * Increases the points by the specified number of points and calculates the resulting ecoIndex, see table 3.4 on p.28
     * @param points The number of points to add to the current number.
     */
    protected void increaseEcoIndex(int points){
        int newPoints = this.ecoIndex.getPoints() + points;
        if(newPoints > this.ecoIndex.getMax()){
            if(this.ecoIndex.getIndex() < 5){
                this.ecoIndex = EcoIndex.values()[this.ecoIndex.ordinal() - 1];
                this.ecoIndex.setPoints(newPoints);
            }else{
                this.ecoIndex.setPoints(this.ecoIndex.getMax());
            }
        }else{
            this.ecoIndex.setPoints(newPoints);
        }
    }

    /**
     * Checks if the ecoIndex is below 40 points. This is important for the occurrence of external events, see p.98
     * @return Returns true if the ecoIndex is below 40 points. Returns false otherwise.
     */
    //TODO unclear if points or index should be used according to documentation
    public boolean checkEcoIndexBelowThreshold(){
        if(this.ecoIndex.getPoints() < 40){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Checks if the ecoIndex is below or equal to 10 points. This is important to determine if the game is over, see p.98
     * @return Returns true if the ecoIndex is below or equal to 10 points. Returns false otherwise.
     */
    public boolean checkGameOver(){
        if(this.ecoIndex.getPoints() <= 10){
            return true;
        }else{
            return false;
        }
    }

    public EcoIndex getEcoIndex() {
        return this.ecoIndex;
    }

    public static void setInstance(CompanyEcoIndex instance) {
        CompanyEcoIndex.instance = instance;
    }

    public static CompanyEcoIndex createInstance() {
        return new CompanyEcoIndex();
    }
}
