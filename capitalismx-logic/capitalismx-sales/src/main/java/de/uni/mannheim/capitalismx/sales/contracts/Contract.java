package de.uni.mannheim.capitalismx.sales.contracts;

import de.uni.mannheim.capitalismx.production.Product;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * A contract to a contractor.
 * Accepting the contracts means the player must produce the number of specified product in the given timeToFinish.
 * The variable timeToFinish defines the months.
 * The player then gets the price per product specified in the contract.
 * Otherwise he needs to pay the penalty.
 * @author duly
 *
 * @since 1.0.0
 *
 */
public class Contract implements Serializable {

    private String contractor;

    private int numProducts;

    private double pricePerProd;

    private double penalty;

    private int timeToFinish;

    private Product product;

    private LocalDate contractStart;


    public Contract(String contractor, LocalDate contractStart, Product product, int numProducts, double pricePerProd, int timeToFinish, double penalty) {
        this.contractor = contractor;
        this.product = product;
        this.numProducts = numProducts;
        this.pricePerProd = pricePerProd;
        this.timeToFinish = timeToFinish;
        this.penalty = penalty;
        this.contractStart = contractStart;
    }


    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public int getNumProducts() {
        return numProducts;
    }

    public void setNumProducts(int numProducts) {
        this.numProducts = numProducts;
    }

    public double getPricePerProd() {
        return pricePerProd;
    }

    public void setPricePerProd(int pricePerProd) {
        this.pricePerProd = pricePerProd;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getTimeToFinish() {
        return timeToFinish;
    }

    public void setTimeToFinish(int timeToFinish) {
        this.timeToFinish = timeToFinish;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getContractStart() {
        return contractStart;
    }

    public void setContractStart(LocalDate contractStart) {
        this.contractStart = contractStart;
    }

    /**
     *
     * @return Returns potential revenue when fulfilling the contract.
     */
    public double getRevenue() {
        return numProducts * pricePerProd;
    }
}
