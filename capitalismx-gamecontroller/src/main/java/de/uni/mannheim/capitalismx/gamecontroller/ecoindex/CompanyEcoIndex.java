package de.uni.mannheim.capitalismx.gamecontroller.ecoindex;

import de.uni.mannheim.capitalismx.logistic.logistics.LogisticsDepartment;
import de.uni.mannheim.capitalismx.production.machinery.Machinery;
import de.uni.mannheim.capitalismx.production.department.ProductionDepartment;

import java.io.Serializable;
import java.util.List;
import java.time.LocalDate;
import java.util.ResourceBundle;

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
    private int ecoFlatTax;

    
    /**
     * see report first team page 30, mechanism nerfed to 10 extra points instead of an extra level (equals 20 points)
     */
    private int extraLevelsFromCampaigns = 0;

    private double ecoCosts;
    private int machineryYearsThreshold;
    private int fleetEcoIndexThreshold;
    private double ecoIndexFactor2;
    private double ecoIndexFactor3;
    private double ecoIndexFactor4;
    private int ecoIndexThreshold;
    private int gameOverThreshold;

    private static final String DEFAULTS_PROPERTIES_FILE = "company-eco-index-defaults";

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
        this.initProperties();
        this.ecoIndex = EcoIndex.GOOD;
        this.ecoIndex.setPoints(100);
        this.calculateAll();
    }

    /**
     * Initializes the company eco index values using the corresponding properties file.
     */
    private void initProperties(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULTS_PROPERTIES_FILE);
        this.ecoFlatTax = Integer.valueOf(resourceBundle.getString("company.eco.index.flat.tax"));
        this.machineryYearsThreshold = Integer.valueOf(resourceBundle.getString("company.eco.index.machinery.years.threshold"));
        this.fleetEcoIndexThreshold = Integer.valueOf(resourceBundle.getString("company.eco.index.fleet.eco.index.threshold"));
        this.ecoIndexFactor2 = Double.valueOf(resourceBundle.getString("company.eco.index.factor.2"));
        this.ecoIndexFactor3 = Double.valueOf(resourceBundle.getString("company.eco.index.factor.3"));
        this.ecoIndexFactor4 = Double.valueOf(resourceBundle.getString("company.eco.index.factor.4"));
        this.ecoIndexThreshold = Integer.valueOf(resourceBundle.getString("company.eco.index.threshold"));
        this.gameOverThreshold = Integer.valueOf(resourceBundle.getString("company.eco.index.game.over.threshold"));
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

    /**
     * Checks if old machinery is in use and decreases the eco index according to p.29.
     * @param gameDate The current date in the game.
     */
    public void checkMachinery(LocalDate gameDate){
        List<Machinery> machines = ProductionDepartment.getInstance().getMachines();
        for(Machinery machinery : machines){
            if((machinery.getYearsSinceLastInvestment() > this.machineryYearsThreshold) && (!machinery.hasDecreasedEcoIndex())){
                this.decreaseEcoIndex(1);
                machinery.setDecreasedEcoIndex(true);
            }
        }
    }

    /**
     * Checks the eco index of the fleet and decreases the eco index similar to p.29.
     */
    public void checkVehicles(){
        if(LogisticsDepartment.getInstance().getInternalFleet().calculateEcoIndexFleet() < this.fleetEcoIndexThreshold){
            this.decreaseEcoIndex(1);
        }
    }

    //TODO
    private void checkRegulatoryLaws(){

    }

    //TODO
    private void checkEnvironmentalDisaster(){
        this.decreaseEcoIndex(2);
    }

    
    /**
     * Calculates the ecoTax based on the ecoFlattax and additionalEcoTax, see p.30
     * @return the ecoTax
     */
    //TODO Determine suitable additionalEcoTax values
    private double calculateEcoTax(){
        double additionalEcoTax = 0;
        if(this.ecoIndex.getIndex() == 4){
            additionalEcoTax = this.ecoFlatTax * this.ecoIndexFactor4;
        }else if(this.ecoIndex.getIndex() == 3){
            additionalEcoTax = this.ecoFlatTax * this.ecoIndexFactor3;
        }else if(this.ecoIndex.getIndex() == 2){
            additionalEcoTax = this.ecoFlatTax * this.ecoIndexFactor2;
        }
        return this.ecoFlatTax + additionalEcoTax;
    }

    /**
     * Calculates the yearly eco costs similarly to p.30.
     * @return Returns the yearly eco costs.
     */
    private double calculateEcoCosts(){
        this.ecoCosts = this.calculateEcoTax() - (ProductionDepartment.getInstance().getProductionTechnology().getRange() + ProductionDepartment.getInstance().calculateAverageEcoIndexOfLaunchedProducts() * 5) * 1100;
        return this.ecoCosts;
    }

    /**
     * Decreases the points by the specified number of points and calculates the resulting ecoIndex, see table 3.4 on p.28
     * @param points The number of points to subtract from the current number.
     */
    protected void decreaseEcoIndex(int points){
        int newPoints = this.ecoIndex.getPoints() - points;
        if(newPoints > 0){
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
        if(this.ecoIndex.getPoints() < this.ecoIndexThreshold){
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
        if(this.ecoIndex.getPoints() <= this.gameOverThreshold){
            return true;
        }else{
            return false;
        }
    }

    public EcoIndex getEcoIndex() {
        return this.ecoIndex;
    }

    public double getEcoCosts() {
        return this.ecoCosts;
    }

    public static void setInstance(CompanyEcoIndex instance) {
        CompanyEcoIndex.instance = instance;
    }
    
    public void addCampaignPoints() {
    	this.increaseEcoIndex(20);
    }
    
    public void setExtraLevelsFromCampaigns(int i) {
    	extraLevelsFromCampaigns = i;
    }
    
    public int getExtraLevelsFromCampaigns() {
    	return extraLevelsFromCampaigns;
    }

    public static CompanyEcoIndex createInstance() {
        return new CompanyEcoIndex();
    }
}
