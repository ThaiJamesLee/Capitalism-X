package de.uni.mannheim.capitalismx.logistic.logistics;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author sdupper
 */
public class InternalFleet implements Serializable {
    private static InternalFleet instance;

    private int capacityFleet;
    private double ecoIndexFleet;
    private double qualityIndexFleet;
    private double internalLogisticIndex;
    private double fixCostsDelivery;
    private int variableCostsDelivery;
    private double totalTruckCost;
    private double decreaseCapacityFactor;

    private ArrayList<Truck> trucks;

    protected InternalFleet(){
        this.trucks = new ArrayList<Truck>();
        this.variableCostsDelivery = 2;
        this.calculateAll();
        this.decreaseCapacityFactor = 0.0;
    }

    public static synchronized InternalFleet getInstance() {
        if(InternalFleet.instance == null) {
            InternalFleet.instance = new InternalFleet();
        }
        return InternalFleet.instance;
    }

    public void calculateAll(){
        this.calculateCapacityFleet();
        this.calculateEcoIndexFleet();
        this.calculateQualityIndexFleet();
        this.calculateInternalLogisticIndex();
        this.calculateFixCostsDelivery();
        this.calculateTotalTruckCost();
    }

    protected int calculateCapacityFleet(){
        this.capacityFleet = (int)((1000 * trucks.size()) * (1 - this.decreaseCapacityFactor));
        return this.capacityFleet;
    }

    protected double calculateEcoIndexFleet(){
        double ecoIndexSum = 0;
        for(Truck t:trucks){
            ecoIndexSum += t.getEcoIndex();
        }
        if(trucks.size() > 0){
            this.ecoIndexFleet = ecoIndexSum / trucks.size();
        }
        return this.ecoIndexFleet;
    }

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

    private double calculateInternalLogisticIndex(){
        this.internalLogisticIndex = (this.calculateEcoIndexFleet() * 0.2) + (this.calculateQualityIndexFleet() * 0.8);
        return this.internalLogisticIndex;
    }

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

    private double calculateTotalTruckCost(){
        double fixTruckCostSum = 0;
        for(Truck t:trucks){
            fixTruckCostSum += t.getFixTruckCost();
        }
        this.totalTruckCost = fixTruckCostSum;
        return this.totalTruckCost;
    }

    protected void addTruckToFleet(Truck truck, LocalDate gameDate){
        truck.setPurchaseDate(gameDate);
        this.trucks.add(truck);
        this.calculateAll();
    }

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

    public int getVariableCostsDelivery() {
        return this.variableCostsDelivery;
    }

    public double getTotalTruckCost() {
        return this.totalTruckCost;
    }

    boolean checkEcoIndexFleetBelowThreshold(){
        if(this.calculateEcoIndexFleet() < 30){
            return true;
        }else{
            return false;
        }
    }

    //TODO only for two months
    void decreaseCapacityFleetRel(double amount){
        this.decreaseCapacityFactor += amount;
    }

    public static void setInstance(InternalFleet instance) {
        InternalFleet.instance = instance;
    }
}
