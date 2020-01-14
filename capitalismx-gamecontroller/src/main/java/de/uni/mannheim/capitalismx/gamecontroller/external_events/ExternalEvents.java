package de.uni.mannheim.capitalismx.gamecontroller.external_events;

import de.uni.mannheim.capitalismx.gamecontroller.ecoindex.CompanyEcoIndex;
import de.uni.mannheim.capitalismx.finance.finance.FinanceDepartment;
import de.uni.mannheim.capitalismx.logistic.logistics.LogisticsDepartment;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author sdupper
 */
public class ExternalEvents implements Serializable {

    private static ExternalEvents instance;

    private List<ExternalEvent> externalEvents;
    private boolean productionTechnologyBelowThreshold;
    private LocalDate lastEventCompanyAcquisitionDate;
    private boolean eventCompanyOvertakesMarketShare;
    private LocalDate eventComputerVirusAttacksDate;
    private LocalDate eventTaxChangesDate;
    private LocalDate eventFluDate;
    private boolean changeOfPower;
    private LocalDate eventEcoActivistsDate;
    private Map<LocalDate, List<ExternalEvent>> externalEventsHistory;

    public enum ExternalEvent implements Serializable{
        EVENT_1("Production Problems pop up"),
        EVENT_2("New technology increases quality"),
        EVENT_3("Company acquisiton possibility offered"),
        EVENT_4("Large company overtakes marketshare"),
        EVENT_5("Company brand reputation plunges"),
        EVENT_6("Computer virus attacks"),
        EVENT_7("Tax changes lead to higher or lower rates"),
        EVENT_8("Stricter eco laws"),
        EVENT_9("Inflation changes"),
        EVENT_10("Stealing inside of the company"),
        EVENT_11("Strikes"),
        EVENT_12("Flu goes around causing ill employees"),
        EVENT_13("Hurricanes, tornadoes, earthquakes"),
        EVENT_14("Fire / Flooding in warehouse"),
        EVENT_15("Problems with customs at the harbour"),
        EVENT_16("Change of power"),
        EVENT_17("Eco activists block roads"),
        EVENT_18("Tensions between our country and others"),
        EVENT_19("Game Over");

        private String title;
        private boolean increase;

        private ExternalEvent(String title){
            this.title = title;
        }

        public void setIncrease(boolean increase){
            this.increase = increase;
        }

        public String getTitle() {
            return this.title;
        }

        public boolean isIncrease(){
            return this.increase;
        }
    }

    private ExternalEvents(){
        this.externalEvents = new ArrayList<>();
        this.externalEventsHistory = new TreeMap<>();
        this.productionTechnologyBelowThreshold = false;
        this.eventCompanyOvertakesMarketShare = false;
        this.changeOfPower = false;
    }

    public static synchronized ExternalEvents getInstance() {
        if(ExternalEvents.instance == null) {
            ExternalEvents.instance = new ExternalEvents();
        }
        return ExternalEvents.instance;
    }

    private void checkEventProductionProblems(){
        if(ProductionDepartment.getInstance().checkProductionTechnologyBelowThreshold()){
            //if productionTechnologyBelowThreshold is not true already
            if(!this.productionTechnologyBelowThreshold){
                externalEvents.add(ExternalEvent.EVENT_1);
                this.productionTechnologyBelowThreshold = true;
            }
        }else{
            this.productionTechnologyBelowThreshold = false;
        }
    }

    private void checkEventNewTechnology(){
        //TODO
        /**if(Production.getInstance().checkBaseQualityAboveThreshold()){
            Customer.getInstance().increaseCustomerSatisfactionRel(0.3);
            externalEvents.add(ExternalEvent.EVENT_2);
        }**/
    }

    private void checkEventCompanyAcquisition(LocalDate gameDate){
        //event can only occur once per year
        if((this.lastEventCompanyAcquisitionDate == null) || (Period.between(this.lastEventCompanyAcquisitionDate, gameDate).getYears() > 0)){
            if(FinanceDepartment.getInstance().checkIncreasingNopat()){
                //TODO ask user
                //FinanceDepartment.getInstance().acquireCompany();
                externalEvents.add(ExternalEvent.EVENT_3);
                this.lastEventCompanyAcquisitionDate = gameDate;
            }
        }

    }

    private void checkEventCompanyOvertakesMarketShare(LocalDate gameDate){
        //can only occur once in the game
        if((RandomNumberGenerator.getRandomInt(0, 49) == 0) && (FinanceDepartment.getInstance().getNetWorth() > 1000000) && (!this.eventCompanyOvertakesMarketShare)){
            FinanceDepartment.getInstance().decreaseNopatRelPermanently(0.10);
            externalEvents.add(ExternalEvent.EVENT_4);
            this.eventCompanyOvertakesMarketShare = true;
        }
    }

    private void checkEventBrandReputationPlunges(){
        //TODO
        /**if(Customer.getInstance().checkCustomerSatisfactionBelowThreshold()){
            MarketingDepartment.getInstance().decreaseCompanyImageRel(0.20);
            externalEvents.add(ExternalEvent.EVENT_5);
        }**/
    }

    private void checkEventComputerVirusAttacks(LocalDate gameDate){
        if((RandomNumberGenerator.getRandomInt(0, 19) == 0) && (gameDate.getYear() > 2000) && (this.eventComputerVirusAttacksDate == null)){
            ProductionDepartment.getInstance().decreaseProcessAutomationRel(0.50);
            externalEvents.add(ExternalEvent.EVENT_6);
            this.eventComputerVirusAttacksDate = gameDate;
        }else if((this.eventComputerVirusAttacksDate != null) && (Period.between(this.eventComputerVirusAttacksDate, gameDate).getMonths() > 3)){
            this.eventComputerVirusAttacksDate = null;
            //TODO add parameter to increaseProcessAutomationRel()
            ProductionDepartment.getInstance().increaseProcessAutomationRel();
        }
    }

    private void checkEventTaxChanges(LocalDate gameDate){
        if((RandomNumberGenerator.getRandomInt(0, 19) == 0) && (this.eventTaxChangesDate == null)){
            if(RandomNumberGenerator.getRandomInt(0, 1) == 0){
                FinanceDepartment.getInstance().increaseTaxRate(0.02);
                ExternalEvent.EVENT_7.setIncrease(true);
            }else{
                FinanceDepartment.getInstance().decreaseTaxRate(0.02);
                ExternalEvent.EVENT_7.setIncrease(false);
            }
            externalEvents.add(ExternalEvent.EVENT_7);
            this.eventTaxChangesDate = gameDate;
        }else if((this.eventTaxChangesDate != null) && (Period.between(this.eventTaxChangesDate, gameDate).getYears() > 0)){
            this.eventTaxChangesDate = null;
            if(ExternalEvent.EVENT_7.isIncrease()){
                FinanceDepartment.getInstance().decreaseTaxRate(0.02);
            }else{
                FinanceDepartment.getInstance().increaseTaxRate(0.02);
            }
        }
    }

    //at the moment, the player is fined every day as long as eco index is below threshold
    private void checkEventStricterEcoLaws(LocalDate gameDate){
        if(CompanyEcoIndex.getInstance().checkEcoIndexBelowThreshold()){
            if(CompanyEcoIndex.getInstance().checkGameOver()){
                //TODO End game
                externalEvents.add(ExternalEvent.EVENT_19);
            }else{
                //TODO use more suitable formula for probability
                double probability = Math.pow(CompanyEcoIndex.getInstance().getEcoIndex().getIndex(), 0.31) - 1;
                if(RandomNumberGenerator.getRandomInt(0, (int)Math.round(1 / probability) - 1) == 0){
                    FinanceDepartment.getInstance().nopatFine(gameDate, 0.10);
                    externalEvents.add(ExternalEvent.EVENT_8);
                }
            }
        }
    }

    //TODO maybe change impact
    private void checkEventInflationChanges(){
        //TODO probability between 0 and 2
        if(RandomNumberGenerator.getRandomInt(0, 49) == 0){
            if(RandomNumberGenerator.getRandomInt(0, 1) == 0){
                FinanceDepartment.getInstance().increaseNopatRelPermanently(0.02);
                ExternalEvent.EVENT_9.setIncrease(true);
            }else{
                FinanceDepartment.getInstance().decreaseNopatRelPermanently(0.02);
                ExternalEvent.EVENT_9.setIncrease(false);
            }
            externalEvents.add(ExternalEvent.EVENT_9);
        }
    }

    private void checkEventStealing(){
        //TODO
        /**if(MarketingDepartment.getInstance().checkTotalJobSatisfactionBelowThreshold(0.50)){
            int numberOfEmployees = 0;
            for(Map.Entry<EmployeeType, Team> team : HRDepartment.getInstance().getTeams().entrySet()){
                numberOfEmployees += team.getValue().getTeam().size();
            }
            Finance.getInstance().decreaseNopatConstant(numberOfEmployees * 5000);
            externalEvents.add(ExternalEvent.EVENT_10);
        }**/
    }

    private void checkEventStrikes(){
        //TODO
        /**
        if(MarketingDepartment.getInstance().checkTotalJobSatisfactionBelowThreshold(0.10)){
            Customer.getInstance().decresePeriodicDemandAmountRel(0.8);
            externalEvents.add(ExternalEvent.EVENT_11);
        }**/
    }

    private void checkEventFlu(LocalDate gameDate){
        if((RandomNumberGenerator.getRandomInt(0, 9) == 0) && ((gameDate.getMonthValue() == 12) || (gameDate.getMonthValue() < 3)) && (this.eventFluDate == null)){
            ProductionDepartment.getInstance().decreaseTotalEngineerQualityOfWorkRel(0.10);
            externalEvents.add(ExternalEvent.EVENT_12);
            this.eventFluDate = gameDate;
        }else if((this.eventFluDate != null) && (Period.between(this.eventFluDate, gameDate).getMonths() > 0)){
            this.eventFluDate = null;
            //TODO add parameter to increaseTotalEngineerQualityOfWorkRel()
            ProductionDepartment.getInstance().increaseTotalEngineerQualityOfWorkRel();
        }
    }

    private void checkEventHurricanesTornadoesEarthquakes(){
        if(CompanyEcoIndex.getInstance().checkEcoIndexBelowThreshold() && WarehousingDepartment.getInstance().checkWarehouseCapacityThreshold()){
            double probability = 0.1 / CompanyEcoIndex.getInstance().getEcoIndex().getIndex();
            if(RandomNumberGenerator.getRandomInt(0, (int)Math.round(1 / probability) - 1) == 0){
                WarehousingDepartment.getInstance().decreaseCapacity(2000);
                externalEvents.add(ExternalEvent.EVENT_13);
            }
        }
    }

    private void checkEventFireFlooding(){
        if(WarehousingDepartment.getInstance().checkFreeStorageThreshold()){
            double probability = WarehousingDepartment.getInstance().getDaysSinceFreeStorageThreshold() * 0.01;
            if(RandomNumberGenerator.getRandomInt(0, (int)Math.round(1 / probability) - 1) == 0){
                WarehousingDepartment.getInstance().decreaseStoredUnitsRel(0.30);
                externalEvents.add(ExternalEvent.EVENT_14);
            }

        }
    }

    //TODO Future work
    private void checkEventProblemsWithCustoms(){

    }

    private void checkEventChangeOfPower(){
        if((RandomNumberGenerator.getRandomInt(0, 9999) == 0) && (!this.changeOfPower)){
            FinanceDepartment.getInstance().increaseTaxRate(0.05);
            externalEvents.add(ExternalEvent.EVENT_16);
            this.changeOfPower = true;
        }
    }

    private void checkEventEcoActivists(LocalDate gameDate){
        if(LogisticsDepartment.getInstance().checkEcoIndexFleetBelowThreshold() && (this.eventEcoActivistsDate == null)){
            LogisticsDepartment.getInstance().decreaseCapacityFleetRel(0.70);
            externalEvents.add(ExternalEvent.EVENT_17);
            this.eventEcoActivistsDate = gameDate;
        }else if((this.eventEcoActivistsDate != null) && (Period.between(this.eventEcoActivistsDate, gameDate).getMonths() > 1)){
            this.eventEcoActivistsDate = null;
            LogisticsDepartment.getInstance().increaseCapacityFleetRel(0.70);
        }
    }

    //TODO Future work
    private void checkEventTensions(){

    }

    public List<ExternalEvent> checkEvents(LocalDate gameDate){
        this.externalEvents = new ArrayList<>();
        this.checkEventProductionProblems();
        this.checkEventNewTechnology();
        this.checkEventCompanyAcquisition(gameDate);
        this.checkEventCompanyOvertakesMarketShare(gameDate);
        this.checkEventBrandReputationPlunges();
        this.checkEventComputerVirusAttacks(gameDate);
        this.checkEventTaxChanges(gameDate);
        this.checkEventStricterEcoLaws(gameDate);
        this.checkEventInflationChanges();
        this.checkEventStealing();
        this.checkEventStrikes();
        this.checkEventFlu(gameDate);
        this.checkEventHurricanesTornadoesEarthquakes();
        this.checkEventFireFlooding();
        this.checkEventProblemsWithCustoms();
        this.checkEventChangeOfPower();
        this.checkEventEcoActivists(gameDate);
        this.checkEventTensions();
        this.externalEventsHistory.put(gameDate, this.externalEvents);
        return this.externalEvents;
    }

    public List<ExternalEvent> getExternalEvents() {
        return this.externalEvents;
    }

    public Map<LocalDate, List<ExternalEvent>> getExternalEventsHistory() {
        return this.externalEventsHistory;
    }

    public static void setInstance(ExternalEvents instance) {
        ExternalEvents.instance = instance;
    }
}
