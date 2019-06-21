package de.uni.mannheim.capitalismx.logistic.support;

import java.util.ArrayList;

/**
 * @author sdupper
 */
public class ProductSupport {
    private ArrayList<SupportType> supportTypes;
    private ExternalSupportPartner externalSupportPartner;
    private int totalSupportTypeQuality;
    private double totalSupportQuality;
    private double totalSupportCosts;

    private enum SupportType{
        NO_PRODUCT_SUPPORT(-10, 0),
        ONLINE_SELF_SERVICE(0, 50),
        ONLINE_SUPPORT(20, 100),
        TELEPHONE_SUPPORT(30, 250),
        STORE_SUPPORT(40, 400),
        ADDITIONAL_SERVICES(10, 50);

        private int supportTypeQuality;
        private int costsSupportType;

        private SupportType(int supportTypeQuality, int costsSupportType){
            this.supportTypeQuality = supportTypeQuality;
            this.costsSupportType = costsSupportType;
        }

        public int getSupportTypeQuality(){
            return this.supportTypeQuality;
        }

        public int getCostsSupportType(){
            return this.costsSupportType;
        }
    }

    //TODO: generate suitable values
    private enum ExternalSupportPartner{
        NO_PARTNER(0, 0),
        PARTNER_1(1000, 80),
        PARTNER_2(800, 40);

        private int contractualCosts;
        private int qualityIndex;

        private ExternalSupportPartner(int contractualCosts, int qualityIndex){
            this.contractualCosts = contractualCosts;
            this.contractualCosts = qualityIndex;
        }

        public int getContractualCosts() {
            return this.contractualCosts;
        }

        public int getQualityIndex() {
            return this.qualityIndex;
        }
    }

    public ProductSupport(){
        this.supportTypes = new ArrayList<SupportType>();
        this.supportTypes.add(SupportType.NO_PRODUCT_SUPPORT);
        this.externalSupportPartner = ExternalSupportPartner.NO_PARTNER;
        this.totalSupportTypeQuality = 0;
        this.totalSupportQuality = 0;
        this.totalSupportCosts = 0;
    }

    public ArrayList<SupportType> generateSupportTypeSelection(){
        ArrayList<SupportType> supportTypeSelection = new ArrayList<SupportType>();

        for(SupportType supportType : SupportType.values()){
            supportTypeSelection.add(supportType);
        }

        supportTypeSelection.remove(SupportType.NO_PRODUCT_SUPPORT);
        return  supportTypeSelection;
    }

    public void addSupport(SupportType supportType){
        if((externalSupportPartner != ExternalSupportPartner.NO_PARTNER) && (!supportTypes.contains(supportType))){
            this.supportTypes.add(supportType);
        }
    }

    public void removeSupport(SupportType supportType){
        this.supportTypes.remove(supportType);
    }

    public ArrayList<ExternalSupportPartner> generateExternalSupportPartnerSelection(){
        ArrayList<ExternalSupportPartner> externalSupportPartnerSelection = new ArrayList<ExternalSupportPartner>();

        for(ExternalSupportPartner externalSupportPartner : ExternalSupportPartner.values()){
            externalSupportPartnerSelection.add(externalSupportPartner);
        }

        externalSupportPartnerSelection.remove(ExternalSupportPartner.NO_PARTNER);
        return externalSupportPartnerSelection;
    }

    public void addExternalSupportPartner(ExternalSupportPartner externalSupportPartner){
        this.externalSupportPartner = externalSupportPartner;
    }

    public void removeExternalSupportPartner(){
        this.externalSupportPartner = ExternalSupportPartner.NO_PARTNER;
    }

    private double calculateTotalSupportTypeQuality(){
        this.totalSupportTypeQuality = 0;
        for(SupportType supportType : supportTypes){
            this.totalSupportTypeQuality += supportType.getSupportTypeQuality();
        }
        return this.totalSupportTypeQuality;
    }

    private double calculateTotalSupportQuality(){
        this.calculateTotalSupportTypeQuality();
        if(this.externalSupportPartner.getQualityIndex() <= 50){
            this.totalSupportQuality = 0.4 * this.externalSupportPartner.getQualityIndex() + 0.6 * this.calculateTotalSupportTypeQuality();
        }else{
            this.totalSupportQuality = 0.3 * this.externalSupportPartner.getQualityIndex() + 0.7 * this.calculateTotalSupportTypeQuality();
        }
        return this.totalSupportQuality;
    }

    private double calculateTotalSupportCosts(){
        totalSupportCosts = 0;
        for(SupportType supportType : supportTypes){
            this.totalSupportCosts += supportType.getCostsSupportType();
        }
        this.totalSupportCosts += this.externalSupportPartner.getContractualCosts();
        return this.totalSupportCosts;
    }

    public ArrayList<SupportType> getSupportTypes() {
        return this.supportTypes;
    }

    public ExternalSupportPartner getExternalSupportPartner() {
        return this.externalSupportPartner;
    }

    public int getTotalSupportTypeQuality() {
        this.calculateTotalSupportTypeQuality();
        return this.totalSupportTypeQuality;
    }

    public double getTotalSupportQuality() {
        this.calculateTotalSupportQuality();
        return this.totalSupportQuality;
    }

    public double getTotalSupportCosts() {
        this.calculateTotalSupportCosts();
        return this.totalSupportCosts;
    }
}