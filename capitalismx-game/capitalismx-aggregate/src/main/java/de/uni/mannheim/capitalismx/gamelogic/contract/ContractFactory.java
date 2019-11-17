package de.uni.mannheim.capitalismx.gamelogic.contract;

import de.uni.mannheim.capitalismx.customer.CustomerDemand;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.production.Machinery;
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
 */
public class ContractFactory {

    private static List<String> contractorList;

    static {
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

    private static double getOfferPrice() {
        CustomerDemand cd = CustomerDemand.getInstance();
        // TODO determine price by customer demand / satisfaction.

        return 0.0;
    }

    /**
     * Creates a contract for the specified product.
     * Sets dates based on the {@link GameState} and the contractor randomly.
     * @param product The contract for the product.
     * @return Returns the contract.
     */
    public static Contract getContract(final Product product) {
        Contract c = null;
        // extract the date from the GameState
        GameState state = GameState.getInstance();
        // the start date
        LocalDate date = state.getGameDate();
        int year = date.getYear();

        String contractor = getRandomContractor();

        int timeToFinish = getRandomPeriod();

        int numProd = (int) (getProductionCapacity() * generateRandomFactor());

        double price = product.getSalesPrice();




        // c = new Contract(contractor, date, product, numProd, );


        /*
        TODO how to decide on amount and price
        - price and amount must be dependent on appeal
        - and also customer satisfaction
        */

        return c;
    }
}
