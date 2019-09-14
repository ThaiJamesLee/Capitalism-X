package de.uni.mannheim.capitalismx.gamelogic;

import de.uni.mannheim.capitalismx.customer.CustomerDemand;
import de.uni.mannheim.capitalismx.customer.CustomerSatisfaction;
import de.uni.mannheim.capitalismx.ecoindex.CompanyEcoIndex;
import de.uni.mannheim.capitalismx.external_events.ExternalEvents;
import de.uni.mannheim.capitalismx.finance.finance.Finance;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.logistic.logistics.Logistics;
import de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.Production;
import de.uni.mannheim.capitalismx.warehouse.Warehousing;

import java.io.Serializable;
import java.time.LocalDate;

public class GameState implements Serializable {

    private static GameState instance;
    private LocalDate gameDate;

    // Departments
    private HRDepartment hrDepartment;
    private Production productionDepartment;
    private Warehousing warehousingDepartment;
    private Finance financeDepartment;
    private MarketingDepartment marketingDepartment;
    private Logistics logisticsDepartment;
    private CustomerSatisfaction customerSatisfaction;
    private CustomerDemand customerDemand;
    private ExternalEvents externalEvents;
    private CompanyEcoIndex companyEcoIndex;

    private GameState() {
        this.gameDate = LocalDate.of(1990, 1, 1);
    }


    public static GameState getInstance() {
        if(instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public void initiate() {
        hrDepartment = HRDepartment.getInstance();
        productionDepartment = Production.getInstance();
        warehousingDepartment = Warehousing.getInstance();
        financeDepartment = Finance.getInstance();
        marketingDepartment = MarketingDepartment.getInstance();
        logisticsDepartment = Logistics.getInstance();
        customerSatisfaction = CustomerSatisfaction.getInstance();
        customerDemand = CustomerDemand.getInstance();
        // TODO procurement once a procurement department is implemented where it is possible to buy components
        externalEvents = ExternalEvents.getInstance();
        companyEcoIndex = CompanyEcoIndex.getInstance();
    }

    public LocalDate getGameDate() {
        return this.gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    /**
     * Sets the new GameState instance as the singleton.
     * @param state The new GameState.
     */
    public static void setInstance(GameState state) {
        instance = state;
    }

    public HRDepartment getHrDepartment() {
        return hrDepartment;
    }

    public void setHrDepartment(HRDepartment hrDepartment) {
        this.hrDepartment = hrDepartment;
    }

    public Production getProductionDepartment() {
        return productionDepartment;
    }

    public void setProductionDepartment(Production productionDepartment) {
        this.productionDepartment = productionDepartment;
    }

    public Warehousing getWarehousingDepartment() {
        return warehousingDepartment;
    }

    public void setWarehousingDepartment(Warehousing warehousingDepartment) {
        this.warehousingDepartment = warehousingDepartment;
    }

    public Finance getFinanceDepartment() {
        return financeDepartment;
    }

    public void setFinanceDepartment(Finance financeDepartment) {
        this.financeDepartment = financeDepartment;
    }

    public MarketingDepartment getMarketingDepartment() {
        return marketingDepartment;
    }

    public void setMarketingDepartment(MarketingDepartment marketingDepartment) {
        this.marketingDepartment = marketingDepartment;
    }

    public Logistics getLogisticsDepartment() {
        return logisticsDepartment;
    }

    public void setLogisticsDepartment(Logistics logisticsDepartment) {
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
}
