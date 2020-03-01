package de.uni.mannheim.capitalismx.production.product;

import de.uni.mannheim.capitalismx.procurement.component.*;
import de.uni.mannheim.capitalismx.procurement.component.UnitType;
import de.uni.mannheim.capitalismx.production.exceptions.InvalidSetOfComponentsException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents product.
 * It is an extension of a unit.
 * It has a list of components and associated costs.
 * It also has a unique product name and a set sales price. The warehouse sales price defines for how much the product can
 * be sold directly through the warehouse.
 *
 * @author dzhao
 */
public class Product extends Unit implements Serializable {

    private UnitType unitType;
    private String productName;
    private ProductCategory productCategory;
    private List<Component> components;
    private double initProcurementQuality;
    private double totalProcurementQuality;
    private double totalProductQuality;
    private LocalDate launchDate;
    private double totalComponentCosts;
    private double totalProductVariableCosts;
    private double totalProductCosts;
    private double salesPrice;
    private double profitMargin;
    private double averageProductQuality;
    private double warehouseSalesPrice;

    /**
     * Instantiates a new Product.
     * Checks whether the product has a valid set of components before initializing its variables. Otherwise it throws an
     * InvalidSetOfComponentsException.
     * The component costs are calculated by accumulating the base costs of all components.
     *
     * @param productName     the product name
     * @param productCategory the product category
     * @param components      the components
     * @throws InvalidSetOfComponentsException the invalid set of components exception
     */
    public Product(String productName, ProductCategory productCategory, List<Component> components) throws InvalidSetOfComponentsException {
        if(this.hasValidSetOfComponents(productCategory, components)) {
            this.unitType = UnitType.PRODUCT_UNIT;
            this.productName = productName;
            this.productCategory = productCategory;
            this.components = components;
            this.totalComponentCosts = 0;
            this.initProcurementQuality = 0;
            for (Component c : components) {
                this.totalComponentCosts += c.getBaseCost();
            }
            /* placeholder for ecoCost TODO*/
            int ecoCostPerProduct = 3000;
            this.totalProductVariableCosts = this.totalComponentCosts + ecoCostPerProduct;
            this.salesPrice = 1;
        } else {
            throw new InvalidSetOfComponentsException("Set of Components is not valid for this Type of Product.");
        }
    }

    @Override
    public String toString() {
        return this.productName;
    }

    /**
     * Gets product category.
     *
     * @return the product category
     */
    public ProductCategory getProductCategory() {
        return this.productCategory;
    }

    /**
     * Checks whether the product has a valid set of components based on the product category.
     * The list of components should include exactly 1 component of each available component category of the corresponding product category.
     *
     * @param productCategory the product category
     * @param components      the components
     * @return whether the set of components is valid for this product or not.
     */
    public boolean hasValidSetOfComponents(ProductCategory productCategory, List<Component> components) {
        boolean validSet = true;
        List<ComponentCategory> neededComponentCategories = ProductCategory.getComponentCategories(productCategory);
        if (productCategory == ProductCategory.PHONE) {
            neededComponentCategories.remove(ComponentCategory.P_CAMERA);
        }
        if (productCategory == ProductCategory.GAME_BOY) {
            neededComponentCategories.remove(ComponentCategory.G_CAMERA);
        }
        for (ComponentCategory componentCategory : neededComponentCategories) {
            for(Component component : components) {
                if(component.getComponentCategory() != componentCategory) {
                    validSet = false;
                } else {
                    validSet = true;
                    break;
                }
            }
        }
        List<ComponentCategory> allComponentCategories = ProductCategory.getComponentCategories(productCategory);
        for (Component component : components) {
            if (!allComponentCategories.contains(component.getComponentCategory())) {
                validSet = false;
            } else {
                allComponentCategories.remove(component.getComponentCategory());
            }
        }
        return validSet;
    }

    /**
     * Calculates the total variable costs.
     * Based on report of predecessor group.
     *
     * @return the total variable cost
     */
    public double calculateTotalVariableCosts() {
        /* TODO placeholder for ecoCost */
        int ecoCostPerProduct = 30;
        this.totalProductVariableCosts = this.totalComponentCosts + ecoCostPerProduct;
        return this.totalProductVariableCosts;
    }

    /**
     * Calculates the total procurement quality.
     *
     * @return the total procurement quality
     */
    public double calculateTotalProcurementQuality() {
        for (Component c : this.components) {
            this.initProcurementQuality += (0.4 * c.getSupplierEcoIndex() + 0.6 * c.getSupplierQuality()) * c.getBaseUtility();
        }
        this.totalProcurementQuality = this.initProcurementQuality / this.components.size();
        this.initProcurementQuality = 0;
        return this.totalProcurementQuality;
    }

    /**
     * Calculates the total product quality.
     * It is affected by the productionTechnologyFactor, totalEngineerProductivity, and qualityAssuranceFactor.
     * Based on the report of the predecessor group.
     *
     * @param productionTechnologyFactor   the production technology factor of the production department
     * @param totalEngineerProductivity    the total engineer productivity
     * @param qualityAssuranceFactor the quality assurance factor of the production department
     * @return the total product quality
     */
    public double calculateTotalProductQuality(double productionTechnologyFactor, double totalEngineerProductivity, double qualityAssuranceFactor) {
        /* the math.pow operation calculates the 10th root of totalEngineerProductivity*/
        this.totalProductQuality = this.calculateTotalProcurementQuality() * productionTechnologyFactor * qualityAssuranceFactor * Math.pow(Math.E, Math.log(totalEngineerProductivity)/10);
        return this.totalProductQuality;
    }

    /**
     * Gets total product quality.
     *
     * @return the total product quality
     */
    public double getTotalProductQuality() {
        return totalProductQuality;
    }

    /**
     * Calculates average base quality of product by using the average component supplier quality.
     *
     * @return the the average product quality
     */
    public double calculateAverageBaseQuality() {
        double aggregatedComponentSupplierQuality = 0;
        for (Component c : this.components) {
            aggregatedComponentSupplierQuality += c.getSupplierQuality();
        }
        this.averageProductQuality = aggregatedComponentSupplierQuality / this.components.size();
        return this.averageProductQuality;
    }

    /**
     * Gets total product costs.
     *
     * @return the total product
     */
    public double getTotalProductCosts() {
        return totalProductCosts;
    }

    /**
     * Sets launch date.
     *
     * @param launchDate the launch date
     */
    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }

    /**
     * Sets total product costs.
     *
     * @param totalProductCosts the total product costs
     */
    public void setTotalProductCosts(double totalProductCosts) {
        this.totalProductCosts = totalProductCosts;
    }

    public double getSalesPrice() {
        return this.salesPrice;
    }

    /**
     * Sets sales price.
     *
     * @param salesPrice the sales price
     */
    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }

    /**
     * Calculates the profit margin .
     * Based on report of predecessor group.
     *
     * @return the profit margin
     */
    public double calculateProfitMargin() {
        this.profitMargin = ((this.salesPrice - this.totalProductCosts) / this.salesPrice) * 100;
        return this.profitMargin;
    }

    /**
     * Gets components.
     *
     * @return the components
     */
    public List<Component> getComponents() {
        return components;
    }

    /**
     * Gets launch date.
     *
     * @return the launch date
     */
    public LocalDate getLaunchDate() {
        return this.launchDate;
    }

    /**
     * Gets product name.
     *
     * @return the product name
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * Gets total procurement quality.
     *
     * @return the total procurement quality
     */
    public double getTotalProcurementQuality() {
        return this.totalProcurementQuality;
    }

    /**
     * Gets total component costs.
     *
     * @return the total component costs
     */
    public double getTotalComponentCosts() {
        return this.totalComponentCosts;
    }

    /**
     * Gets total product variable costs.
     *
     * @return the total product variable costs
     */
    public double getTotalProductVariableCosts() {
        return this.totalProductVariableCosts;
    }

    /**
     * Gets profit margin.
     *
     * @return the profit margin
     */
    public double getProfitMargin() {
        return this.profitMargin;
    }

    /**
     * Gets average product quality.
     *
     * @return the average product quality
     */
    public double getAverageProductQuality() {
        return this.averageProductQuality;
    }

    /**
     * Gets unit type.
     *
     * @return the unit type
     */
    public UnitType getUnitType() {
        return this.unitType;
    }

    /**
     * Gets product costs.
     * It accumulates the component base costs.
     *
     * @param gameDate the game date
     * @return the product costs
     */
    public double getProductCosts(LocalDate gameDate) {
        double productCosts = 0;
        for (Component component : this.components) {
            productCosts += component.getBaseCost();
        }
        return productCosts;
    }

    /**
     * Gets the warehouse sales price.
     * It determines for how much a product can be sold in the warehouse.
     * Currently set to half of total component costs, might need some refactoring for balancing.
     *
     * @return warehouse sales price
     */
    public double getWarehouseSalesPrice() {
        this.warehouseSalesPrice = this.totalComponentCosts / 2;
        return this.warehouseSalesPrice;
    }
}