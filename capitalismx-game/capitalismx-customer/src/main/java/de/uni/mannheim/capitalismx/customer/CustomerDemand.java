package de.uni.mannheim.capitalismx.customer;

import de.uni.mannheim.capitalismx.production.Product;

import java.util.HashMap;
import java.util.Map;

public class CustomerDemand {

    private static CustomerDemand instance;
    private Map<Product, Double> productCustomerSatisfactionOverallAppeal;

    private CustomerDemand() {
        this.productCustomerSatisfactionOverallAppeal = new HashMap<>();
    }

    public static synchronized CustomerDemand getInstance() {
        if(CustomerDemand.instance == null) {
            CustomerDemand.instance = new CustomerDemand();
        }
        return CustomerDemand.instance;
    }

    public Map<Product, Double> calculateProductOverallAppeal() {
        Map<Product, Double> productOverallAppeal = new HashMap<>();
        for(Map.Entry<Product, Double> entry : CustomerSatisfaction.getInstance().calculateOverallAppeal().entrySet()) {
            productOverallAppeal.put(entry.getKey(), entry.getValue() * (0.4 * CustomerSatisfaction.getInstance().calculateCustomerSatisfaction() + 0.8));
        }
        this.productCustomerSatisfactionOverallAppeal = productOverallAppeal;
        return  this.productCustomerSatisfactionOverallAppeal;
    }


}
