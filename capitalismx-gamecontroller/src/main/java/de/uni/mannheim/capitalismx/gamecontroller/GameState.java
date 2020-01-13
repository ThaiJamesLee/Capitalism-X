package de.uni.mannheim.capitalismx.gamecontroller;

import de.uni.mannheim.capitalismx.customer.CustomerDemand;
import de.uni.mannheim.capitalismx.customer.CustomerSatisfaction;
import de.uni.mannheim.capitalismx.gamecontroller.ecoindex.CompanyEcoIndex;
import de.uni.mannheim.capitalismx.gamecontroller.external_events.ExternalEvents;
import de.uni.mannheim.capitalismx.finance.finance.FinanceDepartment;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.logistic.logistics.InternalFleet;
import de.uni.mannheim.capitalismx.logistic.logistics.LogisticsDepartment;
import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import de.uni.mannheim.capitalismx.resdev.department.ResearchAndDevelopmentDepartment;

import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportBoolean;

import de.uni.mannheim.capitalismx.sales.department.SalesDepartment;

import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDate;

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

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
    private PropertyChangeSupportBoolean endGameReached;

	// Departments
	private HRDepartment hrDepartment;
	private ProductionDepartment productionDepartment;
	private WarehousingDepartment warehousingDepartment;
	private FinanceDepartment financeDepartment;
	private MarketingDepartment marketingDepartment;
	private LogisticsDepartment logisticsDepartment;
	private CustomerSatisfaction customerSatisfaction;
	private CustomerDemand customerDemand;
	private ExternalEvents externalEvents;
	private CompanyEcoIndex companyEcoIndex;
	private InternalFleet internalFleet;
	private ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment;
	private ProductSupport productSupport;
	private SalesDepartment salesDepartment;

	private GameState() {
		this.gameDate = LocalDate.of(1990, 1, 1);
		
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
		hrDepartment = HRDepartment.getInstance();
		productionDepartment = ProductionDepartment.getInstance();
		warehousingDepartment = WarehousingDepartment.getInstance();
		financeDepartment = FinanceDepartment.getInstance();
		marketingDepartment = MarketingDepartment.getInstance();
		logisticsDepartment = LogisticsDepartment.getInstance();
		customerSatisfaction = CustomerSatisfaction.getInstance();
		customerDemand = CustomerDemand.getInstance();
		// TODO procurement once a procurement department is implemented where it is
		// possible to buy components
		externalEvents = ExternalEvents.getInstance();
		companyEcoIndex = CompanyEcoIndex.getInstance();
		internalFleet = InternalFleet.getInstance();
		researchAndDevelopmentDepartment = ResearchAndDevelopmentDepartment.getInstance();
		productSupport = ProductSupport.getInstance();
		salesDepartment = SalesDepartment.getInstance();
	}

	/**
	 * Sets all the Department-Singletons to null, so they will be recreated and
	 * reset, when called again.
	 *
	 * TODO: Does not work!
	 */
	public void resetDepartments() {
		HRDepartment.setInstance(null);
		ProductionDepartment.setInstance(null);
		WarehousingDepartment.setInstance(null);
		FinanceDepartment.setInstance(null);
		MarketingDepartment.setInstance(null);
		LogisticsDepartment.setInstance(null);
		CustomerSatisfaction.setInstance(null);
		CustomerDemand.setInstance(null);
		// TODO procurement once a procurement department is implemented where it is
		// possible to buy components
		ExternalEvents.setInstance(null);
		CompanyEcoIndex.setInstance(null);
		InternalFleet.setInstance(null);
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

	public HRDepartment getHrDepartment() {
		return hrDepartment;
	}

	public void setHrDepartment(HRDepartment hrDepartment) {
		this.hrDepartment = hrDepartment;
	}

	public ProductionDepartment getProductionDepartment() {
		return productionDepartment;
	}

	public void setProductionDepartment(ProductionDepartment productionDepartment) {
		this.productionDepartment = productionDepartment;
	}

	public WarehousingDepartment getWarehousingDepartment() {
		return warehousingDepartment;
	}

	public void setWarehousingDepartment(WarehousingDepartment warehousingDepartment) {
		this.warehousingDepartment = warehousingDepartment;
	}

	public FinanceDepartment getFinanceDepartment() {
		return financeDepartment;
	}

	public void setFinanceDepartment(FinanceDepartment financeDepartment) {
		this.financeDepartment = financeDepartment;
	}

	public MarketingDepartment getMarketingDepartment() {
		return marketingDepartment;
	}

	public void setMarketingDepartment(MarketingDepartment marketingDepartment) {
		this.marketingDepartment = marketingDepartment;
	}

	public LogisticsDepartment getLogisticsDepartment() {
		return logisticsDepartment;
	}

	public void setLogisticsDepartment(LogisticsDepartment logisticsDepartment) {
		this.logisticsDepartment = logisticsDepartment;
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

	public ResearchAndDevelopmentDepartment getResearchAndDevelopmentDepartment() {
		return researchAndDevelopmentDepartment;
	}

	public void setResearchAndDevelopmentDepartment(ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment) {
		this.researchAndDevelopmentDepartment = researchAndDevelopmentDepartment;
	}
	public ProductSupport getProductSupport() {
		return productSupport;
	}

	public void setProductSupport(ProductSupport productSupport) {
		this.productSupport = productSupport;
	}

	public SalesDepartment getSalesDepartment() {
		return salesDepartment;
	}

	public void setSalesDepartment(SalesDepartment salesDepartment) {
		this.salesDepartment = salesDepartment;
	}
}
