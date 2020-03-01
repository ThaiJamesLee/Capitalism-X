package de.uni.mannheim.capitalismx.gamecontroller;

import de.uni.mannheim.capitalismx.customer.CustomerDemand;
import de.uni.mannheim.capitalismx.customer.CustomerSatisfaction;
import de.uni.mannheim.capitalismx.domain.department.Department;
import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.gamecontroller.ecoindex.CompanyEcoIndex;
import de.uni.mannheim.capitalismx.gamecontroller.external_events.ExternalEvents;
import de.uni.mannheim.capitalismx.finance.finance.FinanceDepartment;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeGenerator;
import de.uni.mannheim.capitalismx.logistic.logistics.InternalFleet;
import de.uni.mannheim.capitalismx.logistic.logistics.LogisticsDepartment;
import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment;
import de.uni.mannheim.capitalismx.procurement.department.ProcurementDepartment;
import de.uni.mannheim.capitalismx.production.department.ProductionDepartment;
import de.uni.mannheim.capitalismx.resdev.department.ResearchAndDevelopmentDepartment;

import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportBoolean;

import de.uni.mannheim.capitalismx.sales.department.SalesDepartment;

import de.uni.mannheim.capitalismx.utils.data.MessageObject;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;
import de.uni.mannheim.capitalismx.department.WarehousingDepartment;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class contains all instances for the game to work.
 * @author duly
 * @author dzhao
 */
public class GameState implements Serializable {

	private static GameState instance;

	/**
	 * The current game date.
	 */
	private LocalDate gameDate;

	/**
	 * The current employer branding index.
	 * This value depends on the job satisfaction in {@link HRDepartment} and company image in {@link MarketingDepartment}.
	 */
	private double employerBranding;

	/**
	 * A property change support. This class can to fire events, if implemented.
	 */
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	/**
	 * This boolean fires an event when the end of the game is reached.
	 */
    private PropertyChangeSupportBoolean endGameReached;

	/**
	 * The list that contains the {@link MessageObject}s.
	 */
	private PropertyChangeSupportList<MessageObject> messages;

	/**
	 * Event name when the {@link PropertyChangeSupportList} changes.
	 */
	public static final String MESSAGE_LIST_CHANGED_EVENT = "messageListChanged";

	/**
	 * All department classes are saved in this map.
	 * To retrieve a specific department, one needs to call
	 * {@link Map}.get(Department.class.getSimpleName())
	 */
	private Map<String, Department> allDepartments;

	private EmployeeGenerator employeeGenerator;
	private CustomerSatisfaction customerSatisfaction;
	private CustomerDemand customerDemand;
	private ExternalEvents externalEvents;
	private CompanyEcoIndex companyEcoIndex;
	private InternalFleet internalFleet;
	private ProductSupport productSupport;
	private BankingSystem bankingSystem;

	private GameState() {
		this.gameDate = LocalDate.of(1990, 1, 1);
		allDepartments = new ConcurrentHashMap<>();
		messages = new PropertyChangeSupportList<>();
		messages.setAddPropertyName(MESSAGE_LIST_CHANGED_EVENT);
		messages.setRemovePropertyName(MESSAGE_LIST_CHANGED_EVENT);

		 this.endGameReached = new PropertyChangeSupportBoolean();
	     this.endGameReached.setValue(false);
	     this.endGameReached.setPropertyChangedName("gameWon");
	}

	public static GameState getInstance() {
		if (instance == null) {
			instance = new GameState();
		}
		return instance;
	}

	/**
	 * Initialize all instances as singletons.
	 */
	public void initiate() {
		allDepartments.put(HRDepartment.class.getSimpleName(), HRDepartment.getInstance());
		allDepartments.put(ProductionDepartment.class.getSimpleName(), ProductionDepartment.getInstance());
		allDepartments.put(WarehousingDepartment.class.getSimpleName(), WarehousingDepartment.getInstance());
		allDepartments.put(FinanceDepartment.class.getSimpleName(), FinanceDepartment.getInstance());
		allDepartments.put(MarketingDepartment.class.getSimpleName(), MarketingDepartment.getInstance());
		allDepartments.put(LogisticsDepartment.class.getSimpleName(), LogisticsDepartment.getInstance());
		allDepartments.put(ProcurementDepartment.class.getSimpleName(), ProcurementDepartment.getInstance());
		allDepartments.put(ResearchAndDevelopmentDepartment.class.getSimpleName(), ResearchAndDevelopmentDepartment.getInstance());
		allDepartments.put(SalesDepartment.class.getSimpleName(), SalesDepartment.getInstance());

		customerSatisfaction = CustomerSatisfaction.getInstance();
		customerDemand = CustomerDemand.getInstance();
		externalEvents = ExternalEvents.getInstance();
		companyEcoIndex = CompanyEcoIndex.getInstance();
		internalFleet = InternalFleet.getInstance();
		bankingSystem = BankingSystem.getInstance();
		productSupport = ProductSupport.getInstance();
		employeeGenerator = EmployeeGenerator.getInstance();
		employeeGenerator.setDepartment((HRDepartment) allDepartments.get(HRDepartment.class.getSimpleName()));
	}

	/**
	 * Sets all the Department-Singletons to null, so they will be recreated and
	 * reset, when called again.
	 *
	 * TODO: Does not work!
	 */
	public void resetDepartments() {
		HRDepartment.setInstance(HRDepartment.createInstance());
		ProductionDepartment.setInstance(ProductionDepartment.createInstance());
		WarehousingDepartment.setInstance(WarehousingDepartment.createInstance());
		FinanceDepartment.setInstance(FinanceDepartment.createInstance());
		MarketingDepartment.setInstance(MarketingDepartment.createInstance());
		LogisticsDepartment.setInstance(LogisticsDepartment.createInstance());
		ProductSupport.setInstance(ProductSupport.createInstance());
		SalesDepartment.setInstance(SalesDepartment.createInstance());
		ResearchAndDevelopmentDepartment.setInstance(ResearchAndDevelopmentDepartment.createInstance());
		ProcurementDepartment.setInstance(ProcurementDepartment.createInstance());


		CustomerSatisfaction.setInstance(CustomerSatisfaction.createInstance());
		CustomerDemand.setInstance(CustomerDemand.createInstance());
		ExternalEvents.setInstance(ExternalEvents.createInstance());
		CompanyEcoIndex.setInstance(CompanyEcoIndex.createInstance());
		EmployeeGenerator.setInstance(EmployeeGenerator.createInstance());
		EmployeeGenerator.getInstance().setDepartment(HRDepartment.getInstance());
		InternalFleet.setInstance(InternalFleet.createInstance());
		BankingSystem.setInstance(BankingSystem.createInstance());
	}

	/**
	 * Set the current employer branding index.
	 * This value depends on the job satisfaction in {@link HRDepartment} and company image in {@link MarketingDepartment}.
	 */
	public void setEmployerBranding(double employerBranding) {
		this.employerBranding = employerBranding;
	}

	/**
	 *
	 * @return Returns the employer branding index.
	 */
	public double getEmployerBranding() {
		return employerBranding;
	}

	/**
	 * Add a change listener that notifies, if a property has changed.
	 *
	 * @param listener A property change listener.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.addPropertyChangeListener(listener);
		this.messages.addPropertyChangeListener(listener);
		this.endGameReached.addPropertyChangeListener(listener);
	}

	/**
	 * Remove a change listener.
	 *
	 * @param listener The property listener that needs to be removed.
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public LocalDate getGameDate() {
		return this.gameDate;
	}

	/**
	 * Sets the new game date and fires a property changed event.
	 *
	 * @param gameDate Sets new game date.
	 */
	public void setGameDate(LocalDate gameDate) {
		LocalDate oldDate = this.gameDate;
		this.gameDate = gameDate;
		this.propertyChangeSupport.firePropertyChange("gameDate", oldDate, gameDate);
	}

	/**
	 * Fires a property changed event that the game end date was reached
	 * @param gameDate
	 */
	public void endGameReached(LocalDate gameDate) {
		this.endGameReached.setValue(true);
	}

	/**
	 * Sets the new GameState instance as the singleton.
	 *
	 * @param state The new GameState.
	 */
	public static void setInstance(GameState state) {
		GameState.instance = state;
	}

	/**
	 * This instance is not a singleton, and can not be called by the getInstance
	 * method.
	 *
	 * @return Returns a newly created instance.
	 */
	public static GameState createInstance() {
		return new GameState();
	}

	/**
	 * Retrieves the department using the {@link HRDepartment}.class.getSimpleName() as key.
	 * @return returns the current {@link HRDepartment} instance.
	 */
	public HRDepartment getHrDepartment() {
		return (HRDepartment) this.allDepartments.get(HRDepartment.class.getSimpleName());
	}

	/**
	 * Store the instance in the map.
	 * @param hrDepartment set the {@link HRDepartment} into the departments {@link Map}.
	 */
	public void setHrDepartment(HRDepartment hrDepartment) {
		this.allDepartments.put(HRDepartment.class.getSimpleName(), hrDepartment);
	}

	/**
	 * Retrieves the department using the {@link ProductionDepartment}.class.getSimpleName() as key.
	 * @return returns the current {@link ProductionDepartment} instance.
	 */
	public ProductionDepartment getProductionDepartment() {
		return (ProductionDepartment) this.allDepartments.get(ProductionDepartment.class.getSimpleName());
	}

	/**
	 * Store the instance in the map.
	 * @param productionDepartment set the {@link ProductionDepartment} into the departments {@link Map}.
	 */
	public void setProductionDepartment(ProductionDepartment productionDepartment) {
		this.allDepartments.put(ProductionDepartment.class.getSimpleName(), productionDepartment);
	}

	/**
	 * Retrieves the department using the {@link ProcurementDepartment}.class.getSimpleName() as key.
	 * @return returns the current {@link ProcurementDepartment} instance.
	 */
	public ProcurementDepartment getProcurementDepartment() {
		return (ProcurementDepartment) this.allDepartments.get(ProcurementDepartment.class.getSimpleName());
	}

	/**
	 * Store the instance in the map.
	 * @param procurementDepartment set the {@link ProcurementDepartment} into the departments {@link Map}.
	 */
	public void setProcurementDepartment(ProcurementDepartment procurementDepartment) {
		this.allDepartments.put(ProcurementDepartment.class.getSimpleName(), procurementDepartment);
	}

	/**
	 * Retrieves the department using the {@link WarehousingDepartment}.class.getSimpleName() as key.
	 * @return returns the current {@link WarehousingDepartment} instance.
	 */
	public WarehousingDepartment getWarehousingDepartment() {
		return (WarehousingDepartment) this.allDepartments.get(WarehousingDepartment.class.getSimpleName());
	}

	/**
	 * Store the instance in the map.
	 * @param warehousingDepartment set the {@link WarehousingDepartment} into the departments {@link Map}.
	 */
	public void setWarehousingDepartment(WarehousingDepartment warehousingDepartment) {
		this.allDepartments.put(WarehousingDepartment.class.getSimpleName(), warehousingDepartment);
	}

	/**
	 * Retrieves the department using the {@link FinanceDepartment}.class.getSimpleName() as key.
	 * @return returns the current {@link FinanceDepartment} instance.
	 */
	public FinanceDepartment getFinanceDepartment() {
		return (FinanceDepartment) this.allDepartments.get(FinanceDepartment.class.getSimpleName());
	}

	/**
	 * Store the instance in the map.
	 * @param financeDepartment set the {@link FinanceDepartment} into the departments {@link Map}.
	 */
	public void setFinanceDepartment(FinanceDepartment financeDepartment) {
		this.allDepartments.put(FinanceDepartment.class.getSimpleName(), financeDepartment);
	}

	/**
	 * Retrieves the department using the {@link MarketingDepartment}.class.getSimpleName() as key.
	 * @return returns the current {@link MarketingDepartment} instance.
	 */
	public MarketingDepartment getMarketingDepartment() {
		return (MarketingDepartment) this.allDepartments.get(MarketingDepartment.class.getSimpleName());
	}

	/**
	 * Store the instance in the map.
	 * @param marketingDepartment set the {@link MarketingDepartment} into the departments {@link Map}.
	 */
	public void setMarketingDepartment(MarketingDepartment marketingDepartment) {
		this.allDepartments.put(MarketingDepartment.class.getSimpleName(), marketingDepartment);
	}

	/**
	 * Retrieves the department using the {@link LogisticsDepartment}.class.getSimpleName() as key.
	 * @return returns the current {@link LogisticsDepartment} instance.
	 */
	public LogisticsDepartment getLogisticsDepartment() {
		return (LogisticsDepartment) this.allDepartments.get(LogisticsDepartment.class.getSimpleName());
	}

	/**
	 * Store the instance in the map.
	 * @param logisticsDepartment set the {@link LogisticsDepartment} into the departments {@link Map}.
	 */
	public void setLogisticsDepartment(LogisticsDepartment logisticsDepartment) {
		this.allDepartments.put(LogisticsDepartment.class.getSimpleName(), logisticsDepartment);
	}


	public CustomerSatisfaction getCustomerSatisfaction() {
		return customerSatisfaction;
	}

	public void setCustomerSatisfaction(CustomerSatisfaction customerSatisfaction) {
		this.customerSatisfaction = customerSatisfaction;
	}

	public CustomerDemand getCustomerDemand() {
		return customerDemand;
	}

	public void setCustomerDemand(CustomerDemand customerDemand) {
		this.customerDemand = customerDemand;
	}

	public ExternalEvents getExternalEvents() {
		return externalEvents;
	}

	public void setExternalEvents(ExternalEvents externalEvents) {
		this.externalEvents = externalEvents;
	}

	public CompanyEcoIndex getCompanyEcoIndex() {
		return companyEcoIndex;
	}

	public void setCompanyEcoIndex(CompanyEcoIndex companyEcoIndex) {
		this.companyEcoIndex = companyEcoIndex;
	}

	public InternalFleet getInternalFleet() {
		return internalFleet;
	}

	public void setInternalFleet(InternalFleet internalFleet) {
		this.internalFleet = internalFleet;
	}

	public EmployeeGenerator getEmployeeGenerator() {
		return employeeGenerator;
	}

	public void setEmployeeGenerator(EmployeeGenerator employeeGenerator) {
		this.employeeGenerator = employeeGenerator;
	}

	/**
	 * Retrieves the department using the {@link ResearchAndDevelopmentDepartment}.class.getSimpleName() as key.
	 * @return returns the current {@link ResearchAndDevelopmentDepartment} instance.
	 */
	public ResearchAndDevelopmentDepartment getResearchAndDevelopmentDepartment() {
		return (ResearchAndDevelopmentDepartment) this.allDepartments.get(ResearchAndDevelopmentDepartment.class.getSimpleName());
	}

	/**
	 * Store the instance in the map.
	 * @param researchAndDevelopmentDepartment set the {@link ResearchAndDevelopmentDepartment} into the departments {@link Map}.
	 */
	public void setResearchAndDevelopmentDepartment(ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment) {
		this.allDepartments.put(ResearchAndDevelopmentDepartment.class.getSimpleName(), researchAndDevelopmentDepartment);
	}

	public ProductSupport getProductSupport() {
		return productSupport;
	}

	public void setProductSupport(ProductSupport productSupport) {
		this.productSupport = productSupport;
	}

	/**
	 * Retrieves the department using the {@link SalesDepartment}.class.getSimpleName() as key.
	 * @return returns the current {@link SalesDepartment} instance.
	 */
	public SalesDepartment getSalesDepartment() {
		return (SalesDepartment) this.allDepartments.get(SalesDepartment.class.getSimpleName());
	}

	/**
	 * Store the instance in the map.
	 * @param salesDepartment set the {@link SalesDepartment} into the departments {@link Map}.
	 */
	public void setSalesDepartment(SalesDepartment salesDepartment) {
		this.allDepartments.put(SalesDepartment.class.getSimpleName(), salesDepartment);
	}

	public BankingSystem getBankingSystem() {
		return bankingSystem;
	}

	public void setBankingSystem(BankingSystem bankingSystem) {
		this.bankingSystem = bankingSystem;
	}

	/**
	 *
	 * @return Returns the list of messages.
	 */
	public PropertyChangeSupportList<MessageObject> getMessages() {
		return messages;
	}
}
