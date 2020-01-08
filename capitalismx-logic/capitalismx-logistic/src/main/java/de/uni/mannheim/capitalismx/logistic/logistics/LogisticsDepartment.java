package de.uni.mannheim.capitalismx.logistic.logistics;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.domain.exception.InconsistentLevelException;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.*;

/**
 * @author sdupper
 */
public class LogisticsDepartment extends DepartmentImpl {
    private static LogisticsDepartment instance;

    private static final Logger logger = LoggerFactory.getLogger(LogisticsDepartment.class);

    //private InternalFleet internalFleet;
    private ExternalPartner externalPartner;
    private double shippingFee;
    private double costLogistics;
    private double logisticsIndex;
    private double totalDeliveryCosts;
    private double costsExternalDelivery;
    private double totalLogisticsCosts;
    //delivered products per day
    private int deliveredProducts;

    /**
     * Internal (Truck) Fleet capacity. You can not buy more trucks than this capacity.
     * The player can increase this value by leveling up this department.
     */
    private int logisticsCapacity;

    /**
     * The initial value of the logistics Capacity.
     */
    private int initialLogisticsCapacity;


    private static final String LEVELING_PROPERTIES = "logistics-leveling-definition";
    private static final String MAX_LEVEL_PROPERTY = "logistics.department.max.level";
    private static final String INITIAL_CAPACITY_PROPERTY = "logistics.department.init.capacity";

    private static final String SKILL_COST_PROPERTY_PREFIX = "logistics.skill.cost.";
    private static final String SKILL_CAPACITY_PREFIX = "logistics.skill.capacity.";

    private LogisticsDepartment(){
        super("Logistics");
        //this.internalFleet = new InternalFleet();
        this.shippingFee = 15;
        this.deliveredProducts = 0;
        this.calculateAll(LocalDate.now());
    }

    public static synchronized LogisticsDepartment getInstance() {
        if(LogisticsDepartment.instance == null) {
            LogisticsDepartment.instance = new LogisticsDepartment();
        }
        return LogisticsDepartment.instance;
    }

    /**
     * Init default values from properties.
     */
    private void initProperties() {
        setMaxLevel(Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(MAX_LEVEL_PROPERTY)));
        this.initialLogisticsCapacity = Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(INITIAL_CAPACITY_PROPERTY));
        this.logisticsCapacity = initialLogisticsCapacity;
    }

    /**
     * Initialize Logistics Skills.
     */
    private void initSkills() {
        Map<Integer, Double> costMap = initCostMap();
        try {
            setLevelingMechanism(new LevelingMechanism(this, costMap));
        } catch (InconsistentLevelException e) {
            String error = "The costMap size " + costMap.size() +  " does not match the maximum level " + this.getMaxLevel() + " of this department!";
            logger.error(error, e);
        }

        ResourceBundle skillBundle = ResourceBundle.getBundle(LEVELING_PROPERTIES);
        for (int i=1; i <= getMaxLevel(); i++) {
            int fleetCapacity = Integer.parseInt(skillBundle.getString(SKILL_CAPACITY_PREFIX + i));
            skillMap.put(i, new LogisticsSkill(i, fleetCapacity));
        }
    }

    /**
     * Initializes the cost map. This is used for the {@link LevelingMechanism}.
     */
    private Map<Integer, Double> initCostMap() {
        // init cost map
        /* TODO BALANCING NEEDED*/
        Map<Integer, Double> costMap = new HashMap<>();

        ResourceBundle bundle = ResourceBundle.getBundle(LEVELING_PROPERTIES);
        for(int i = 1; i <= getMaxLevel(); i++) {
            double cost = Integer.parseInt(bundle.getString(SKILL_COST_PROPERTY_PREFIX + i));
            costMap.put(i, cost);
        }

        return costMap;
    }

    /**
     * Calculates the current capacity and updates the variable.
     */
    private void updateLogisticsCapacity() {
        int newCapacity = this.initialLogisticsCapacity;
        List<DepartmentSkill> availableSkills = getAvailableSkills();

        for(DepartmentSkill skill : availableSkills) {
            newCapacity += ((LogisticsSkill) skill).getNewFleetCapacity();
        }

        this.logisticsCapacity = newCapacity;
    }

    @Override
    public void setLevel(int level) {
        super.setLevel(level);
        updateLogisticsCapacity();
    }

    public void calculateAll(LocalDate gameDate){
        this.calculateCostsLogistics();
        this.calculateLogisticsIndex();
        this.calculateTotalDeliveryCosts();
        this.calculateTotalLogisticsCosts(gameDate);
    }

    private double calculateCostsLogistics(){
        if(externalPartner == null){
            this.costLogistics = InternalFleet.getInstance().getTotalTruckCost();
        }else{
            this.costLogistics = InternalFleet.getInstance().getTotalTruckCost() + externalPartner.getContractualCost();
        }
        return this.costLogistics;
    }

    //TODO what if no external partner hired
    //TODO set deliveredProducts
    private double calculateLogisticsIndex(){
        if(externalPartner == null){
            return this.logisticsIndex = -1.0;
        }
        if(this.deliveredProducts > InternalFleet.getInstance().getCapacityFleet()){
            this.logisticsIndex = (((this.deliveredProducts - InternalFleet.getInstance().getCapacityFleet()) * externalPartner.getExternalLogisticsIndex())
                    + (InternalFleet.getInstance().getCapacityFleet() * InternalFleet.getInstance().getInternalLogisticIndex()));
        }else if(this.deliveredProducts == InternalFleet.getInstance().getCapacityFleet()){
            this.logisticsIndex = InternalFleet.getInstance().getCapacityFleet() * InternalFleet.getInstance().getInternalLogisticIndex();
        } else{
            this.logisticsIndex = this.deliveredProducts * InternalFleet.getInstance().getInternalLogisticIndex();
        }
        return this.logisticsIndex;
    }

    private double calculateTotalDeliveryCosts(){
        if(this.deliveredProducts <= InternalFleet.getInstance().getCapacityFleet()){
            this.totalDeliveryCosts = (Math.ceil(this.deliveredProducts / 1000.0) * InternalFleet.getInstance().getFixCostsDelivery())
                    + (this.deliveredProducts * InternalFleet.getInstance().getVariableCostsDelivery());
        } else{
            //TODO
            this.totalDeliveryCosts = (InternalFleet.getInstance().getTrucks().size() * InternalFleet.getInstance().getFixCostsDelivery())
                    + (InternalFleet.getInstance().getTrucks().size() * 1000 * InternalFleet.getInstance().getVariableCostsDelivery()) + this.calculateCostsExternalDelivery();
        }
        return this.totalDeliveryCosts;
    }

    //TODO possibility to select logistics approach (e.g., only external partner, although internal fleet exists)
    private double calculateCostsExternalDelivery(){
        if(externalPartner != null){
            this.costsExternalDelivery = (this.deliveredProducts - InternalFleet.getInstance().getCapacityFleet()) * externalPartner.getVariableDeliveryCost();
        }else{
            this.costsExternalDelivery = (this.deliveredProducts - InternalFleet.getInstance().getCapacityFleet()) * this.shippingFee;
        }
        return this.costsExternalDelivery;
    }

    private double calculateTotalLogisticsCosts(LocalDate gameDate){
        this.totalLogisticsCosts = (this.calculateCostsLogistics() / gameDate.lengthOfMonth()) + this.calculateTotalDeliveryCosts();
        return this.totalLogisticsCosts;
    }

    /**
     * generate external partners to choose from
     */
    public ArrayList<ExternalPartner> generateExternalPartnerSelection(){
        ArrayList<ExternalPartner> externalPartnerSelection = new ArrayList<ExternalPartner>();

        // generate external partners
        externalPartnerSelection.add(new ExternalPartner("Partner 1", RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(60, 100),
                RandomNumberGenerator.getRandomDouble(1.1, 1.3), RandomNumberGenerator.getRandomDouble(0.7, 0.9)));
        externalPartnerSelection.add(new ExternalPartner("Partner 2", RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(30, 70),
                RandomNumberGenerator.getRandomDouble(1.0, 1.2), RandomNumberGenerator.getRandomDouble(0.8, 1.0)));
        externalPartnerSelection.add(new ExternalPartner("Partner 3", RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(60, 100),
                RandomNumberGenerator.getRandomDouble(1.0, 1.2), RandomNumberGenerator.getRandomDouble(0.8, 1.0)));

        externalPartnerSelection.add(new ExternalPartner("Partner 4", RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(0, 40),
                RandomNumberGenerator.getRandomDouble(0.9, 1.1), RandomNumberGenerator.getRandomDouble(0.9, 1.1)));
        externalPartnerSelection.add(new ExternalPartner("Partner 5", RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(30, 70),
                RandomNumberGenerator.getRandomDouble(0.9, 1.1), RandomNumberGenerator.getRandomDouble(0.9, 1.1)));
        externalPartnerSelection.add(new ExternalPartner("Partner 6", RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(60, 100),
                RandomNumberGenerator.getRandomDouble(0.9, 1.1), RandomNumberGenerator.getRandomDouble(0.9, 1.1)));

        externalPartnerSelection.add(new ExternalPartner("Partner 7", RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(30, 70),
                RandomNumberGenerator.getRandomDouble(0.9, 1.1), RandomNumberGenerator.getRandomDouble(0.9, 1.1)));
        externalPartnerSelection.add(new ExternalPartner("Partner 8", RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(30, 70),
                RandomNumberGenerator.getRandomDouble(0.8, 1.0), RandomNumberGenerator.getRandomDouble(1.0, 1.2)));
        externalPartnerSelection.add(new ExternalPartner("Partner 9", RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(0, 40),
                RandomNumberGenerator.getRandomDouble(0.7, 0.9), RandomNumberGenerator.getRandomDouble(1.1, 1.3)));

        return externalPartnerSelection;
    }

    /**
     * generate six trucks to choose from
     */
    public ArrayList<Truck> generateTruckSelection(){
        ArrayList<Truck> truckSelection = new ArrayList<Truck>();

        // generate six trucks according to table on page 49
        truckSelection.add(new Truck("Truck 1", RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(1.1, 1.3),
                RandomNumberGenerator.getRandomDouble(0.6, 0.8)));
        truckSelection.add(new Truck("Truck 2", RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(0.9, 1.1),
                RandomNumberGenerator.getRandomDouble(0.8, 1.1)));
        truckSelection.add(new Truck("Truck 3", RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(1.0, 1.2),
                RandomNumberGenerator.getRandomDouble(0.7, 0.9)));
        truckSelection.add(new Truck("Truck 4", RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(0.8, 1.1),
                RandomNumberGenerator.getRandomDouble(0.9, 1.1)));
        truckSelection.add(new Truck("Truck 5", RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(0.7, 0.9),
                RandomNumberGenerator.getRandomDouble(1.0, 1.2)));
        truckSelection.add(new Truck("Truck 6", RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(0.6, 0.8),
                RandomNumberGenerator.getRandomDouble(1.1, 1.3)));
        return truckSelection;
    }

    public void setDeliveredProducts(int deliveredProducts){
        this.deliveredProducts = deliveredProducts;
    }

    public void addExternalPartner(ExternalPartner externalPartner){
        this.externalPartner = externalPartner;
        //this.calculateAll();
    }

    public void removeExternalPartner(){
        this.externalPartner = null;
        //this.calculateAll();
    }

    public void addTruckToFleet(Truck truck, LocalDate gameDate){
        InternalFleet.getInstance().addTruckToFleet(truck, gameDate);
        //this.calculateAll();
    }

    public void removeTruckFromFleet(Truck truck){
        InternalFleet.getInstance().removeTruckFromFleet(truck);
        //this.calculateAll();
    }

    public InternalFleet getInternalFleet() {
        return InternalFleet.getInstance();
    }

    public ExternalPartner getExternalPartner() {
        return this.externalPartner;
    }

    public double getShippingFee() {
        return this.shippingFee;
    }

    public double getCostLogistics() {
        return this.costLogistics;
    }

    public double getLogisticsIndex() {
        return this.logisticsIndex;
    }

    public double getTotalDeliveryCosts() {
        return this.totalDeliveryCosts;
    }

    public double getCostsExternalDelivery() {
        return this.costsExternalDelivery;
    }

    public double getTotalLogisticsCosts(LocalDate gameDate) {
        //return this.totalLogisticsCosts;
        return this.calculateTotalLogisticsCosts(gameDate);
    }

    public int getDeliveredProducts() {
        return this.deliveredProducts;
    }

    public boolean checkEcoIndexFleetBelowThreshold(){
        return InternalFleet.getInstance().checkEcoIndexFleetBelowThreshold();
    }

    public void decreaseCapacityFleetRel(double amount){
        InternalFleet.getInstance().decreaseCapacityFleetRel(amount);
    }

    public static void setInstance(LogisticsDepartment instance) {
        LogisticsDepartment.instance = instance;
    }

    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        throw new UnsupportedOperationException();
    }
}
