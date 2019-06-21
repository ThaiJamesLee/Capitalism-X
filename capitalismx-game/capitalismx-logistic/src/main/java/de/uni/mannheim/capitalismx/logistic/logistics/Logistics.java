package de.uni.mannheim.capitalismx.logistic.logistics;

import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author sdupper
 */
public class Logistics {
    private static Logistics instance;

    private InternalFleet internalFleet;
    private ExternalPartner externalPartner;
    private double shippingFee;
    private double costLogistics;
    private double logisticsIndex;
    private double totalDeliveryCosts;
    private double costsExternalDelivery;
    private double totalLogisticsCosts;
    //delivered products per day
    private int deliveredProducts;

    private Logistics(){
        this.internalFleet = new InternalFleet();
        this.shippingFee = 15;
        this.deliveredProducts = 0;
        this.calculateAll();
    }

    public static synchronized Logistics getInstance() {
        if(Logistics.instance == null) {
            Logistics.instance = new Logistics();
        }
        return Logistics.instance;
    }

    private void calculateAll(){
        this.calculateCostsLogistics();
        this.calculateLogisticsIndex();
        this.calculateTotalDeliveryCosts();
        this.calculateTotalLogisticsCosts();
    }

    private double calculateCostsLogistics(){
        if(externalPartner == null){
            this.costLogistics = internalFleet.getTotalTruckCost();
        }else{
            this.costLogistics = internalFleet.getTotalTruckCost() + externalPartner.getContractualCost();
        }
        return this.costLogistics;
    }

    //TODO what if no external partner hired
    private double calculateLogisticsIndex(){
        if(externalPartner == null){
            this.logisticsIndex = -1.0;
        }
        if(this.deliveredProducts > internalFleet.getCapacityFleet()){
            this.logisticsIndex = (((this.deliveredProducts - internalFleet.getCapacityFleet()) * externalPartner.getExternalLogisticsIndex()) + (internalFleet.getCapacityFleet() * internalFleet.getInternalLogisticIndex()));
        }else if(this.deliveredProducts == internalFleet.getCapacityFleet()){
            this.logisticsIndex = internalFleet.getCapacityFleet() * internalFleet.getInternalLogisticIndex();
        } else{
            this.logisticsIndex = this.deliveredProducts * internalFleet.getInternalLogisticIndex();
        }
        return this.logisticsIndex;
    }

    private double calculateTotalDeliveryCosts(){
        if(this.deliveredProducts <= internalFleet.getCapacityFleet()){
            this.totalDeliveryCosts = (Math.ceil(this.deliveredProducts / 1000.0) * internalFleet.getFixCostsDelivery()) + (this.deliveredProducts * internalFleet.getVariableCostsDelivery());
        } else{
            //TODO
            this.totalDeliveryCosts = (internalFleet.getTrucks().size() * internalFleet.getFixCostsDelivery()) + (internalFleet.getTrucks().size() * 1000 * internalFleet.getVariableCostsDelivery()) + this.calculateCostsExternalDelivery();
        }
        return this.totalDeliveryCosts;
    }

    //TODO possibility to select logistics approach (e.g., only external partner, although internal fleet exists)
    private double calculateCostsExternalDelivery(){
        if(externalPartner != null){
            this.costsExternalDelivery = (this.deliveredProducts - internalFleet.getCapacityFleet()) * externalPartner.getVariableDeliveryCost();
        }else{
            this.costsExternalDelivery = (this.deliveredProducts - internalFleet.getCapacityFleet()) * this.shippingFee;
        }
        return this.costsExternalDelivery;
    }

    private double calculateTotalLogisticsCosts(){
        this.totalLogisticsCosts = (this.calculateCostsLogistics() / 30) + this.calculateTotalDeliveryCosts();
        return this.totalLogisticsCosts;
    }

    /**
     * generate external partners to choose from
     */
    private ArrayList<ExternalPartner> generateExternalPartnerSelection(){
        ArrayList<ExternalPartner> externalPartnerSelection = new ArrayList<ExternalPartner>();

        // generate external partners
        externalPartnerSelection.add(new ExternalPartner(RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(60, 100),
                RandomNumberGenerator.getRandomDouble(1.1, 1.3), RandomNumberGenerator.getRandomDouble(0.7, 0.9)));
        externalPartnerSelection.add(new ExternalPartner(RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(30, 70),
                RandomNumberGenerator.getRandomDouble(1.0, 1.2), RandomNumberGenerator.getRandomDouble(0.8, 1.0)));
        externalPartnerSelection.add(new ExternalPartner(RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(60, 100),
                RandomNumberGenerator.getRandomDouble(1.0, 1.2), RandomNumberGenerator.getRandomDouble(0.8, 1.0)));

        externalPartnerSelection.add(new ExternalPartner(RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(0, 40),
                RandomNumberGenerator.getRandomDouble(0.9, 1.1), RandomNumberGenerator.getRandomDouble(0.9, 1.1)));
        externalPartnerSelection.add(new ExternalPartner(RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(30, 70),
                RandomNumberGenerator.getRandomDouble(0.9, 1.1), RandomNumberGenerator.getRandomDouble(0.9, 1.1)));
        externalPartnerSelection.add(new ExternalPartner(RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(60, 100),
                RandomNumberGenerator.getRandomDouble(0.9, 1.1), RandomNumberGenerator.getRandomDouble(0.9, 1.1)));

        externalPartnerSelection.add(new ExternalPartner(RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(30, 70),
                RandomNumberGenerator.getRandomDouble(0.9, 1.1), RandomNumberGenerator.getRandomDouble(0.9, 1.1)));
        externalPartnerSelection.add(new ExternalPartner(RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(30, 70),
                RandomNumberGenerator.getRandomDouble(0.8, 1.0), RandomNumberGenerator.getRandomDouble(1.0, 1.2)));
        externalPartnerSelection.add(new ExternalPartner(RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(0, 40),
                RandomNumberGenerator.getRandomDouble(0.7, 0.9), RandomNumberGenerator.getRandomDouble(1.1, 1.3)));

        return externalPartnerSelection;
    }

    /**
     * generate six trucks to choose from
     */
    private ArrayList<Truck> generateTruckSelection(){
        ArrayList<Truck> truckSelection = new ArrayList<Truck>();

        // generate six trucks according to table on page 49
        truckSelection.add(new Truck(RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(1.1, 1.3), RandomNumberGenerator.getRandomDouble(0.6, 0.8)));
        truckSelection.add(new Truck(RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(0.9, 1.1), RandomNumberGenerator.getRandomDouble(0.8, 1.1)));
        truckSelection.add(new Truck(RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(1.0, 1.2), RandomNumberGenerator.getRandomDouble(0.7, 0.9)));
        truckSelection.add(new Truck(RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(0.8, 1.1), RandomNumberGenerator.getRandomDouble(0.9, 1.1)));
        truckSelection.add(new Truck(RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(0.7, 0.9), RandomNumberGenerator.getRandomDouble(1.0, 1.2)));
        truckSelection.add(new Truck(RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(0.6, 0.8), RandomNumberGenerator.getRandomDouble(1.1, 1.3)));
        return truckSelection;
    }

    private void setDeliveredProducts(int deliveredProducts){
        this.deliveredProducts = deliveredProducts;
    }

    private void addExternalPartner(ExternalPartner externalPartner){
        this.externalPartner = externalPartner;
        this.calculateAll();
    }

    private void removeExternalPartner(){
        this.externalPartner = null;
        this.calculateAll();
    }

    private void addTruckToFleet(Truck truck, LocalDate gameDate){
        this.internalFleet.addTruckToFleet(truck, gameDate);
        this.calculateAll();
    }

    private void removeTruckFromFleet(Truck truck){
        this.internalFleet.removeTruckFromFleet(truck);
        this.calculateAll();
    }

    public InternalFleet getInternalFleet() {
        return this.internalFleet;
    }

    public ExternalPartner getExternalPartner() {
        return this.externalPartner;
    }

    public double getShippingFee() {
        return this.shippingFee;
    }

    public double getCostLogistics() {
        return this.costLogistics;
    }

    public double getLogisticsIndex() {
        return this.logisticsIndex;
    }

    public double getTotalDeliveryCosts() {
        return this.totalDeliveryCosts;
    }

    public double getCostsExternalDelivery() {
        return this.costsExternalDelivery;
    }

    public double getTotalLogisticsCosts() {
        return this.totalLogisticsCosts;
    }

    public int getDeliveredProducts() {
        return this.deliveredProducts;
    }

    public boolean checkEcoIndexFleetBelowThreshold(){
        return this.internalFleet.checkEcoIndexFleetBelowThreshold();
    }

    public void decreaseCapacityFleetRel(double amount){
        this.internalFleet.decreaseCapacityFleetRel(amount);
    }
}
