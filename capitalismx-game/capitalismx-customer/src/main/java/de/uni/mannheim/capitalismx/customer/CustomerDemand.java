package de.uni.mannheim.capitalismx.customer;

import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public class CustomerDemand implements Serializable {
    private static CustomerDemand instance;
    private Map<Product, Double> productCustomerSatisfactionOverallAppeal;
    private double totalSalesQualityOfWork;
    private Map<Product, Double> overallAppealDemand;
    private Map<Product, Double> demandPercentage;
    private Map<Product, Double> demandAmount;
    private Map<Product, Integer> daysSinceLaunchDate;
    private Map<Product, Double> periodicDemandAmount;
    private Map<Product, Integer> salesFigures;
    private int gamePopulation;

    private CustomerDemand() {
        this.productCustomerSatisfactionOverallAppeal = new HashMap<>();
        this.overallAppealDemand = new HashMap<>();
        this.demandPercentage = new HashMap<>();
        this.demandAmount = new HashMap<>();
        this.daysSinceLaunchDate = new HashMap<>();
        this.periodicDemandAmount = new HashMap<>();
        this.salesFigures = new HashMap<>();
        this.gamePopulation = 100000;
    }

    public static synchronized CustomerDemand getInstance() {
        if(CustomerDemand.instance == null) {
            CustomerDemand.instance = new CustomerDemand();
        }
        return CustomerDemand.instance;
    }

    private Map<Product, Double> calculateProductOverallAppeal(LocalDate gameDate) {
        Map<Product, Double> productOverallAppeal = new HashMap<>();
        for(Map.Entry<Product, Double> entry : CustomerSatisfaction.getInstance().calculateOverallAppeal(gameDate).entrySet()) {
            productOverallAppeal.put(entry.getKey(), entry.getValue() * (0.4 * CustomerSatisfaction.getInstance().calculateCustomerSatisfaction(gameDate) + 0.8));
        }
        this.productCustomerSatisfactionOverallAppeal = productOverallAppeal;
        return this.productCustomerSatisfactionOverallAppeal;
    }

    private Map<Product, Double> calculateOverallAppealDemand(double totalSalesQualityOfWork, LocalDate gameDate) {
        this.totalSalesQualityOfWork = totalSalesQualityOfWork;
        Map<Product, Double> overallAppealDemand = new HashMap<>();
        Map<Product, Double> productOverallAppeal = this.calculateProductOverallAppeal(gameDate);
        for(Map.Entry<Product, Double> entry : productOverallAppeal.entrySet()) {
            overallAppealDemand.put(entry.getKey(), entry.getValue() * (0.9 + 0.2 * Math.pow(Math.E, Math.log(totalSalesQualityOfWork)/10)));
        }
        this.overallAppealDemand = overallAppealDemand;
        return this.overallAppealDemand;
    }

    private Map<Product, Double> calculateDemandPercentage(double totalSalesQualityOfWork, LocalDate gameDate) {
        this.calculateOverallAppealDemand(totalSalesQualityOfWork, gameDate);
        Map<Product, Double> demandPercentage = new HashMap<>();
        for(Map.Entry<Product, Double> entry : this.overallAppealDemand.entrySet()) {
            demandPercentage.put(entry.getKey(), Math.tanh(entry.getValue() / 2));
        }
        this.demandPercentage = demandPercentage;
        return this.demandPercentage;
    }

    private Map<Product, Double> calculateDemandAmount(double totalSalesQualityOfWork, LocalDate gameDate) {
        Map<Product, Double> demandAmount = new HashMap<>();
        Map<Product, Double> demandPercentage = this.calculateDemandPercentage(totalSalesQualityOfWork, gameDate);
        for(Map.Entry<Product, Double> entry : demandPercentage.entrySet()) {
            demandAmount.put(entry.getKey(), entry.getValue() * this.gamePopulation);
        }
        this.demandAmount = demandAmount;
        return this.demandAmount;
    }

    private Map<Product, Integer> calculateDaysSinceLaunchDate(LocalDate gameDate) {
        Map<Product, Integer> daysSinceLaunchDate = new HashMap<>();
        /* TODO list of products are the list of our own products, we have to change this if we introduce competing products */
        for(Product product : CustomerSatisfaction.getInstance().getProducts()) {
            daysSinceLaunchDate.put(product, Period.between(product.getLaunchDate(), gameDate).getDays());
        }
        this.daysSinceLaunchDate = daysSinceLaunchDate;
        return this.daysSinceLaunchDate;
    }

    private Map<Product, Double> calculatePeriodicDemandAmount(double totalSalesQualityOfWork, LocalDate gameDate) {
        this.calculateDaysSinceLaunchDate(gameDate);
        Map<Product, Double> periodicDemandAmount = new HashMap<>();
        Map<Product, Double> demandAmount = calculateDemandAmount(totalSalesQualityOfWork, gameDate);
        for(Map.Entry<Product, Double> entry : demandAmount.entrySet()) {
            periodicDemandAmount.put(entry.getKey(), entry.getValue() * 0.002 * ((Math.tanh(0.01 * this.daysSinceLaunchDate.get(entry.getKey())-3) / 2) + 0.5));
        }
        this.periodicDemandAmount = periodicDemandAmount;
        return this.periodicDemandAmount;
    }

    /* TODO use inventory of warehouse for calculation not amount of products produced on that day */
    public Map<Product, Integer> calculateSalesFigures(double totalSalesQualityOfWork, LocalDate gameDate) {
        Map<Product, Integer> salesFigures = new HashMap<>();
        Map<Product, Double> periodicDemandAmount = calculatePeriodicDemandAmount(totalSalesQualityOfWork, gameDate);
        for(Map.Entry<Product, Double> entry : periodicDemandAmount.entrySet()) {
            int storedUnits = WarehousingDepartment.getInstance().getInventory().get(entry.getKey());
            if(entry.getValue() > storedUnits) {
                salesFigures.put(entry.getKey(), storedUnits);
            } else {
                salesFigures.put(entry.getKey(), entry.getValue().intValue());
            }
        }
        this.salesFigures = salesFigures;
        return this.salesFigures;
    }

    public void calculateAll(double totalSalesQualityOfWork, LocalDate gameDate) {
        this.calculateSalesFigures(totalSalesQualityOfWork, gameDate);
    }

    public static void setInstance(CustomerDemand instance) {
        CustomerDemand.instance = instance;
    }
}
