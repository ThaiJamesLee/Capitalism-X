package de.uni.mannheim.capitalismx.logistic.logistics;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author sdupper
 */
public class InternalFleet {
    private int capacityFleet;
    private double ecoIndexFleet;
    private double qualityIndexFleet;
    private double internalLogisticIndex;
    private double fixCostsDelivery;
    private int variableCostsDelivery;
    private double totalTruckCost;
    private double decreaseCapacityFactor;

    private ArrayList<Truck> trucks;

    public InternalFleet(){
        this.trucks = new ArrayList<Truck>();
        this.variableCostsDelivery = 2;
        this.calculateAll();
        this.decreaseCapacityFactor = 0.0;
    }

    private void calculateAll(){
        this.calculateCapacityFleet();
        this.calculateEcoIndexFleet();
        this.calculateQualityIndexFleet();
        this.calculateInternalLogisticIndex();
        this.calculateFixCostsDelivery();
        this.calculateTotalTruckCost();
    }

    private double calculateCapacityFleet(){
        this.capacityFleet = (int)((1000 * trucks.size()) * (1 - this.decreaseCapacityFactor));
        return this.capacityFleet;
    }

    private double calculateEcoIndexFleet(){
        double ecoIndexSum = 0;
        for(Truck t:trucks){
            ecoIndexSum += t.getEcoIndex();
        }
        this.ecoIndexFleet = ecoIndexSum / trucks.size();
        return this.ecoIndexFleet;
    }

    private double calculateQualityIndexFleet(){
        double qualityIndexSum = 0;
        for(Truck t:trucks){
            qualityIndexSum += t.getQualityIndex();
        }
        this.qualityIndexFleet = qualityIndexSum / trucks.size();
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
        this.fixCostsDelivery = fixCostsDeliverySum / trucks.size();
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
}
