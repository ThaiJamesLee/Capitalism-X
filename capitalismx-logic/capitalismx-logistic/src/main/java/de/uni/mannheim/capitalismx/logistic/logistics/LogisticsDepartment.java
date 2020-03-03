package de.uni.mannheim.capitalismx.logistic.logistics;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.domain.exception.InconsistentLevelException;
import de.uni.mannheim.capitalismx.domain.exception.LevelingRequirementNotFulFilledException;
import de.uni.mannheim.capitalismx.logistic.logistics.exception.NotEnoughTruckCapacityException;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.*;

/**
 * This class represents the logistics department.
 * Based on the report p.48-54
 *
 * @author sdupper
 */
public class LogisticsDepartment extends DepartmentImpl {

    /**
     * The singleton pointer.
     */
    private static LogisticsDepartment instance;

    /**
     * The external logistics partner of the company.
     */
    private ExternalPartner externalPartner;

    /**
     * The number of delivered products per day.
     */
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

    /**
     * The costs for shipping by post.
     */
    private double shippingFee;

    /**
     * A list of external partners to choose from.
     */
    private ArrayList<ExternalPartner> externalPartnerSelection;

    private static final Logger logger = LoggerFactory.getLogger(LogisticsDepartment.class);
    private double costLogistics;
    private double logisticsIndex;
    private double totalDeliveryCosts;
    private double costsExternalDelivery;
    private double totalLogisticsCosts;
    private int truckCapacity;

    private static final String LANGUAGE_PROPERTIES_FILE = "logistics-module";
    private static final String DEFAULTS_PROPERTIES_FILE = "logistics-defaults";

    private static final String LEVELING_PROPERTIES = "logistics-leveling-definition";
    private static final String MAX_LEVEL_PROPERTY = "logistics.department.max.level";
    private static final String INITIAL_CAPACITY_PROPERTY = "logistics.department.init.capacity";

    private static final String SKILL_COST_PROPERTY_PREFIX = "logistics.skill.cost.";
    private static final String SKILL_CAPACITY_PREFIX = "logistics.skill.capacity.";

    /**
     * Constructor
     * Initializes the variables and calculates all relevant values of the logistics department.
     */
    private LogisticsDepartment(){
        super("Logistics");

        this.initProperties();
        this.initSkills();

        this.deliveredProducts = 0;
        this.calculateAll(LocalDate.now());
    }

    /**
     *
     * @return Returns the singleton instance
     */
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

        ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULTS_PROPERTIES_FILE);
        this.shippingFee = Double.valueOf(resourceBundle.getString("logistics.department.shipping.fee"));
        this.truckCapacity = Integer.valueOf(resourceBundle.getString("logistics.truck.capacity"));
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
    public void setLevel(int level) throws LevelingRequirementNotFulFilledException {
        super.setLevel(level);
        updateLogisticsCapacity();
    }

    /**
     * Calculates different values of the logistics department, e.g., the total logistics costs.
     */
    public void calculateAll(LocalDate gameDate){
        this.calculateCostsLogistics();
        this.calculateLogisticsIndex();
        this.calculateTotalDeliveryCosts();
        this.calculateTotalLogisticsCosts(gameDate);
    }

    /**
     * Calculates the costs for logistics based on the total truck costs and the contractual costs of the external
     * partner according to p.52.
     * @return Returns the costs for logistics.
     */
    protected double calculateCostsLogistics(){
        if(externalPartner == null){
            this.costLogistics = InternalFleet.getInstance().getTotalTruckCost();
        }else{
            this.costLogistics = InternalFleet.getInstance().getTotalTruckCost() + externalPartner.getContractualCost();
        }
        return this.costLogistics;
    }

    //TODO what if no external partner hired
    //TODO set deliveredProducts

    /**
     * Calculates the logistics index according to p.52. The calculation differs based on the number of delivered
     * products and the capacity of the internal fleet. The external logistics partner is only used for deliveries if
     * the internal fleet does not have enough capacity.
     * @return Returns the logistics index of the company.
     */
    public double calculateLogisticsIndex(){
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

    /**
     * Calculates the total delivery costs according to p.50. The calculation differs based on the number of delivered
     * products and the capacity of the internal fleet. The external logistics partner is only used for deliveries if
     * the internal fleet does not have enough capacity. If no external partner was hired, the remaining products
     * are sent by post.
     * @return Returns the total delivery costs.
     */
    protected double calculateTotalDeliveryCosts(){
        if(this.deliveredProducts <= InternalFleet.getInstance().getCapacityFleet()){
            this.totalDeliveryCosts = (Math.ceil(this.deliveredProducts / Double.valueOf(this.truckCapacity)) * InternalFleet.getInstance().getFixCostsDelivery())
                    + (this.deliveredProducts * InternalFleet.getInstance().getVariableCostsDelivery());
        } else{
            //TODO
            this.totalDeliveryCosts = (InternalFleet.getInstance().getTrucks().size() * InternalFleet.getInstance().getFixCostsDelivery())
                    + (InternalFleet.getInstance().getTrucks().size() * Double.valueOf(this.truckCapacity) * InternalFleet.getInstance().getVariableCostsDelivery()) + this.calculateCostsExternalDelivery();
        }
        return this.totalDeliveryCosts;
    }

    /**
     * Calculates the costs for external deliveries according to p.52. If an external logistics partner is hired, the
     * products are delivered by the partner. Otherwise, the products are sent by post.
     * @return Returns the costs for external deliveries.
     */
    private double calculateCostsExternalDelivery(){
        if(externalPartner != null){
            this.costsExternalDelivery = (this.deliveredProducts - InternalFleet.getInstance().getCapacityFleet()) * externalPartner.getVariableDeliveryCost();
        }else{
            this.costsExternalDelivery = (this.deliveredProducts - InternalFleet.getInstance().getCapacityFleet()) * this.shippingFee;
        }
        return this.costsExternalDelivery;
    }

    /**
     * Calculates the total (daily) logistics costs based on the costs for logistics and the total delivery costs
     * according to p.53.
     * @param gameDate The current date in the game.
     * @return Returns the total (daily) logistics costs.
     */
    private double calculateTotalLogisticsCosts(LocalDate gameDate){
        this.totalLogisticsCosts = (this.calculateCostsLogistics() / gameDate.lengthOfMonth()) + this.calculateTotalDeliveryCosts();
        return this.totalLogisticsCosts;
    }

    /**
     * Generates a selection of external logistics partners with different characteristics according to p.52.
     * @return Returns a list of 9 different external logistics partners.
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

        this.externalPartnerSelection = externalPartnerSelection;
        return this.externalPartnerSelection;
    }

    /**
     * Generates a selection of trucks with different characteristics according to p.49.
     * @param locale The Locale object of the desired language.
     * @return Returns a list of 6 different trucks.
     */
    public ArrayList<Truck> generateTruckSelection(Locale locale){
        ArrayList<Truck> truckSelection = new ArrayList<Truck>();

        // generate six trucks according to table on page 49
        truckSelection.add(new Truck(this.getLocalisedString("truck", locale) + " 1", RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(1.1, 1.3),
                RandomNumberGenerator.getRandomDouble(0.6, 0.8)));
        truckSelection.add(new Truck(this.getLocalisedString("truck", locale) + " 2", RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(0.9, 1.1),
                RandomNumberGenerator.getRandomDouble(0.8, 1.1)));
        truckSelection.add(new Truck(this.getLocalisedString("truck", locale) + " 3", RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(60, 100), RandomNumberGenerator.getRandomDouble(1.0, 1.2),
                RandomNumberGenerator.getRandomDouble(0.7, 0.9)));
        truckSelection.add(new Truck(this.getLocalisedString("truck", locale) + " 4", RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(0.8, 1.1),
                RandomNumberGenerator.getRandomDouble(0.9, 1.1)));
        truckSelection.add(new Truck(this.getLocalisedString("truck", locale) + " 5", RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(30, 70), RandomNumberGenerator.getRandomDouble(0.7, 0.9),
                RandomNumberGenerator.getRandomDouble(1.0, 1.2)));
        truckSelection.add(new Truck(this.getLocalisedString("truck", locale) + " 6", RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(0, 40), RandomNumberGenerator.getRandomDouble(0.6, 0.8),
                RandomNumberGenerator.getRandomDouble(1.1, 1.3)));
        return truckSelection;
    }

    /**
     * Sets the number of delivered products.
     * @param deliveredProducts The number of delivered products.
     */
    public void setDeliveredProducts(int deliveredProducts){
        this.deliveredProducts = deliveredProducts;
    }

    /**
     * Adds an external logistics partner.
     * @param externalPartner The external logistics partner to be added.
     */
    public void addExternalPartner(ExternalPartner externalPartner){
        this.externalPartner = externalPartner;
        //this.calculateAll();
    }

    /**
     * Removes the currently hired external logistics partner.
     */
    public void removeExternalPartner(){
        this.externalPartner = null;
        //this.calculateAll();
    }

    /**
     * Adds a truck to the internal fleet.
     * @param truck The truck to be added to the internal fleet.
     * @param gameDate The current date in the game.
     * @throws NotEnoughTruckCapacityException if the internal fleet does not have enough capacity.
     */
    public void addTruckToFleet(Truck truck, LocalDate gameDate) throws NotEnoughTruckCapacityException{
        if(this.logisticsCapacity > InternalFleet.getInstance().getTrucks().size()){
            InternalFleet.getInstance().addTruckToFleet(truck, gameDate);
            //this.calculateAll();
        }else{
            //not enough truck capacity
            throw new NotEnoughTruckCapacityException("No more Capacity available to buy new Truck.");
        }
    }

    /**
     * Removes a truck from the internal fleet.
     * @param truck The truck to be removed from the internal fleet.
     */
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

    /**
     * Checks if the eco index of the fleet is below the threshold.
     * @return Returns true if the eco index is below the threshold. Returns false otherwise.
     */
    public boolean checkEcoIndexFleetBelowThreshold(){
        return InternalFleet.getInstance().checkEcoIndexFleetBelowThreshold();
    }

    /**
     * Increases the factor by which the capacity of the internal fleet is decreased.
     * @param amount The amount by which the factor should be increased.
     */
    public void decreaseCapacityFleetRel(double amount){
        InternalFleet.getInstance().decreaseCapacityFleetRel(amount);
    }

    /**
     * Decreases the factor by which the capacity of the internal fleet is decreased.
     * @param amount The amount by which the factor should be decreased.
     */
    public void increaseCapacityFleetRel(double amount){
        InternalFleet.getInstance().increaseCapacityFleetRel(amount);
    }

    public static LogisticsDepartment createInstance() {
        return new LogisticsDepartment();
    }

    public static void setInstance(LogisticsDepartment instance) {
        LogisticsDepartment.instance = instance;
    }

    /**
     * Register the PropertyChangeListener to all PropertyChangeSupport objects.
     *
     * @param listener The PropertyChangeListener to register to listen to all PropertyChangeSupport objects.
     */
    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        throw new UnsupportedOperationException();
    }

    public ArrayList<ExternalPartner> getExternalPartnerSelection() {
        return externalPartnerSelection;
    }

    public String getLocalisedString(String text, Locale locale) {
        ResourceBundle langBundle = ResourceBundle.getBundle(LANGUAGE_PROPERTIES_FILE, locale);
        return langBundle.getString(text);
    }
}
