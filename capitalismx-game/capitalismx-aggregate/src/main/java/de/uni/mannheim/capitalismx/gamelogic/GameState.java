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
        return  instance;
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
}
