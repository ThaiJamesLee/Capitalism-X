package de.uni.mannheim.capitalismx.gamelogic.contract;

import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.production.Machinery;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A Factory to create contracts.
 * @author duly
 *
 */
public class ContractFactory {

    private static List<String> contractorList = new ArrayList<>();

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
     *  Gets the list of machines and compute the total machinery capacity.
     * @return Returns the total capacity of machines.
     */
    private static int getProductionCapacity() {
        ProductionDepartment productionDepartment = ProductionDepartment.getInstance();
        List<Machinery> machines = productionDepartment.getMachines();

        int capacity = 0;

        for(Machinery m : machines) {
            capacity += m.getMachineryCapacity();
        }
        return capacity;
    }

    /**
     *
     * @return Returns the time in months for the contract to be fulfilled.
     */
    private int getRandomPeriod() {
        return RandomNumberGenerator.getRandomInt(1, 12);
    }

    /**
     * Creates a contract for the specified product.
     * @param p The contract for the product.
     * @return Returns the contract.
     */
    public static Contract getContract(Product p) {
        Contract c = null;

        // extract the date from the GameState
        GameState state = GameState.getInstance();

        // the start date
        LocalDate date = state.getGameDate();

        int year = date.getYear();

        String contractor = getRandomContractor();


        /*
        TODO how to decide on amount and price
        - price and amount must be dependent on appeal
        - and also customer satisfaction
        */

        return c;
    }
}
