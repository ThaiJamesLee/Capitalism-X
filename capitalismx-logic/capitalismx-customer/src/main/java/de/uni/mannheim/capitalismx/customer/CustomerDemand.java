package de.uni.mannheim.capitalismx.customer;

import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class computes the current customer demand (depending on the GameState).
 * This class requires the {@link CustomerSatisfaction#calculateCustomerSatisfaction(LocalDate)}.
 * @author dzhao
 * @author duly
 *
 * @since 1.0.0
 */
public class CustomerDemand implements Serializable {

    private static CustomerDemand instance;

    /**
     * The customer satisfaction overall appeal.
     * See in {@link CustomerSatisfaction}.
     */
    private Map<Product, Double> productCustomerSatisfactionOverallAppeal;

    /**
     * The overall appeal demand for each product.
     */
    private Map<Product, Double> overallAppealDemand;

    /**
     * The demand percentage for each product.
     */
    private Map<Product, Double> demandPercentage;

    /**
     * The demand amount for each product.
     */
    private Map<Product, Double> demandAmount;

    /**
     * The days since the launch of the products.
     */
    private Map<Product, Integer> daysSinceLaunchDate;

    /**
     * The perioduc demand amount for each product.
     */
    private Map<Product, Double> periodicDemandAmount;

    /**
     * TODO
     */
    private Map<Product, Integer> salesFigures;

    /**
     * The game population. Probably not necessary and also not defined
     * in the original report, even though the demand function depends on this.
     */
    private int gamePopulation;

    private CustomerDemand() {
        this.productCustomerSatisfactionOverallAppeal = new ConcurrentHashMap<>();
        this.overallAppealDemand = new ConcurrentHashMap<>();
        this.demandPercentage = new ConcurrentHashMap<>();
        this.demandAmount = new ConcurrentHashMap<>();
        this.daysSinceLaunchDate = new ConcurrentHashMap<>();
        this.periodicDemandAmount = new ConcurrentHashMap<>();
        this.salesFigures = new ConcurrentHashMap<>();
        this.gamePopulation = 100000;
    }

    public static synchronized CustomerDemand getInstance() {
        if(CustomerDemand.instance == null) {
            CustomerDemand.instance = new CustomerDemand();
        }
        return CustomerDemand.instance;
    }

    /**
     * This method allows to ignore the singleton and operate on an independent instance.
     * @return Return a fresh instance.
     */
    public static CustomerDemand createInstance() {
        return new CustomerDemand();
    }

    private Map<Product, Double> calculateProductOverallAppeal(LocalDate gameDate) {
        Map<Product, Double> productOverallAppeal = new ConcurrentHashMap<>();
        for(Map.Entry<Product, Double> entry : CustomerSatisfaction.getInstance().calculateOverallAppeal(gameDate).entrySet()) {
            productOverallAppeal.put(entry.getKey(), entry.getValue() * (0.4 * CustomerSatisfaction.getInstance().calculateCustomerSatisfaction(gameDate) + 0.8));
        }
        this.productCustomerSatisfactionOverallAppeal = productOverallAppeal;
        return this.productCustomerSatisfactionOverallAppeal;
    }

    /**
     *
     * @param totalSalesQualityOfWork The quality of work of the {@link de.uni.mannheim.capitalismx.hr.domain.employee.impl.SalesPerson}. See in {@link de.uni.mannheim.capitalismx.hr.department.HRDepartment}.
     * @param gameDate The current date of the GameState.
     * @return Returns the overall appeal demand for each product.
     */
    public Map<Product, Double> calculateOverallAppealDemand(double totalSalesQualityOfWork, LocalDate gameDate) {
        Map<Product, Double> overallAppealDemand = new ConcurrentHashMap<>();
        Map<Product, Double> productOverallAppeal = this.calculateProductOverallAppeal(gameDate);
        for(Map.Entry<Product, Double> entry : productOverallAppeal.entrySet()) {
            overallAppealDemand.put(entry.getKey(), entry.getValue() * (0.9 + 0.2 * Math.pow(Math.E, Math.log(totalSalesQualityOfWork)/10)));
        }
        this.overallAppealDemand = overallAppealDemand;
        return this.overallAppealDemand;
    }

    /**
     *
     * @param totalSalesQualityOfWork The quality of work of the {@link de.uni.mannheim.capitalismx.hr.domain.employee.impl.SalesPerson}. See in {@link de.uni.mannheim.capitalismx.hr.department.HRDepartment}.
     * @param gameDate The current date of the GameState.
     * @return Returns the demand percentage for each product.
     */
    public Map<Product, Double> calculateDemandPercentage(double totalSalesQualityOfWork, LocalDate gameDate) {
        this.calculateOverallAppealDemand(totalSalesQualityOfWork, gameDate);
        Map<Product, Double> demandPercentage = new ConcurrentHashMap<>();
        for(Map.Entry<Product, Double> entry : this.overallAppealDemand.entrySet()) {
            demandPercentage.put(entry.getKey(), Math.tanh(entry.getValue() / 2));
        }
        this.demandPercentage = demandPercentage;
        return this.demandPercentage;
    }

    /**
     *
     * @param totalSalesQualityOfWork The quality of work of the {@link de.uni.mannheim.capitalismx.hr.domain.employee.impl.SalesPerson}. See in {@link de.uni.mannheim.capitalismx.hr.department.HRDepartment}.
     * @param gameDate The current date of the GameState.
     * @return Returns the overall appeal demand for each product.
     */
    private Map<Product, Double> calculateDemandAmount(double totalSalesQualityOfWork, LocalDate gameDate) {
        Map<Product, Double> demandAmount = new ConcurrentHashMap<>();
        Map<Product, Double> demandPercentage = this.calculateDemandPercentage(totalSalesQualityOfWork, gameDate);
        for(Map.Entry<Product, Double> entry : demandPercentage.entrySet()) {
            demandAmount.put(entry.getKey(), entry.getValue() * this.gamePopulation);
        }
        this.demandAmount = demandAmount;
        return this.demandAmount;
    }

    private Map<Product, Integer> calculateDaysSinceLaunchDate(LocalDate gameDate) {
        Map<Product, Integer> daysSinceLaunchDate = new ConcurrentHashMap<>();
        /* TODO list of products are the list of our own products, we have to change this if we introduce competing products */
        for(Product product : CustomerSatisfaction.getInstance().getProducts()) {
            daysSinceLaunchDate.put(product, Period.between(product.getLaunchDate(), gameDate).getDays());
        }
        this.daysSinceLaunchDate = daysSinceLaunchDate;
        return this.daysSinceLaunchDate;
    }

    private Map<Product, Double> calculatePeriodicDemandAmount(double totalSalesQualityOfWork, LocalDate gameDate) {
        this.calculateDaysSinceLaunchDate(gameDate);
        Map<Product, Double> periodicDemandAmount = new ConcurrentHashMap<>();
        Map<Product, Double> demandAmount = calculateDemandAmount(totalSalesQualityOfWork, gameDate);
        for(Map.Entry<Product, Double> entry : demandAmount.entrySet()) {
            periodicDemandAmount.put(entry.getKey(), entry.getValue() * 0.002 * ((Math.tanh(0.01 * this.daysSinceLaunchDate.get(entry.getKey())-3) / 2) + 0.5));
        }
        this.periodicDemandAmount = periodicDemandAmount;
        return this.periodicDemandAmount;
    }

    /*
     TODO use inventory of warehouse for calculation not amount of products produced on that day
    public Map<Product, Integer> calculateSalesFigures(double totalSalesQualityOfWork, LocalDate gameDate) {
        Map<Product, Integer> salesFigures = new ConcurrentHashMap<>();
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
    }*/

    public void calculateAll(double totalSalesQualityOfWork, LocalDate gameDate) {
        this.calculatePeriodicDemandAmount(totalSalesQualityOfWork, gameDate);
    }

    public static void setInstance(CustomerDemand instance) {
        CustomerDemand.instance = instance;
    }

    /**
     * Requires this class to be refreshed and also the {@link CustomerSatisfaction}.
     * @return Returns the demand percentage for each product.
     */
    public Map<Product, Double> getDemandPercentage() {
        return demandPercentage;
    }
}
