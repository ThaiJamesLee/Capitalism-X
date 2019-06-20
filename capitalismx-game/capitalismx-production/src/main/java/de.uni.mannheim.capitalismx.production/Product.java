package de.uni.mannheim.capitalismx.production;

import de.uni.mannheim.capitalismx.procurement.component.Component;

import java.time.LocalDate;
import java.util.ArrayList;

public class Product {
    private String productName;
    private ProductCategory productCategory;
    private ArrayList<Component> components;
    private double totalProcurementQuality;
    private double totalProductQuality;
    private LocalDate launchDate;
    private double totalComponentCosts;
    private double totalProductVariableCosts;
    private double totalProductCosts;
    private double salesPrice;
    private double profitMargin;
    private double averageProductQuality;

    public Product(String productName, ProductCategory productCategory, ArrayList<Component> components) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.components = components;
        this.totalComponentCosts = 0;
        for(Component c : components) {
            this.totalComponentCosts += c.getBaseCost();
        }
        /* placeholder for ecoCost TODO*/
        int ecoCostPerProduct = 3000;
        this.totalProductVariableCosts = this.totalComponentCosts + ecoCostPerProduct;
    }

    public String toString() {
        return this.productName;
    }

    public ProductCategory getProductCategory() {
        return this.productCategory;
    }

    public double calculateTotalVariableCosts() {
        /* TODO placeholder for ecoCost */
        int ecoCostPerProduct = 3000;
        this.totalProductVariableCosts = this.totalComponentCosts + ecoCostPerProduct;
        return this.totalProductVariableCosts;
    }

    public double calculateTotalProcurementQuality() {
        for(Component c : components) {
            this.totalProcurementQuality += (0.4 * c.getSupplierEcoIndex() + 0.6 * c.getSupplierQuality()) * c.getBaseUtility();
        }
        return this.totalProcurementQuality / this.components.size();
    }

    public double calculateTotalProductQuality(double productionTechnologyFactor, double totalEngineerProductivity, double researchAndDevelopmentFactor) {
        /* the math.pow operation calculates the 10th root of totalEignineerProductivity*/
        this.totalProductQuality = this.totalProcurementQuality * productionTechnologyFactor * researchAndDevelopmentFactor * Math.pow(Math.E, Math.log(totalEngineerProductivity)/10);
        return this.totalProductQuality;
    }


    public double calculateAverageBaseQuality() {
        double aggregatedComponentSupplierQuality = 0;
        for(Component c : this.components) {
            aggregatedComponentSupplierQuality += c.getSupplierQuality();
        }
        this.averageProductQuality = aggregatedComponentSupplierQuality / this.components.size();
        return this.averageProductQuality;
    }

    public double getTotalProductCosts() {
        return totalProductCosts;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }

    public void setTotalProductCosts(double totalProductCosts) {
        this.totalProductCosts = totalProductCosts;
    }

    public double getSalesPrice() {
        return this.salesPrice;
    }

    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public double calculateProfitMargin() {
        this.profitMargin = ((this.salesPrice - this.totalProductCosts) / this.salesPrice) * 100;
        return this.profitMargin;
    }
}
