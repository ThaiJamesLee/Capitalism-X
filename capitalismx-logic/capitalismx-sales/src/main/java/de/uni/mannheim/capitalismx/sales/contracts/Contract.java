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

    /**
     * A unique Id.
     */
    private String uId;

    /**
     * The name of the contractor.
     */
    private String contractor;

    /**
     * The number of products to produce, to fulfill the contract.
     */
    private int numProducts;

    /**
     * The price for each product (wholesale price).
     */
    private double pricePerProd;

    /**
     * The penalty, if the contract is not fulfilled and timeToFinish is due.
     */
    private double penalty;

    /**
     * The time to fulfill the contract from the point of accepting this contract.
     */
    private int timeToFinish;

    /**
     * The product to sell. This influences the price per product.
     */
    private Product product;

    /**
     * The start date of the contract.
     */
    private LocalDate contractStart;

    /**
     * The date when the contract is done.
     */
    private LocalDate contractDone;

    /**
     * The number of products produced for this product.
     */
    private int producedProducts;

    /**
     *
     * @param contractor The name of the contractor.
     * @param contractStart The number of products to produce, to fulfill the contract.
     * @param product The product to sell. This influences the price per product.
     * @param numProducts The number of products to produce, to fulfill the contract.
     * @param pricePerProd The price for each product (wholesale price).
     * @param timeToFinish The time to fulfill the contract from the point of accepting this contract.
     * @param penalty The penalty, if the contract is not fulfilled and timeToFinish is due.
     */
    public Contract(String contractor, LocalDate contractStart, Product product, int numProducts, double pricePerProd, int timeToFinish, double penalty) {
        this.contractor = contractor;
        this.product = product;
        this.numProducts = numProducts;
        this.pricePerProd = pricePerProd;
        this.timeToFinish = timeToFinish;
        this.penalty = penalty;
        this.contractStart = contractStart;
        this.producedProducts = 0;
    }

    /**
     *
     * @return Get the name of the contractor.
     */
    public String getContractor() {
        return contractor;
    }

    /**
     *
     * @param contractor The name of the contractor.
     */
    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    /**
     *
     * @return Get the quantity to produce to fulfill this contract.
     */
    public int getNumProducts() {
        return numProducts;
    }

    /**
     *
     * @param numProducts The quantity to produce to fulfill this contract.
     */
    public void setNumProducts(int numProducts) {
        this.numProducts = numProducts;
    }

    /**
     *
     * @return Returns the wholesale price for each product.
     */
    public double getPricePerProd() {
        return pricePerProd;
    }

    /**
     *
     * @param pricePerProd Sets the wholesale price for each product.
     */
    public void setPricePerProd(int pricePerProd) {
        this.pricePerProd = pricePerProd;
    }

    /**
     *
     * @return Get the amount that the player needs to pay, when not fulfilling this contract.
     */
    public double getPenalty() {
        return penalty;
    }

    /**
     *
     * @param penalty The amount that the player needs to pay, when not fulfilling this contract.
     */
    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    /**
     *
     * @return Returns the months.
     */
    public int getTimeToFinish() {
        return timeToFinish;
    }

    /**
     * The time is specified in months.
     * @param timeToFinish The time to fulfill this contract.
     */
    public void setTimeToFinish(int timeToFinish) {
        this.timeToFinish = timeToFinish;
    }

    /**
     *
     * @return Returns the product of this contract.
     */
    public Product getProduct() {
        return product;
    }

    /**
     *
     * @param product Set the product for this contract.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     *
     * @return Returns the date when the contract started.
     */
    public LocalDate getContractStart() {
        return contractStart;
    }

    /**
     * Call this, when the player accepts this contract.
     * @param contractStart The start date of the contract.
     */
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

    /**
     *
     * @return Returns the date when the contract is done. Returns null if it is not done yet.
     */
    public LocalDate getContractDoneDate() {
        return contractDone;
    }

    /**
     *
     * @param contractDone The date the contract is done.
     */
    public void setContractDoneDate(LocalDate contractDone) {
        this.contractDone = contractDone;
    }

    /**
     *
     * @param currentDate The current game date.
     * @return Returns true if the contract is due (game date > start date + time to finish).
     */
    public boolean contractIsDue(LocalDate currentDate) {
        return contractStart.plusMonths(timeToFinish).isBefore(currentDate);
    }

    /**
     *
     * @param uId Set the UUID.
     */
    public void setuId(String uId) {
        this.uId = uId;
    }

    /**
     *
     * @return Returns the UUID of the contract.
     */
    public String getuId() {
        return uId;
    }

    @Override
    public String toString() {
        return "Contractor: " + contractor + "; product:" + product.toString() + "; numProducts:" + numProducts + "; pricePerProd:" + pricePerProd + "; timeToFinish:" + timeToFinish + "; penalty:" + penalty + "; contractStart:" + contractStart;
    }
}
