package de.uni.mannheim.capitalismx.marketing.consultancy;

/**
 * Definition of consultancies.
 * @author duly
 */
public enum ConsultancyType {

    WORLD_FAMOUS("O'Reilly & Company", 5000, 0.8, 0.9, 0.85, 0.7, 0.8, 0.95, new double[] {0.8, 0.9, 0.85, 0.7, 0.8, 0.95}),
    LOCAL_CONSULTANCY("Sinoido Consulting", 3000, 0.85, 0.9, 0.9, 0.65, 0.8, 0.9, new double[]{0.85, 0.9, 0.9, 0.65, 0.8, 0.9}),
    STUDENT_CONSULTANCY("Wannabe Consultants", 1000, 0.9, 0.95, 0.8, 0.6, 0.85, 0.9, new double[] {0.9, 0.95, 0.8, 0.6, 0.85, 0.9});


    private String name;

    //weights
    private double totalSupportQuality;
    private double logisticIndex;
    private double companyImage;
    private double productionTechnology;
    private double manufactureEfficiency;
    private double totalJobSatisfaction;
    private Double[] weights;

    private int cost;

    ConsultancyType(String name, int cost, double totalSupportQuality, double logisticIndex, double companyImage, double productionTechnology, double manufactureEfficiency, double totalJobSatisfaction, double[] weights) {
        this.name = name;
        this.cost = cost;

        this.totalSupportQuality = totalSupportQuality;
        this.logisticIndex = logisticIndex;
        this.companyImage = companyImage;
        this.productionTechnology = productionTechnology;
        this.manufactureEfficiency = manufactureEfficiency;
        this.totalJobSatisfaction = totalJobSatisfaction;
    }

    public String getName() {
        return name;
    }

    public double getTotalSupportQuality() {
        return totalSupportQuality;
    }

    public double getLogisticIndex() {
        return logisticIndex;
    }

    public double getCompanyImage() {
        return companyImage;
    }

    public double getProductionTechnology() {
        return productionTechnology;
    }

    public double getManufactureEfficiency() {
        return manufactureEfficiency;
    }

    public double getTotalJobSatisfaction() {
        return totalJobSatisfaction;
    }

    public int getCost() {
        return cost;
    }
}