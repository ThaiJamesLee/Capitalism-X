package de.uni.mannheim.capitalismx.logistic.logistics;

import de.uni.mannheim.capitalismx.utils.number.DecimalRound;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 * @author sdupper
 */
public class Truck implements Serializable {
    private String name;
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

    public Truck(String name, double ecoIndexTruck, double qualityIndexTruck, double purchasePriceTruckFactor, double fixCostsDeliveryFactor){
        this.name = name;
        int basePrice = 100000;
        int basePriceDelivery = 200;
        this.ecoIndex = DecimalRound.round(ecoIndexTruck, 2);
        this.qualityIndex = DecimalRound.round(qualityIndexTruck, 2);
        this.purchasePrice = DecimalRound.round(basePrice * purchasePriceTruckFactor, 2);
        this.fixCostsDelivery = DecimalRound.round(basePriceDelivery * fixCostsDeliveryFactor, 2);

        this.capacity = 1000;
        //monthly maintenance costs
        this.fixTruckCost = DecimalRound.round((this.purchasePrice * 0.01) / 12, 2);
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

    public LocalDate getPurchaseDate() {
        return this.purchaseDate;
    }

    public String getName() {
        return this.name;
    }
}
