package de.uni.mannheim.capitalismx.logistic.logistics;

/**
 * @author sdupper
 */
public class ExternalPartner {
    private double ecoIndexPartner;
    private double qualityIndexPartner;
    private double reliabilityIndexPartner;
    private double contractualCost;
    private double variableDeliveryCost;
    private double externalLogisticsIndex;

    public ExternalPartner(double ecoIndexPartner, double qualityIndexPartner, double reliabilityIndexPartner, double contractualCostFactor, double variableDeliveryCostFactor){
        this.ecoIndexPartner = ecoIndexPartner;
        this.qualityIndexPartner = qualityIndexPartner;
        this.reliabilityIndexPartner = reliabilityIndexPartner;
        this.contractualCost = 1000 * contractualCostFactor;
        this.variableDeliveryCost = 5 * variableDeliveryCostFactor;

        this.calculateExternalLogisticsIndex();
    }

    private double calculateExternalLogisticsIndex(){
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
}
