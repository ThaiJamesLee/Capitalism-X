package de.uni.mannheim.capitalismx.sales.department;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.domain.exception.InconsistentLevelException;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import de.uni.mannheim.capitalismx.sales.contracts.ContractFactory;
import de.uni.mannheim.capitalismx.sales.skills.SalesDepartmentSkill;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;
import de.uni.mannheim.capitalismx.utils.data.Range;
import de.uni.mannheim.capitalismx.utils.formatter.DataFormatter;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This is the sales department. Leveling up this department will allow the user to get better
 * contracts and prices.
 *
 * @author duly
 */
public class SalesDepartment extends DepartmentImpl {

    /**
     * List of active contracts.
     */
    private PropertyChangeSupportList<Contract> activeContracts;

    /**
     * List of available contracts.
     * The player can choose the contracts on the list.
     */
    private PropertyChangeSupportList<Contract> availableContracts;

    /**
     * List of finish contracts.
     */
    private PropertyChangeSupportList<Contract> doneContracts;

    /**
     * List of failed contracts.
     */
    private PropertyChangeSupportList<Contract> failedContracts;


    private static final Logger LOGGER = LoggerFactory.getLogger(SalesDepartment.class);

    public static final String ACTIVE_CONTRACTS_EVENT = "activeContractListChanged";
    public static final String AVAILABLE_CONTRACTS_EVENT = "availableContractListChanged";
    public static final String DONE_CONTRACTS_EVENT = "doneContractsListChanged";
    public static final String FAILED_CONTRACTS_EVENT = "failedContractsListChanged";

    private static final String SALES_PROPERTY_FILE = "sales-module";
    private static final String MAX_LEVEL_PROPERTY = "sales.department.level.max";
    private static final String NUM_CONTRACTS_PROPERTY_PREFIX = "sales.skill.contracts.number.";
    private static final String PRICE_FACTOR_PROPERTY_PREFIX = "sales.skill.contracts.price.factor.";
    private static final String PENALTY_FACTOR_PROPERTY_PREFIX = "sales.skill.contracts.penalty.";
    private static final String LEVELING_COST_PROPERTY_PREFIX = "sales.skill.cost.";
    private static final String NEW_CONTRACTS_AVAILABLE_MESSAGE_PROPERTY = "sales.message.new.contracts";

    private static SalesDepartment instance;

    private SalesDepartment() {
        super("Sales Department");
        setLevel(1);
        activeContracts = new PropertyChangeSupportList<>();
        availableContracts = new PropertyChangeSupportList<>();
        doneContracts = new PropertyChangeSupportList<>();
        failedContracts = new PropertyChangeSupportList<>();
        init();
    }

    /**
     * Initialize all values.
     */
    private void init() {
        this.activeContracts.setAddPropertyName(ACTIVE_CONTRACTS_EVENT);
        this.activeContracts.setRemovePropertyName(ACTIVE_CONTRACTS_EVENT);

        this.availableContracts.setAddPropertyName(AVAILABLE_CONTRACTS_EVENT);
        this.availableContracts.setRemovePropertyName(AVAILABLE_CONTRACTS_EVENT);

        this.doneContracts.setAddPropertyName(DONE_CONTRACTS_EVENT);
        this.doneContracts.setRemovePropertyName(DONE_CONTRACTS_EVENT);

        this.failedContracts.setRemovePropertyName(FAILED_CONTRACTS_EVENT);
        this.failedContracts.setAddPropertyName(FAILED_CONTRACTS_EVENT);
        initProperties();
    }

    /**
     * Initialize values from properties.
     */
    private void initProperties() {
        ResourceBundle bundle = ResourceBundle.getBundle(SALES_PROPERTY_FILE);
        setMaxLevel(Integer.parseInt(bundle.getString(MAX_LEVEL_PROPERTY)));

        // init skills after properties, since some properties are needed before skill initialization.
        initSkills(bundle);
        initCostMap(bundle);
    }

    private void initCostMap(ResourceBundle bundle) {
        Map<Integer, Double> costMap = new ConcurrentHashMap<>();

        for(int i = 1; i <= getMaxLevel(); i++) {
            double cost = Integer.parseInt(bundle.getString(LEVELING_COST_PROPERTY_PREFIX + i));
            costMap.put(i, cost);
        }
        try {
            this.setLevelingMechanism(new LevelingMechanism(this, costMap));
        } catch (InconsistentLevelException e) {
            LOGGER.error("The number of levels does not match with number of costs!", e);
        }

    }

    /**
     * Initialize the DepartmentSkills.
     */
    private void initSkills(ResourceBundle bundle) {
        for(int i = 1; i <= getMaxLevel(); i++) {
            int numContract = Integer.parseInt(bundle.getString(NUM_CONTRACTS_PROPERTY_PREFIX + i));
            String[] stringRange = bundle.getString(PRICE_FACTOR_PROPERTY_PREFIX + i).split(",");
            Double[] doubleRange = DataFormatter.stringArrayToDoubleArray(stringRange);
            Range priceFactor = new Range(doubleRange[0], doubleRange[1]);

            double penaltyFactor = Double.parseDouble(bundle.getString(PENALTY_FACTOR_PROPERTY_PREFIX + i));

            this.skillMap.put(i, new SalesDepartmentSkill(i, numContract, penaltyFactor,priceFactor));
        }
    }

    public static SalesDepartment getInstance() {
        if(instance == null) {
            instance = new SalesDepartment();
        }
        return instance;
    }

    public static void setInstance(SalesDepartment instance) {
        SalesDepartment.instance = instance;
    }

    /**
     * @return Returns a fresh instance of this department.
     */
    public static SalesDepartment createInstance() {
        return new SalesDepartment();
    }

    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        this.activeContracts.addPropertyChangeListener(listener);
        this.availableContracts.addPropertyChangeListener(listener);
        this.doneContracts.addPropertyChangeListener(listener);
    }

    /**
     *
     * @return Returns all currently active contracts.
     */
    public PropertyChangeSupportList<Contract> getActiveContracts() {
        return activeContracts;
    }

    public void setActiveContracts(PropertyChangeSupportList<Contract> activeContracts) {
        this.activeContracts = activeContracts;
    }

    /**
     *
     * @return Returns all available contracts.
     */
    public PropertyChangeSupportList<Contract> getAvailableContracts() {
        return availableContracts;
    }

    /**
     *
     * @return Returns contracts finished.
     */
    public PropertyChangeSupportList<Contract> getDoneContracts() {
        return doneContracts;
    }

    /**
     * @param contract A new contract to the active contract.
     */
    public void addContractToActive(Contract contract, LocalDate contractStart) {
        contract.setContractStart(contractStart);
        this.activeContracts.add(contract);
        this.availableContracts.remove(contract);
    }

    /**
     * Removes the contract from the active contracts and adds it to the done contracts.
     * @param contract The contract that is finished.
     * @param doneDate The date when the contract was finished.
     */
    public void contractDone(Contract contract, LocalDate doneDate) {
        contract.setContractDoneDate(doneDate);
        this.activeContracts.remove(contract);
        this.doneContracts.add(contract);
    }

    /**
     * Call this, when the player terminates the contract or because he failed to fulfill the contract in time
     * (when the contract is due).
     * @param contract The contract to terminate.
     * @param terminateDate The date the contract was terminated.
     */
    public void terminateContract(Contract contract, LocalDate terminateDate) {
        contract.setTerminateDate(terminateDate);
        this.activeContracts.remove(contract);
        this.failedContracts.add(contract);
    }
    

    /**
     *
     * @param date The date when the contracts are generated.
     * @param productionDepartment The {@link ProductionDepartment} instance.
     */
    public void generateContracts(LocalDate date, ProductionDepartment productionDepartment, Map<Product, Double> demandPercentage) {
        SalesDepartmentSkill skill = (SalesDepartmentSkill)skillMap.get(getLevel());
        int numContracts = skill.getNumContracts();
        Range factor = skill.getPriceFactor();
        double penalty = skill.getPenaltyFactor();

        List<Contract> newContracts = new ArrayList<>();
        List<Product> products = productionDepartment.getLaunchedProductsChange().getList();
        ContractFactory contractFactory = new ContractFactory(productionDepartment);

        if(!products.isEmpty()) {
            for(int i = 0; i<numContracts; i++) {
                int max = Math.max(products.size()-1, 0);
                Product p = products.get(RandomNumberGenerator.getRandomInt(0, max));

                double demand = demandPercentage.get(p) != null ? demandPercentage.get(p) : 0.0;

                if(demand > 0.0) {
                    LOGGER.info("demand bigger zero");
                    Contract c = contractFactory.getContract(p, date, factor);
                    c.setPenalty(c.getPenalty() * penalty);
                    c.setNumProducts((int)(c.getNumProducts() * demand));
                    c.setuId(UUID.randomUUID().toString());
                    newContracts.add(c);
                }
            }
        }
        availableContracts.setList(newContracts);
    }

    /**
     * Show this message as a notification for the player, when there are new contracts.
     * @param locale Supports DE and EN.
     * @return Returns the message, when new contracts are available.
     */
    public String getUpdatedContractsNotification(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(SALES_PROPERTY_FILE, locale);
        return bundle.getString(NEW_CONTRACTS_AVAILABLE_MESSAGE_PROPERTY);
    }

}
