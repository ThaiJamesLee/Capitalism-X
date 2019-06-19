package de.uni.mannheim.capitalismx.production;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import java.util.ArrayList;
import java.util.Date;

public class Product {
    private ArrayList<Component> components = new ArrayList<Component>();
    private double totalProcurementQuality;
    private double totalProductQuality;
    private int numberProducedProducts;
    private Date launchDate;



    public void calculateTotalProcurementQuality() {
        for(Component c : components) {
            this.totalProcurementQuality += (0.4 * c.getSupplierEcoIndex() + 0.6 * c.getSupplierQuality()) * c.getBaseUtility();
        }
    }

    public void calculateTotalProductQuality(double productionTechnologyFactor, double totalEngineerProductivity, double researchAndDevelopmentFactor) {
        /* the math.pow operation calculates the 10th root of totalEignineerProductivity*/
        this.totalProductQuality = this.totalProcurementQuality * productionTechnologyFactor * researchAndDevelopmentFactor * Math.pow(Math.E, Math.log(totalEngineerProductivity)/10);
    }


}
