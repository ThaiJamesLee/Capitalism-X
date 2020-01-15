package de.uni.mannheim.capitalismx.sales.contracts;

import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import de.uni.mannheim.capitalismx.utils.data.Range;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A Factory to create contracts.
 * @author duly
 *
 * @since 1.0.0
 *
 */
public class ContractFactory {

    private List<String> contractorList;


    private ProductionDepartment productionDepartment;

    public ContractFactory(ProductionDepartment productionDepartment) {
        this.productionDepartment = productionDepartment;
        init();
    }

    private void init() {
        contractorList = new ArrayList<>();
        contractorList.add("Metro");
        contractorList.add("Mediamarkt");
        contractorList.add("Saturn");
        contractorList.add("Amazon");
        contractorList.add("GearBest");
        contractorList.add("NewEgg");
        contractorList.add("BestBuy");
        contractorList.add("Walmart");
        contractorList.add("expert");
        contractorList.add("Mindfactory");
        contractorList.add("Cyberport");
    }

    public void setProductionDepartment(ProductionDepartment productionDepartment) {
        this.productionDepartment = productionDepartment;
    }

    public ProductionDepartment getProductionDepartment() {
        return productionDepartment;
    }

    /**
     *
     * @return Returns the pre-defined contractor list.
     */
    public List<String> getContractorList() {
        return contractorList;
    }

    /**
     *
     * @return Returns a random contractor.
     */
    private String getRandomContractor() {
        int index = RandomNumberGenerator.getRandomInt(0, contractorList.size()-1);
        return contractorList.get(index);
    }

    /**
     * @return Returns the total capacity of machines.
     */
    private int getProductionCapacity() {
        return  (int) productionDepartment.getMonthlyAvailableMachineCapacity();
    }

    /**
     * The period is currently between 1 and 12 months.
     * @return Returns the time in months for the contract to be fulfilled.
     */
    private int getRandomPeriod() {
        return RandomNumberGenerator.getRandomInt(1, 12);
    }

    /**
     *
     * @return Returns true or false with approx. equal probability.
     */
    private boolean isPositive() {
        return new SecureRandom().nextBoolean();
    }

    /**
     *
     * @return A random factor with equal probability to be in interval [0.0 - 1.0] or [1.0 - 2.0]
     */
    private double generateRandomFactor() {
        double randomFactor = new SecureRandom().nextDouble();
        if(isPositive()) {
            randomFactor += 1;
        } else {
            randomFactor = 1 - randomFactor;
        }
        return randomFactor;
    }


    /**
     * Creates a contract for the specified product.
     * @param product The contract for the product.
     * @param date The current date of the GameState.
     * @return Returns the contract.
     */
    public Contract getContract(final Product product, final LocalDate date, final Range range) {
        Contract c = null;

        String contractor = getRandomContractor();

        int timeToFinish = getRandomPeriod();

        int numProd = (int) (getProductionCapacity() * generateRandomFactor());

        double productProductionCost = product.getProductCosts(date);

        double pricePerProd = productProductionCost  * getFactor(range);

        // default
        double penalty = numProd * pricePerProd;

        c = new Contract(contractor, null, product, numProd, pricePerProd, timeToFinish, penalty);

        return c;
    }

    /**
     *
     * @param range The {@link Range}.
     * @return Returns a random value between the lower bound and the upper bound.
     */
    private double getFactor(Range range) {
        return RandomNumberGenerator.getRandomDouble(range.getLowerBound(), range.getUpperBound());
    }
}
