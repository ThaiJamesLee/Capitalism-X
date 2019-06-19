package de.uni.mannheim.capitalismx.production;

public class Production {
    private double productionTechnologyFactor;
    private ProductionTechnology productionTechnology;
    private double researchAndDevelopmentFactor;
    private ProductionInvestment researchAndDevelopment;
    private double processAutomationFactor;
    private ProductionInvestment processAutomation;
    private double totalEngineerQualityOfWork;
    private double totalEngineerProductivity;
    private ProductionInvestment systemSecurity;

    public Production() {
        this.researchAndDevelopment = new ProductionInvestment("Research and Development");
        this.processAutomation = new ProductionInvestment("Process Automation");
        this.systemSecurity = new ProductionInvestment("System Security");
    }

    public void calculateProductionTechnologyFactor() {
        this.productionTechnologyFactor = 0.7 + 0.1 * this.productionTechnology.getRange();
    }

    public void calculateResearchAndDevelopmentFactor() {
        this.researchAndDevelopmentFactor = 0.95 + 0.05 * this.researchAndDevelopmentFactor;
    }

    public void calculateProcessAutomationFactor() {
        this.processAutomationFactor = 0.95 + 0.05 * this.processAutomation.getLevel();
    }

    public void calculateTotalEngineerQualityOfWork() {
        /* placeholder for the quality of work of the engineering team*/
        this.totalEngineerQualityOfWork = 0.7;
    }

    public void calculateTotalEngineerProductivity() {
        this.totalEngineerProductivity = this.totalEngineerQualityOfWork * this.processAutomationFactor;
    }

    /* once every*/
}
