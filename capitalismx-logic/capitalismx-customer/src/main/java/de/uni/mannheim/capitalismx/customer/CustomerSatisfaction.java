package de.uni.mannheim.capitalismx.customer;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.production.product.Product;
import de.uni.mannheim.capitalismx.production.department.ProductionDepartment;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * This class is responsible to calculate the customer satisfaction.
 * Before the customer satisfaction can be calculated, the GameState must have a certain progress.
 *
 * It requires the following values:
 * - the {@link ProductionDepartment} must have introduced some products already.
 * - the total support quality of the {@link de.uni.mannheim.capitalismx.logistic.support.ProductSupport}
 * - the company image score from the {@link de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment}
 * - the job satisfaction score from {@link de.uni.mannheim.capitalismx.hr.department.HRDepartment}
 * - the employerBranding = jobSatisfaction * 0.6 + companyImage * 0.4
 * - the logisticIndex from the {@link de.uni.mannheim.capitalismx.logistic.logistics.LogisticsDepartment}
 *
 *
 * @author dzhao
 * @author duly
 */
public class CustomerSatisfaction implements Serializable {

    private static CustomerSatisfaction instance;

    /**
     * The average overall appeal.
     */
    private double averageOverallAppeal;

    /**
     * The overall appeal for each product.
     */
    private Map<Product, Double> overallAppeal;

    /**
     * The totalSupportQuality.
     * See in {@link de.uni.mannheim.capitalismx.logistic.support.ProductSupport}.
     */
    private double totalSupportQuality;

    /**
     * The logisticIndex.
     * See in {@link de.uni.mannheim.capitalismx.logistic.logistics.LogisticsDepartment}.
     */
    private double logisticIndex;

    /**
     * The companyImage.
     * See in {@link de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment}.
     */
    private double companyImage;

    /**
     * The employerBranding.
     * This depends on the current game state.
     */
    private double employerBranding;

    /**
     * List of currently launched products.
     * Needs to be fetched on every refresh.
     */
    private List<Product> products;

    /**
     * The product appeal value for each product.
     */
    private Map<Product, Double> productAppeal;

    /**
     * The price appeal for each product.
     */
    private Map<Product, Double> priceAppeal;

    /**
     * The overall appeal customer satisfaction.
     */
    private double customerSatisfactionOverallAppeal;

    /**
     * The total customer satisfaction.
     */
    private double customerSatisfaction;

    private CustomerSatisfaction() {
        this.totalSupportQuality = 0;
        this.logisticIndex = 0;
        this.companyImage = 0;
        this.employerBranding = 0;
        this.products = new ArrayList<>();
        this.productAppeal = new HashMap<>();
        this.priceAppeal = new HashMap<>();
        this.overallAppeal = new HashMap<>();
    }

    public static synchronized CustomerSatisfaction getInstance() {
        if(CustomerSatisfaction.instance == null) {
            CustomerSatisfaction.instance = new CustomerSatisfaction();
        }
        return CustomerSatisfaction.instance;
    }

    /**
     * This method allows to ignore the singleton and operate on an independent instance.
     * @return Return a fresh instance.
     */
    public static CustomerSatisfaction createInstance() {
        return new CustomerSatisfaction();
    }

    private Map<Product, Double> calculateProductAppeal() {
        Map<Product, Double> productAppeal = new HashMap<>();
        double highestProductQuality = 0;
        for(Product product : this.products) {
            if(product.getTotalProductQuality() > highestProductQuality) {
                highestProductQuality = product.getTotalProductQuality();
            }
        }
        /* placeholder until we have competitors */
        double marketProductUtility = highestProductQuality;
        double proxyQuality = Math.max(highestProductQuality, marketProductUtility);
        for(Product product : this.products) {
            productAppeal.put(product, product.getTotalProductQuality() / proxyQuality);
        }
        this.productAppeal = productAppeal;
        return this.productAppeal;
    }

    /**
     * Calculate the price appeal of each product for the current state of the game.
     * @param gameDate The current date of the game.
     * @return Returns a map of product and price appeal.
     */
    private Map<Product, Double> calculatePriceAppeal(LocalDate gameDate) {
        Map<Product, Double> priceAppeal = new HashMap<>();
        for(Product product : this.products) {
            double proxyPrice = 0;
            for(Component component : product.getComponents()) {
                proxyPrice += component.getTimeBasedComponentCost(gameDate);
            }
            priceAppeal.put(product, proxyPrice / product.getSalesPrice());
        }
        this.priceAppeal = priceAppeal;
        return this.priceAppeal;
    }

    /**
     * Calculate the overall appeal of each product for the current state of the game.
     * @param gameDate The current date of the game.
     * @return Returns a map of product and overall appeal of the products.
     */
    public Map<Product, Double> calculateOverallAppeal(LocalDate gameDate) {
        Map<Product, Double> overallAppeal = new HashMap<>();
        Map<Product, Double> productAppealMap = calculateProductAppeal();
        Map<Product, Double> priceAppealMap = calculatePriceAppeal(gameDate);
        for(Product product : this.products) {
            overallAppeal.put(product, productAppealMap.get(product) * priceAppealMap.get(product));
        }
        this.overallAppeal = overallAppeal;
        return this.overallAppeal;
    }


    /**
     * Calculate the overall appeal of each product for the current state of the game.
     * @param gameDate The current date of the game.
     * @return Returns a map of product and overall appeal of the products.
     */
    private double calculateAverageOverallAppeal(LocalDate gameDate) {
        double aggregatedOverallAppeal = 0;
        Map<Product, Double> overallAppealMap = calculateOverallAppeal(gameDate);
        for(Map.Entry<Product, Double> entry : overallAppealMap.entrySet()) {
            aggregatedOverallAppeal += entry.getValue();
        }
        this.averageOverallAppeal = aggregatedOverallAppeal / overallAppealMap.size();
        return this.averageOverallAppeal;
    }

    /**
     * Calculate the customer satisfaction overall appeal.
     * This method is used to calculate the concrete customer satisfaction.
     * @param gameDate The current date of the game.
     * @return Returns the current overall appeal customer satisfaction.
     */
    private double calculateCustomerSatisfactionOverallAppeal(LocalDate gameDate) {
        this.customerSatisfactionOverallAppeal = Math.tanh(this.calculateAverageOverallAppeal(gameDate) * 0.7) * 100;
        return this.customerSatisfactionOverallAppeal;
    }

    /**
     * Calculate the customer satisfaction.
     * @param gameDate The current date of the game.
     * @return Returns the current customer satisfaction.
     */
    public double calculateCustomerSatisfaction(LocalDate gameDate) {
        this.customerSatisfaction = Math.tanh(0.5 * calculateCustomerSatisfactionOverallAppeal(gameDate) + 0.2 * this.totalSupportQuality + 0.1 * this.logisticIndex +
                0.15 * this.companyImage + 0.05 * employerBranding);
        return this.customerSatisfaction;
    }

    public void setCompanyImage(double companyImage) {
        this.companyImage = companyImage;
    }

    public void setEmployerBranding(double employerBranding) {
        this.employerBranding = employerBranding;
    }

    public void setAverageOverallAppeal(double averageOverallAppeal) {
        this.averageOverallAppeal = averageOverallAppeal;
    }

    public void setTotalSupportQuality(double totalSupportQuality) {
        this.totalSupportQuality = totalSupportQuality;
    }

    public void setLogisticIndex(double logisticIndex) {
        this.logisticIndex = logisticIndex;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public double getCustomerSatisfaction() {
        return customerSatisfaction;
    }
    
    public double getEmployerBranding() {
        return employerBranding;
    }

    public Map<Product, Double> getOverallAppeal() {
        return overallAppeal;
    }

    public void calculateAll(LocalDate gameDate) {
         this.calculateCustomerSatisfaction(gameDate);
    }

    public static void setInstance(CustomerSatisfaction instance) {
        CustomerSatisfaction.instance = instance;
    }
}
