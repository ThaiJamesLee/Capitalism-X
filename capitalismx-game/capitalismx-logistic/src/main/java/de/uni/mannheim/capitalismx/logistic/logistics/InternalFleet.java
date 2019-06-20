package de.uni.mannheim.capitalismx.logistic.logistics;

import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

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

    private ArrayList<Truck> trucks;

    public InternalFleet(){
        this.trucks = new ArrayList<Truck>();
        this.variableCostsDelivery = 2;
        this.calculateAll();
    }

    private void calculateAll(){
        this.calculateCapacityFleet();
        this.calculateEcoIndexFleet();
        this.calculateQualityIndexFleet();
        this.calculateInternalLogisticIndex();
        this.calculateFixCostsDelivery();
        this.calculateTotalTruckCost();
    }

    private void calculateCapacityFleet(){
        this.capacityFleet = 1000 * trucks.size();
    }

    private void calculateEcoIndexFleet(){
        double ecoIndexSum = 0;
        for(Truck t:trucks){
            ecoIndexSum += t.getEcoIndexTruck();
        }
        this.ecoIndexFleet = ecoIndexSum / trucks.size();
    }

    private void calculateQualityIndexFleet(){
        double qualityIndexSum = 0;
        for(Truck t:trucks){
            qualityIndexSum += t.getQualityIndexTruck();
        }
        this.qualityIndexFleet = qualityIndexSum / trucks.size();
    }

    private void calculateInternalLogisticIndex(){
        this.internalLogisticIndex = (ecoIndexFleet * 0.2) + (qualityIndexFleet * 0.8);
    }

    private void calculateFixCostsDelivery(){
        double fixCostsDeliverySum = 0;
        for(Truck t:trucks){
            fixCostsDeliverySum += t.getFixCostsDelivery();
        }
        this.fixCostsDelivery = fixCostsDeliverySum / trucks.size();
    }

    private void calculateTotalTruckCost(){
        double fixTruckCostSum = 0;
        for(Truck t:trucks){
            fixTruckCostSum += t.getFixTruckCost();
        }
        this.totalTruckCost = fixTruckCostSum;
    }

    protected void addTruckToFleet(Truck truck){
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
}
