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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesDepartment.class);

    public static final String ACTIVE_CONTRACTS_EVENT = "activeContractListChanged";
    public static final String AVAILABLE_CONTRACTS_EVENT = "availableContractListChanged";

    private static final String SALES_PROPERTY_FILE = "sales-module";
    private static final String MAX_LEVEL_PROPERTY = "sales.department.level.max";
    private static final String NUM_CONTRACTS_PROPERTY_PREFIX = "sales.skill.contracts.number.";
    private static final String PRICE_FACTOR_PROPERTY_PREFIX = "sales.skill.contracts.price.factor.";
    private static final String LEVELING_COST_PROPERTY_PREFIX = "sales.skill.cost.";

    private static SalesDepartment instance;

    private SalesDepartment() {
        super("Sales Department");
        activeContracts = new PropertyChangeSupportList<>();
        availableContracts = new PropertyChangeSupportList<>();
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
            this.skillMap.put(i, new SalesDepartmentSkill(i, numContract, priceFactor));
        }
    }

    public static SalesDepartment getInstance() {
        if(instance == null) {
            instance = new SalesDepartment();
        }
        return instance;
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
    }

    public PropertyChangeSupportList<Contract> getActiveContracts() {
        return activeContracts;
    }

    public void setActiveContracts(PropertyChangeSupportList<Contract> activeContracts) {
        this.activeContracts = activeContracts;
    }

    public PropertyChangeSupportList<Contract> getAvailableContracts() {
        return availableContracts;
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
     *
     * @param date The date when the contracts are generated.
     * @param productionDepartment The {@link ProductionDepartment} instance.
     */
    public void generateContracts(LocalDate date, ProductionDepartment productionDepartment) {
        SalesDepartmentSkill skill = (SalesDepartmentSkill)skillMap.get(getLevel());
        int numContracts = skill.getNumContracts();
        Range factor = skill.getPriceFactor();

        numContracts = (int)(numContracts * RandomNumberGenerator.getRandomDouble(factor.getLowerBound(), factor.getUpperBound()));
        List<Contract> newContracts = new ArrayList<>();

        List<Product> products = productionDepartment.getLaunchedProducts();

        for(int i = 0; i<numContracts; i++) {
            Product p = products.get(RandomNumberGenerator.getRandomInt(0, products.size()-1));
            newContracts.add(ContractFactory.getContract(p, date));
        }
        availableContracts.setList(newContracts);
    }

}
