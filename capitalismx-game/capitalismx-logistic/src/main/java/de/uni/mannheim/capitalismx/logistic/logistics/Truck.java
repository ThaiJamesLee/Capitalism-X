package de.uni.mannheim.capitalismx.logistic.logistics;

/**
 * @author sdupper
 */
public class Truck {
    private int capacity;
    private double ecoIndexTruck;
    private double qualityIndexTruck;
    private double purchasePriceTruck;
    private double fixTruckCost;
    private int depreciationRate;
    private double fixCostsDelivery;
    private double variableCostsDelivery = 2;

    public Truck(double ecoIndexTruck, double qualityIndexTruck, double purchasePriceTruckFactor, double fixCostsDeliveryFactor){
        int basePrice = 100000;
        this.ecoIndexTruck = ecoIndexTruck;
        this.qualityIndexTruck = qualityIndexTruck;
        this.purchasePriceTruck = basePrice * purchasePriceTruckFactor;
        this.fixCostsDelivery = basePrice * fixCostsDeliveryFactor;

        this.capacity = 1000;
        this.fixTruckCost = (purchasePriceTruck * 0.01) / 12;
        //TODO for 9 years
        this.depreciationRate = 1/9;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public double getEcoIndexTruck(){
        return this.ecoIndexTruck;
    }

    public double getQualityIndexTruck(){
        return this.qualityIndexTruck;
    }

    public double getPurchasePriceTruck(){
        return this.purchasePriceTruck;
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
}
