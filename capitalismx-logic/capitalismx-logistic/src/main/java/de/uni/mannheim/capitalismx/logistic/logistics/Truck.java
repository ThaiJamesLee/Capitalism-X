package de.uni.mannheim.capitalismx.logistic.logistics;

import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportBoolean;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

/**
 * This class represents the trucks that can be added to the internal fleet of the company.
 * Based on the report p.48-49
 *
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
    private double basePrice;
    private double basePriceDelivery;
    private double maintenanceCostsFactor;
    private PropertyChangeSupportDouble resellPrice;

    private static final String DEFAULTS_PROPERTIES_FILE = "logistics-defaults";

    /**
     * Constructor
     * Generates and initializes a new truck according to the specified parameters and p.48-49.
     * @param name The name of the truck.
     * @param ecoIndexTruck The eco index of the truck.
     * @param qualityIndexTruck The quality index of the truck.
     * @param purchasePriceTruckFactor The factor that influences the purchase price of the truck.
     * @param fixCostsDeliveryFactor The factor that influences the fix costs per delivery with the truck.
     */
    public Truck(String name, double ecoIndexTruck, double qualityIndexTruck, double purchasePriceTruckFactor, double fixCostsDeliveryFactor){
        this.name = name;

        this.initProperties();

        this.ecoIndex = DecimalRound.round(ecoIndexTruck, 2);
        this.qualityIndex = DecimalRound.round(qualityIndexTruck, 2);
        this.purchasePrice = DecimalRound.round(this.basePrice * purchasePriceTruckFactor, 2);
        this.fixCostsDelivery = DecimalRound.round(this.basePriceDelivery * fixCostsDeliveryFactor, 2);

        //monthly maintenance costs
        this.fixTruckCost = DecimalRound.round((this.purchasePrice * this.maintenanceCostsFactor) / 12, 2);
        //TODO for 9 years
        this.depreciationRate = 1 / this.usefulLife;

        this.resellPrice = new PropertyChangeSupportDouble();
        this.resellPrice.setValue(this.purchasePrice);
        this.resellPrice.setPropertyChangedName("resellPrice");
    }

    /**
     * Initializes the truck values using the corresponding properties file.
     */
    private void initProperties(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULTS_PROPERTIES_FILE);
        this.basePrice = Double.valueOf(resourceBundle.getString("logistics.truck.base.price"));
        this.basePriceDelivery = Double.valueOf(resourceBundle.getString("logistics.truck.base.price.delivery"));
        this.capacity = Integer.valueOf(resourceBundle.getString("logistics.truck.capacity"));
        this.maintenanceCostsFactor = Double.valueOf(resourceBundle.getString("logistics.truck.maintenance.costs.factor"));
        this.usefulLife = Integer.valueOf(resourceBundle.getString("logistics.truck.useful.life"));
        this.variableCostsDelivery = Double.valueOf(resourceBundle.getString("logistics.truck.variable.costs.delivery"));
    }

    /**
     * Calculates the number of years the truck has been used for.
     * @param gameDate The current date in the game.
     * @return Returns the number of years the truck has been used for.
     */
    public int calculateTimeUsed(LocalDate gameDate){
        return Period.between(this.purchaseDate, gameDate).getYears();
    }

    /**
     * Sets the purchase date of the truck.
     * @param purchaseDate The purchase date of the truck.
     */
    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
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

    public LocalDate getPurchaseDate() {
        return this.purchaseDate;
    }

    public String getName() {
        return this.name;
    }

    public void setResellPrice(double resellPrice) {
        this.resellPrice.setValue(resellPrice);
    }

    public double getResellPrice(){
        return this.resellPrice.getValue();
    }

    /**
     * Register a change listener that notifies, if a property has changed.
     *
     * @param listener A property change listener.
     */
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        this.resellPrice.addPropertyChangeListener(listener);
    }
}
