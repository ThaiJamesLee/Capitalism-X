package de.uni.mannheim.capitalismx.logistic.logistics;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class represents the internal logistics fleet.
 * The company can buy trucks for its internal fleet to deliver products.
 * Based on the report p.48-51
 *
 * @author sdupper
 */
public class InternalFleet implements Serializable {

    /**
     * The singleton pointer.
     */
    private static InternalFleet instance;

    /**
     * Represents the environmental friendliness of the fleet.
     */
    private double ecoIndexFleet;

    /**
     * Represents the delivery quality of the fleet.
     */
    private double qualityIndexFleet;

    /**
     * A list of trucks in the internal fleet.
     */
    private ArrayList<Truck> trucks;

    /**
     * The factor by which the internal fleet capacity is decreased, e.g., in case of penalties.
     */
    private double decreaseCapacityFactor;

    private int capacityFleet;
    private double internalLogisticIndex;
    private double fixCostsDelivery;
    private double variableCostsDelivery;
    private double totalTruckCost;
    private double truckCapacity;

    private static final String DEFAULTS_PROPERTIES_FILE = "logistics-defaults";

    /**
     * Constructor
     * Initializes the variables and calculates all relevant values of the internal fleet.
     */
    protected InternalFleet(){
        this.trucks = new ArrayList<>();

        this.initProperties();

        this.calculateAll();
    }

    /**
     * Initializes the internal fleet values using the corresponding properties file.
     */
    private void initProperties(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULTS_PROPERTIES_FILE);
        this.variableCostsDelivery = Double.valueOf(resourceBundle.getString("logistics.internal.fleet.variable.costs.delivery"));
        this.decreaseCapacityFactor = Double.valueOf(resourceBundle.getString("logistics.internal.fleet.decrease.capacity.factor"));
        this.truckCapacity = Double.valueOf(resourceBundle.getString("logistics.truck.capacity"));
    }

    /**
     *
     * @return Returns the singleton instance
     */
    public static synchronized InternalFleet getInstance() {
        if(InternalFleet.instance == null) {
            InternalFleet.instance = new InternalFleet();
        }
        return InternalFleet.instance;
    }

    /**
     * Calculates different values of the internal fleet, e.g., the eco index.
     */
    public void calculateAll(){
        this.calculateCapacityFleet();
        this.calculateEcoIndexFleet();
        this.calculateQualityIndexFleet();
        this.calculateInternalLogisticIndex();
        this.calculateFixCostsDelivery();
        this.calculateTotalTruckCost();
    }

    /**
     * Calculates the capacity of the internal fleet based on the number of trucks according to p.49. The capacity might
     * be decreased according to the decreaseCapacityFactor.
     * @return Returns the capacity of the internal fleet.
     */
    protected int calculateCapacityFleet(){
        this.capacityFleet = (int)((this.truckCapacity * trucks.size()) * (1 - this.decreaseCapacityFactor));
        return this.capacityFleet;
    }

    /**
     * Calculates the eco index of the internal fleet by calculating the average eco index of all trucks in the fleet
     * according to p.50. If there are no trucks in the internal fleet, the eco index is perfect.
     * @return Returns the eco index of the internal fleet.
     */
    public double calculateEcoIndexFleet(){
        double ecoIndexSum = 0;
        for(Truck t:trucks){
            ecoIndexSum += t.getEcoIndex();
        }
        if(trucks.size() > 0){
            this.ecoIndexFleet = ecoIndexSum / trucks.size();
        }else{
            this.ecoIndexFleet = 100; // Perfect eco index if no trucks owned
        }
        return this.ecoIndexFleet;
    }

    /**
     * Calculates the quality index of the internal fleet by calculating the average quality index of all trucks in the
     * fleet according to p.50.
     * @return Returns the quality index of the internal fleet.
     */
    protected double calculateQualityIndexFleet(){
        double qualityIndexSum = 0;
        for(Truck t:trucks){
            qualityIndexSum += t.getQualityIndex();
        }
        if(trucks.size() > 0) {
            this.qualityIndexFleet = qualityIndexSum / trucks.size();
        }
        return this.qualityIndexFleet;
    }

    /**
     * Calculates the internal logistics index based on the eco index and quality index of the fleet according to p.50.
     * @return Returns the internal logistics index.
     */
    private double calculateInternalLogisticIndex(){
        this.internalLogisticIndex = (this.calculateEcoIndexFleet() * 0.2) + (this.calculateQualityIndexFleet() * 0.8);
        return this.internalLogisticIndex;
    }

    /**
     * Calculates the fix costs per delivery by calculating the average fixed delivery costs of the trucks in the
     * internal fleet.
     * @return Returns the fix costs of a delivery.
     */
    private double calculateFixCostsDelivery(){
        double fixCostsDeliverySum = 0;
        for(Truck t:trucks){
            fixCostsDeliverySum += t.getFixCostsDelivery();
        }
        if(trucks.size() > 0) {
            this.fixCostsDelivery = fixCostsDeliverySum / trucks.size();
        }
        return this.fixCostsDelivery;
    }

    /**
     * Calculates the total (monthly) truck costs of the internal logistics fleet based on the fixed truck costs of all
     * trucks in the internal fleet according to p.51.
     * @return Returns the total (monthly) truck costs.
     */
    private double calculateTotalTruckCost(){
        double fixTruckCostSum = 0;
        for(Truck t:trucks){
            fixTruckCostSum += t.getFixTruckCost();
        }
        this.totalTruckCost = fixTruckCostSum;
        return this.totalTruckCost;
    }

    /**
     * Adds a truck to the internal fleet, sets the purchase date of the truck, and updates the values of the internal
     * fleet accordingly.
     * @param truck The truck to be added to the internal fleet.
     * @param gameDate The current date in the game.
     */
    protected void addTruckToFleet(Truck truck, LocalDate gameDate){
        truck.setPurchaseDate(gameDate);
        this.trucks.add(truck);
        this.calculateAll();
    }

    /**
     * Removes a truck from the internal fleet and updates the values of the internal fleet accordingly.
     * @param truck The truck to be removed from the internal fleet.
     */
    protected void removeTruckFromFleet(Truck truck){
        this.trucks.remove(truck);
        this.calculateAll();
    }

    public int getCapacityFleet() {
        return this.capacityFleet;
    }

    public double getEcoIndexFleet() {
        return this.ecoIndexFleet;
    }

    public double getQualityIndexFleet() {
        return this.qualityIndexFleet;
    }

    public double getInternalLogisticIndex() {
        return this.internalLogisticIndex;
    }

    public ArrayList<Truck> getTrucks() {
        return this.trucks;
    }

    public double getFixCostsDelivery() {
        return this.fixCostsDelivery;
    }

    public double getVariableCostsDelivery() {
        return this.variableCostsDelivery;
    }

    public double getTotalTruckCost() {
        return this.totalTruckCost;
    }

    /**
     * Checks if the eco index of the internal fleet is below 30. This is important for external events (Table A.1).
     * @return Returns true if the eco index is below 30. Returns false otherwise.
     */
    boolean checkEcoIndexFleetBelowThreshold(){
        if(this.calculateEcoIndexFleet() < 30){
            return true;
        }else{
            return false;
        }
    }

    //TODO probably not necessary to check if <= 1.0

    /**
     * Increases the factor by which the capacity of the internal fleet is decreased. The factor can not exceed 1.0.
     * @param amount The amount by which the factor should be increased.
     */
    void decreaseCapacityFleetRel(double amount){
        if((this.decreaseCapacityFactor + amount) <= 1.0){
            this.decreaseCapacityFactor += amount;
        }else{
            this.decreaseCapacityFactor = 1.0;
        }
    }

    /**
     * Decreases the factor by which the capacity of the internal fleet is decreased.
     * @param amount The amount by which the factor should be decreased.
     */
    void increaseCapacityFleetRel(double amount){
        this.decreaseCapacityFactor -= amount;
    }

    public static void setInstance(InternalFleet instance) {
        InternalFleet.instance = instance;
    }

    public static InternalFleet createInstance() {
        return new InternalFleet();
    }
}
