package de.uni.mannheim.capitalismx.sales.contracts;

import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
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

    private static final List<String> contractorList;

    static {
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

    private ContractFactory() {}

    /**
     *
     * @return Returns the pre-defined contractor list.
     */
    public static List<String> getContractorList() {
        return contractorList;
    }

    /**
     *
     * @return Returns a random contractor.
     */
    private static String getRandomContractor() {
        int index = RandomNumberGenerator.getRandomInt(0, contractorList.size());
        return contractorList.get(index);
    }

    /**
     *
     * @return Returns the total capacity of machines.
     */
    private static int getProductionCapacity() {
        ProductionDepartment productionDepartment = ProductionDepartment.getInstance();

        return  (int) productionDepartment.getMonthlyAvailableMachineCapacity();
    }

    /**
     * The period is currently between 1 and 12 months.
     * @return Returns the time in months for the contract to be fulfilled.
     */
    private static int getRandomPeriod() {
        return RandomNumberGenerator.getRandomInt(1, 12);
    }

    /**
     *
     * @return Returns true or false with approx. equal probability.
     */
    private static boolean isPositive() {
        return new SecureRandom().nextBoolean();
    }

    /**
     *
     * @return A random factor with equal probability to be in interval [0.0 - 1.0] or [1.0 - 2.0]
     */
    private static double generateRandomFactor() {
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
    public static Contract getContract(final Product product, final LocalDate date) {
        Contract c = null;

        String contractor = getRandomContractor();

        int timeToFinish = getRandomPeriod();

        int numProd = (int) (getProductionCapacity() * generateRandomFactor());

        // double price = product.getSalesPrice();
        double productProductionCost = product.getProductCosts(date);

        double pricePerProd = productProductionCost  * generateRandomFactor();

        double penalty = numProd * pricePerProd;

        c = new Contract(contractor, null, product, numProd, pricePerProd, timeToFinish, penalty);

        return c;
    }
}
