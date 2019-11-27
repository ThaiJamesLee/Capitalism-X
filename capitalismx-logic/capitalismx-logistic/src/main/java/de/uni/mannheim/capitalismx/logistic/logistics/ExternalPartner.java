package de.uni.mannheim.capitalismx.logistic.logistics;

import de.uni.mannheim.capitalismx.utils.number.DecimalRound;

import java.io.Serializable;

/**
 * @author sdupper
 */
public class ExternalPartner implements Serializable {
    private String name;
    private double ecoIndexPartner;
    private double qualityIndexPartner;
    private double reliabilityIndexPartner;
    private double contractualCost;
    private double variableDeliveryCost;
    private double externalLogisticsIndex;

    public ExternalPartner(String name, double ecoIndexPartner, double qualityIndexPartner, double reliabilityIndexPartner, double contractualCostFactor, double variableDeliveryCostFactor){
        this.name = name;
        this.ecoIndexPartner = DecimalRound.round(ecoIndexPartner, 2);
        this.qualityIndexPartner = DecimalRound.round(qualityIndexPartner, 2);
        this.reliabilityIndexPartner = DecimalRound.round(reliabilityIndexPartner, 2);
        this.contractualCost = DecimalRound.round(1000 * contractualCostFactor, 2);
        this.variableDeliveryCost = DecimalRound.round(5 * variableDeliveryCostFactor, 2);

        this.calculateExternalLogisticsIndex();
    }

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
