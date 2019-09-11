package de.uni.mannheim.capitalismx.customer;

import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.production.Product;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerSatisfaction implements Serializable {

    private static CustomerSatisfaction instance;
    private double averageOverallAppeal;
    private Map<Product, Double> overallAppeal;
    private double totalSupportQuality;
    private double logisticIndex;
    private double companyImage;
    private double employerBranding;
    private List<Product> products;
    private Map<Product, Double> productAppeal;
    private Map<Product, Double> priceAppeal;
    private double customerSatisfactionOverallAppeal;
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

    private Map<Product, Double> calculatePriceAppeal(LocalDate gameDate) {
        Map<Product, Double> priceAppeal = new HashMap<>();
        for(Product product : this.products) {
            double proxyPrice = 0;
            for(Component component : product.getComponents()) {
                proxyPrice += component.calculateBaseCost(gameDate);
            }
            priceAppeal.put(product, proxyPrice / product.getSalesPrice());
        }
        this.priceAppeal = priceAppeal;
        return this.priceAppeal;
    }

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

    private double calculateAverageOverallAppeal(LocalDate gameDate) {
        double aggregatedOverallAppeal = 0;
        Map<Product, Double> overallAppealMap = calculateOverallAppeal(gameDate);
        for(Map.Entry<Product, Double> entry : overallAppealMap.entrySet()) {
            aggregatedOverallAppeal += entry.getValue();
        }
        this.averageOverallAppeal = aggregatedOverallAppeal / overallAppealMap.size();
        return this.averageOverallAppeal;
    }

    public double calculateCustomerSatisfactionOverallAppeal(LocalDate gameDate) {
        this.customerSatisfactionOverallAppeal = Math.tanh(this.calculateAverageOverallAppeal(gameDate) * 0.7) * 100;
        return this.customerSatisfactionOverallAppeal;
    }

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

    public Map<Product, Double> getOverallAppeal() {
        return overallAppeal;
    }
}
