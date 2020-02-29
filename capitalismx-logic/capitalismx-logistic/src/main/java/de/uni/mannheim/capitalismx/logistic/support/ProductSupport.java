package de.uni.mannheim.capitalismx.logistic.support;

import de.uni.mannheim.capitalismx.logistic.logistics.exception.NotEnoughTruckCapacityException;
import de.uni.mannheim.capitalismx.logistic.support.exception.NoExternalSupportPartnerException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class represents the product support.
 * The company can choose the offer different types of product support, e.g., online support.
 * Based on the report p.53-54
 *
 * @author sdupper
 */
public class ProductSupport implements Serializable {

    /**
     * The singleton pointer.
     */
    private static ProductSupport instance;

    /**
     * The support types provided by the company.
     */
    private ArrayList<SupportType> supportTypes;

    /**
     * The external support partner hired to provide support.
     */
    private ExternalSupportPartner externalSupportPartner;

    /**
     * The sum of support type qualities of the provided support types.
     */
    private int totalSupportTypeQuality;

    private double totalSupportQuality;
    private double totalSupportCosts;

    /**
     * The different support types that can be provided by the company. They differ in their support type quality and
     * costs according to p.53.
     */
    public enum SupportType implements Serializable{
        //TODO change 0 supportTypeQuality
        NO_PRODUCT_SUPPORT("no"),
        ONLINE_SELF_SERVICE("online.self"),
        ONLINE_SUPPORT("online.support"),
        TELEPHONE_SUPPORT("telephone.support"),
        STORE_SUPPORT("store.support"),
        ADDITIONAL_SERVICES("additional.services");

        private int supportTypeQuality;
        private int costsSupportType;
        private String name;

        private static final String DEFAULTS_PROPERTIES_FILE = "logistics-defaults";

        SupportType(String name){
            this.name = name;

            this.initProperties();
        }

        /**
         * Initializes the support type values using the corresponding properties file.
         */
        private void initProperties(){
            ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULTS_PROPERTIES_FILE);
            this.supportTypeQuality = Integer.valueOf(resourceBundle.getString("logistics.support.type.quality." + this.name));
            this.costsSupportType = Integer.valueOf(resourceBundle.getString("logistics.support.type.costs." + this.name));
        }

        public int getSupportTypeQuality(){
            return this.supportTypeQuality;
        }

        public int getCostsSupportType(){
            return this.costsSupportType;
        }

        /**
         * Determines the localized name and returns it.
         * @return Returns the localized name.
         */
        public String getLocalizedName(Locale locale) {
            return this.getLocalisedString("logistics.support." + this.name, locale);
        }

        public String getLocalisedString(String text, Locale locale) {
            ResourceBundle langBundle = ResourceBundle.getBundle("logistics-module", locale);
            return langBundle.getString(text);
        }
    }

    /**
     * The different external support partners that can be hired by the company. The company has to hire an external
     * partner to provide product support. The partners differ in their contractual costs and quality.
     */
    public enum ExternalSupportPartner implements Serializable{
        NO_PARTNER("no"),
        PARTNER_1("1"),
        PARTNER_2("2");

        private int contractualCosts;
        private int qualityIndex;
        private String name;

        private static final String DEFAULTS_PROPERTIES_FILE = "logistics-defaults";

        ExternalSupportPartner(String name){
            this.name = name;

            this.initProperties();

            this.name = "Partner " + name;
        }

        /**
         * Initializes the external support partner values using the corresponding properties file.
         */
        private void initProperties(){
            ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULTS_PROPERTIES_FILE);
            this.contractualCosts = Integer.valueOf(resourceBundle.getString("logistics.support.partner.costs." + this.name));
            this.qualityIndex = Integer.valueOf(resourceBundle.getString("logistics.support.partner.costs." + this.name));
        }

        public int getContractualCosts() {
            return this.contractualCosts;
        }

        public int getQualityIndex() {
            return this.qualityIndex;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Constructor
     * Initializes the variables relevant for product support.
     */
    protected ProductSupport(){
        this.supportTypes = new ArrayList<>();
        this.supportTypes.add(SupportType.NO_PRODUCT_SUPPORT);
        this.externalSupportPartner = ExternalSupportPartner.NO_PARTNER;
        this.totalSupportTypeQuality = 0;
        this.totalSupportQuality = 0;
        this.totalSupportCosts = 0;
    }

    /**
     *
     * @return Returns the singleton instance
     */
    public static synchronized ProductSupport getInstance() {
        if(ProductSupport.instance == null) {
            ProductSupport.instance = new ProductSupport();
        }
        return ProductSupport.instance;
    }

    /**
     * Calculates different values relevant for product support, e.g., the total support quality.
     */
    public void calculateAll(){
        this.calculateTotalSupportTypeQuality();
        this.calculateTotalSupportQuality();
        this.calculateTotalSupportCosts();
    }

    /**
     * Generates a list of the available support types to choose from.
     * @return Returns a list of the available support types.
     */
    public ArrayList<SupportType> generateSupportTypeSelection(){
        ArrayList<SupportType> supportTypeSelection = new ArrayList<SupportType>();

        for(SupportType supportType : SupportType.values()){
            supportTypeSelection.add(supportType);
        }

        supportTypeSelection.remove(SupportType.NO_PRODUCT_SUPPORT);
        return  supportTypeSelection;
    }

    /**
     * Adds a new support type to the list of support types provided by the company. Only possible if an external
     * support partner is hired.
     * @param supportType The new support type to be added.
     * @throws NoExternalSupportPartnerException if no external support partner is hired.
     */
    public void addSupport(SupportType supportType) throws NoExternalSupportPartnerException{
        if((externalSupportPartner != ExternalSupportPartner.NO_PARTNER)){
            if(!supportTypes.contains(supportType)){
                this.supportTypes.add(supportType);
                this.supportTypes.remove(SupportType.NO_PRODUCT_SUPPORT);
            }
        }else{
            throw new NoExternalSupportPartnerException("No external support partner hired");
        }
    }

    /**
     * Removes a support type from the list of support types provided by the company.
     * @param supportType The support type to be removed.
     */
    public void removeSupport(SupportType supportType){
        this.supportTypes.remove(supportType);
        if(this.supportTypes.size() == 0){
            this.supportTypes.add(SupportType.NO_PRODUCT_SUPPORT);
        }
    }

    /**
     * Generates a list of the available external support partners to choose from.
     * @return Returns a list of the available external support partners.
     */
    public ArrayList<ExternalSupportPartner> generateExternalSupportPartnerSelection(){
        ArrayList<ExternalSupportPartner> externalSupportPartnerSelection = new ArrayList<ExternalSupportPartner>();

        for(ExternalSupportPartner externalSupportPartner : ExternalSupportPartner.values()){
            externalSupportPartnerSelection.add(externalSupportPartner);
        }

        externalSupportPartnerSelection.remove(ExternalSupportPartner.NO_PARTNER);
        return externalSupportPartnerSelection;
    }

    /**
     * Hires the specified external support partner.
     * @param externalSupportPartner The external support partner to be hired.
     */
    public void addExternalSupportPartner(ExternalSupportPartner externalSupportPartner){
        this.externalSupportPartner = externalSupportPartner;
    }

    /**
     * Fires the currently hired external support partner.
     */
    public void removeExternalSupportPartner(){
        this.externalSupportPartner = ExternalSupportPartner.NO_PARTNER;
        this.supportTypes.clear();
        this.supportTypes.add(SupportType.NO_PRODUCT_SUPPORT);
    }

    /**
     * Calculates the total support type quality by adding up the support type qualities of all provided support types.
     * @return Returns the total support type quality.
     */
    protected double calculateTotalSupportTypeQuality(){
        this.totalSupportTypeQuality = 0;
        for(SupportType supportType : supportTypes){
            this.totalSupportTypeQuality += supportType.getSupportTypeQuality();
        }
        return this.totalSupportTypeQuality;
    }

    /**
     * Calculates the total support quality based on the quality index of the external support partner and the total
     * support type quality according to p.53.
     * @return Returns the total support quality.
     */
    protected double calculateTotalSupportQuality(){
        if(this.externalSupportPartner.getQualityIndex() <= 50){
            this.totalSupportQuality = 0.4 * this.externalSupportPartner.getQualityIndex() + 0.6 * this.calculateTotalSupportTypeQuality();
        }else{
            this.totalSupportQuality = 0.3 * this.externalSupportPartner.getQualityIndex() + 0.7 * this.calculateTotalSupportTypeQuality();
        }
        return this.totalSupportQuality;
    }

    /**
     * Calculates the total support costs based on the costs of the provided support types and the contractual costs
     * of the external support partner according to p.54.
     * @return Returns the  total support costs.
     */
    public double calculateTotalSupportCosts(){
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

    public static void setInstance(ProductSupport instance) {
        ProductSupport.instance = instance;
    }

    public static ProductSupport createInstance() {
        return new ProductSupport();
    }
}
