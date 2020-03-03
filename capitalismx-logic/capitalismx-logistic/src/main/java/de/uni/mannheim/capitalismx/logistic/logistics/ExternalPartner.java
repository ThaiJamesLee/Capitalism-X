package de.uni.mannheim.capitalismx.logistic.logistics;

import de.uni.mannheim.capitalismx.utils.number.DecimalRound;

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * This class represents the external logistics partner.
 * The company has the opportunity to contract an external partner to deliver products. The partners differ in their
 * characteristics, e.g., their environmental friendliness and delivery quality.
 * Based on the report p.51-53
 *
 * @author sdupper
 */
public class ExternalPartner implements Serializable {

    /**
     * Represents the environmental friendliness of the partner.
     */
    private double ecoIndexPartner;

    /**
     * Represents the delivery quality of the partner.
     */
    private double qualityIndexPartner;

    /**
     * Represents the reliability of the partner.
     */
    private double reliabilityIndexPartner;

    private String name;
    private double contractualCost;
    private double variableDeliveryCost;
    private double externalLogisticsIndex;
    private double baseContractualCosts;
    private double baseVariableDeliveryCosts;

    private static final String DEFAULTS_PROPERTIES_FILE = "logistics-defaults";

    /**
     * Constructor
     * Initializes the variables according to the specified parameters and calculates the resulting external logistics
     * index.
     * @param name The name of the external partner.
     * @param ecoIndexPartner The ecoIndex of the partner.
     * @param qualityIndexPartner The qualityIndex of the partner.
     * @param reliabilityIndexPartner The reliabilityIndex of the partner.
     * @param contractualCostFactor The contractualCostFactor that influences the contractual costs.
     * @param variableDeliveryCostFactor The variableDeliveryCostFactor that influences the variable delivery costs.
     */
    public ExternalPartner(String name, double ecoIndexPartner, double qualityIndexPartner, double reliabilityIndexPartner, double contractualCostFactor, double variableDeliveryCostFactor){
        this.name = name;

        this.initProperties();

        this.ecoIndexPartner = DecimalRound.round(ecoIndexPartner, 2);
        this.qualityIndexPartner = DecimalRound.round(qualityIndexPartner, 2);
        this.reliabilityIndexPartner = DecimalRound.round(reliabilityIndexPartner, 2);
        this.contractualCost = DecimalRound.round(this.baseContractualCosts * contractualCostFactor, 2);
        this.variableDeliveryCost = DecimalRound.round(this.baseVariableDeliveryCosts * variableDeliveryCostFactor, 2);

        this.calculateExternalLogisticsIndex();
    }

    /**
     * Initializes the external partner values using the corresponding properties file.
     */
    private void initProperties(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULTS_PROPERTIES_FILE);
        this.baseContractualCosts = Double.valueOf(resourceBundle.getString("logistics.external.logistics.partner.base.contractual.costs"));
        this.baseVariableDeliveryCosts = Double.valueOf(resourceBundle.getString("logistics.external.logistics.partner.base.variable.delivery.costs"));
    }

    /**
     * Calculates the external logistics index based on the ecoIndex, qualityIndex, and reliabilityIndex of the partner
     * according to p.51.
     * @return Returns the external logistics index.
     */
    public double calculateExternalLogisticsIndex(){
        if(this.reliabilityIndexPartner <= 40){
            this.externalLogisticsIndex = (this.reliabilityIndexPartner * 0.5 + 0.5 * (this.qualityIndexPartner * 0.8 + this.ecoIndexPartner * 0.2));
        }else{
            this.externalLogisticsIndex = (this.reliabilityIndexPartner * 0.4 + 0.6 * (this.qualityIndexPartner * 0.8 + this.ecoIndexPartner * 0.2));
        }
        return this.externalLogisticsIndex;
    }

    public double getEcoIndexPartner() {
        return this.ecoIndexPartner;
    }

    public double getQualityIndexPartner() {
        return this.qualityIndexPartner;
    }

    public double getReliabilityIndexPartner() {
        return this.reliabilityIndexPartner;
    }

    public double getContractualCost() {
        return this.contractualCost;
    }

    public double getVariableDeliveryCost() {
        return this.variableDeliveryCost;
    }

    public double getExternalLogisticsIndex() {
        return this.externalLogisticsIndex;
    }

    public String getName() {
        return this.name;
    }
}
