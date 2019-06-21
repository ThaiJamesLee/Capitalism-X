package de.uni.mannheim.capitalismx.production;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentCategory;

import java.time.LocalDate;
import java.util.*;

public class Product {
    private String productName;
    private ProductCategory productCategory;
    private List<Component> components;
    private double totalProcurementQuality;
    private double totalProductQuality;
    private LocalDate launchDate;
    private double totalComponentCosts;
    private double totalProductVariableCosts;
    private double totalProductCosts;
    private double salesPrice;
    private double profitMargin;
    private double averageProductQuality;

    public Product(String productName, ProductCategory productCategory, List<Component> components) {
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

    public double getTotalProductQuality() {
        return totalProductQuality;
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

    public List<Component> getComponents() {
        return components;
    }

    public LocalDate getLaunchDate() {
        return this.launchDate;
    }

    /*
    public List<Component> getNewestPossibleComponents(int currentYear) {
        Component[] components = Component.values();
        List<Component> availableComponents = new ArrayList<>();
        for(Component c : components) {
            if(c.getAvailabilityDate() <= currentYear) {
                availableComponents.add(c);
            }
        }
        List<Component> resultList = new ArrayList<>();
        switch(this.productCategory) {
            case PHONE:
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.P_CAMERA, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.P_CONNECTIVITY, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.P_CPU, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.P_DISPLAYCASE, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.P_KEYPAD, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.P_POWERSUPPLY, availableComponents));
                break;
            case GAME_BOY:
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.G_CAMERA, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.G_CONNECTIVITY, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.G_CPU, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.G_DISPLAYCASE, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.G_POWERSUPPLY, availableComponents));
                break;
            case NOTEBOOK:
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.N_CPU, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.N_DISPLAYCASE, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.N_POWERSUPPLY, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.N_SOFTWARE, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.N_STORAGE, availableComponents));
                break;
            case TELEVISION:
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.T_CASE, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.T_DISPLAY, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.T_OS, availableComponents));
                resultList.add(this.getNewestOfComponentCategory(ComponentCategory.T_SOUND, availableComponents));
                break;
            default: // Do nothing
        }
        return resultList;
    }

    private Component getNewestOfComponentCategory(ComponentCategory componentCategory, List<Component> availableComponents) {
        List<Component> filteredList = new ArrayList<>();
        for(Component c : availableComponents) {
            if(c.getComponentCategory().equals(componentCategory)) {
                filteredList.add(c);
            }
        }
        Component newestComponent = null;
        int maxYear = 0;
        for(Component c : filteredList) {
            if(c.getAvailabilityDate() > maxYear) {
                maxYear = c.getAvailabilityDate();
                newestComponent = c;
            }
        }
        return newestComponent;
    }
     */
}