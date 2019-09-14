package de.uni.mannheim.capitalismx.logistic.logistics;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 * @author sdupper
 */
public class Truck implements Serializable {
    private int capacity;
    private double ecoIndex;
    private double qualityIndex;
    private double purchasePrice;
    private double fixTruckCost;
    private int depreciationRate;
    private double fixCostsDelivery;
    private double variableCostsDelivery;
    private int usefulLife;
    private LocalDate purchaseDate;

    public Truck(double ecoIndexTruck, double qualityIndexTruck, double purchasePriceTruckFactor, double fixCostsDeliveryFactor){
        int basePrice = 100000;
        this.ecoIndex = ecoIndexTruck;
        this.qualityIndex = qualityIndexTruck;
        this.purchasePrice = basePrice * purchasePriceTruckFactor;
        this.fixCostsDelivery = basePrice * fixCostsDeliveryFactor;

        this.capacity = 1000;
        this.fixTruckCost = (this.purchasePrice * 0.01) / 12;
        //TODO for 9 years
        this.depreciationRate = 1/9;
        this.usefulLife = 9;
        this.variableCostsDelivery = 2;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public double getEcoIndex(){
        return this.ecoIndex;
    }

    public double getQualityIndex(){
        return this.qualityIndex;
    }

    public double getPurchasePrice(){
        return this.purchasePrice;
    }

    public double getFixTruckCost(){
        return this.fixTruckCost;
    }

    public int getDepreciationRate(){
        return this.depreciationRate;
    }

    public double getFixCostsDelivery() {
        return this.fixCostsDelivery;
    }

    public double getVariableCostsDelivery() {
        return this.variableCostsDelivery;
    }

    public int getUsefulLife() {
        return this.usefulLife;
    }

    public int calculateTimeUsed(LocalDate gameDate){
        return Period.between(this.purchaseDate, gameDate).getYears();
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
