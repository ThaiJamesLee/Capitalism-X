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
 * This class represents the external events that can occur during the game.
 * This class implements the external events, factors that cause these events, and checks which external events occurred.
 * Based on the report p.80-83
 *
 * @author sdupper
 */
public class ExternalEvents implements Serializable {

    /**
     * The singleton pointer.
     */
    private static ExternalEvents instance;

    /**
     * List of external events that occurred when checkEvents(LocalDate gameDate) was called.
     */
    private List<ExternalEvent> externalEvents;

    /**
     * specifies if the production technology is currently below the threshold.
     */
    private boolean productionTechnologyBelowThreshold;

    /**
     * specifies the last date on which the event 'company acquisition' occurred.
     */
    private LocalDate lastEventCompanyAcquisitionDate;

    /**
     * specifies whether the event 'company overtakes market share' ever occurred during the game.
     */
    private boolean eventCompanyOvertakesMarketShare;

    /**
     * specifies the date on which the current 'computer virus attack' event occurred. When the impact of the current
     * event ends, the date is set to null.
     */
    private LocalDate eventComputerVirusAttacksDate;

    /**
     * specifies the date on which the current 'tax change' event occurred. When the impact of the current event ends,
     * the date is set to null.
     */
    private LocalDate eventTaxChangesDate;

    /**
     * specifies the date on which the current 'flu' event occurred. When the impact of the current event ends, the
     * date is set to null.
     */
    private LocalDate eventFluDate;

    /**
     * specifies whether the event 'change of power' ever occurred during the game.
     */
    private boolean changeOfPower;

    /**
     * specifies the date on which the current 'eco activists' event occurred. When the impact of the current event
     * ends, the date is set to null.
     */
    private LocalDate eventEcoActivistsDate;

    /**
     * Map that contains the list of all external events that occurred on a certain date.
     */
    private Map<LocalDate, List<ExternalEvent>> externalEventsHistory;

    /**
     * All external events that can occur.
     */
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

    /**
     * Constructor
     */
    private ExternalEvents(){
        this.externalEvents = new ArrayList<>();
        this.externalEventsHistory = new TreeMap<>();
        this.productionTechnologyBelowThreshold = false;
        this.eventCompanyOvertakesMarketShare = false;
        this.changeOfPower = false;
    }

    /**
     *
     * @return Returns the singleton instance
     */
    public static synchronized ExternalEvents getInstance() {
        if(ExternalEvents.instance == null) {
            ExternalEvents.instance = new ExternalEvents();
        }
        return ExternalEvents.instance;
    }

    /**
     * Checks if production problems occurred, see p.98
     * If the event already occurred, it can only occur again if the production technology was at least once above the
     * threshold since the last occurrence.
     */
    private void checkEventProductionProblems(){
        if(ProductionDepartment.getInstance().checkProductionTechnologyBelowThreshold()){
            //if productionTechnologyBelowThreshold is not true already
            if(!this.productionTechnologyBelowThreshold){
                //TODO activate event
                //externalEvents.add(ExternalEvent.EVENT_1);
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

    /**
     * Checks if a company can be acquired, see p.98
     * This event can only occur at most once per year.
     * @param gameDate The current date in the game.
     */
    private void checkEventCompanyAcquisition(LocalDate gameDate){
        //event can only occur once per year
        if((this.lastEventCompanyAcquisitionDate == null) || (Period.between(this.lastEventCompanyAcquisitionDate, gameDate).getYears() > 0)){
            //TODO set nopat history
            if(FinanceDepartment.getInstance().checkIncreasingNopat()){
                //TODO ask user
                //FinanceDepartment.getInstance().acquireCompany();
                externalEvents.add(ExternalEvent.EVENT_3);
                this.lastEventCompanyAcquisitionDate = gameDate;
            }
        }

    }

    /**
     * Checks if another company overtakes market share, see p.98
     * This event can only occur once in the game.
     * @param gameDate The current date in the game.
     */
    private void checkEventCompanyOvertakesMarketShare(LocalDate gameDate){
        //can only occur once in the game
        //if((RandomNumberGenerator.getRandomInt(0, 49) == 0) && (FinanceDepartment.getInstance().getNetWorth() > 1000000) && (!this.eventCompanyOvertakesMarketShare)){
        if((RandomNumberGenerator.getRandomInt(0, 20000) == 0) && (FinanceDepartment.getInstance().getNetWorth() > 1000000) && (!this.eventCompanyOvertakesMarketShare)){
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

    /**
     * Checks if a computer virus attack occurred, see p.98
     * This event can only occur again if the impact of the last attack ended.
     * @param gameDate The current date in the game.
     */
    private void checkEventComputerVirusAttacks(LocalDate gameDate){
        //if((RandomNumberGenerator.getRandomInt(0, 19) == 0) && (gameDate.getYear() > 2000) && (this.eventComputerVirusAttacksDate == null)){
        if((RandomNumberGenerator.getRandomInt(0, 2000) == 0) && (gameDate.getYear() > 2000) && (this.eventComputerVirusAttacksDate == null)){
            ProductionDepartment.getInstance().decreaseProcessAutomationRel(0.50);
            externalEvents.add(ExternalEvent.EVENT_6);
            this.eventComputerVirusAttacksDate = gameDate;
        }else if((this.eventComputerVirusAttacksDate != null) && (Period.between(this.eventComputerVirusAttacksDate, gameDate).getMonths() > 3)){
            this.eventComputerVirusAttacksDate = null;
            //TODO add parameter to increaseProcessAutomationRel()
            ProductionDepartment.getInstance().increaseProcessAutomationRel();
        }
    }

    /**
     * Checks if the tax changes - the tax rate can either increase or decrease, see p.98
     * This event can only occur again if the impact of the last tax change ended.
     * @param gameDate The current date in the game.
     */
    private void checkEventTaxChanges(LocalDate gameDate){
        //if((RandomNumberGenerator.getRandomInt(0, 19) == 0) && (this.eventTaxChangesDate == null)){
        if((RandomNumberGenerator.getRandomInt(0, 2000) == 0) && (this.eventTaxChangesDate == null)){
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

    /**
     * Checks if stricter eco laws were introduced, see p.98
     * The player can be fined every day as long as the eco index is below the threshold. If the eco index is too low,
     * the game is over.
     * @param gameDate The current date in the game.
     */
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

    /**
     * Checks if the inflation changes, see p.98
     * As a result, the NOPAT can either increase or decrease
     */
    private void checkEventInflationChanges(){
        //TODO maybe change impact
        //TODO probability between 0 and 2
        //if(RandomNumberGenerator.getRandomInt(0, 49) == 0){
        if(RandomNumberGenerator.getRandomInt(0, 2000) == 0){
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

    /**
     * Checks if the event 'flu' occurred, see p.98
     * This event can only occur again after the impact of the last flu ended.
     * @param gameDate The current date in the game.
     */
    private void checkEventFlu(LocalDate gameDate){
        //if((RandomNumberGenerator.getRandomInt(0, 9) == 0) && ((gameDate.getMonthValue() == 12) || (gameDate.getMonthValue() < 3)) && (this.eventFluDate == null)){
        if((RandomNumberGenerator.getRandomInt(0, 90) == 0) && ((gameDate.getMonthValue() == 12) || (gameDate.getMonthValue() < 3)) && (this.eventFluDate == null)){
            ProductionDepartment.getInstance().decreaseTotalEngineerQualityOfWorkRel(0.10);
            externalEvents.add(ExternalEvent.EVENT_12);
            this.eventFluDate = gameDate;
        }else if((this.eventFluDate != null) && (Period.between(this.eventFluDate, gameDate).getMonths() > 0)){
            this.eventFluDate = null;
            //TODO add parameter to increaseTotalEngineerQualityOfWorkRel()
            ProductionDepartment.getInstance().increaseTotalEngineerQualityOfWorkRel();
        }
    }

    /**
     * Checks if hurricanes, tornadoes, or earthquakes occurred, see p.98
     */
    private void checkEventHurricanesTornadoesEarthquakes(){
        if(CompanyEcoIndex.getInstance().checkEcoIndexBelowThreshold() && WarehousingDepartment.getInstance().checkWarehouseCapacityThreshold()){
            double probability = 0.1 / CompanyEcoIndex.getInstance().getEcoIndex().getIndex();
            if(RandomNumberGenerator.getRandomInt(0, (int)Math.round(1 / probability) - 1) == 0){
                WarehousingDepartment.getInstance().decreaseCapacity(2000);
                externalEvents.add(ExternalEvent.EVENT_13);
            }
        }
    }

    /**
     * Checks if a fire or flooding occurred, see p.98
     */
    private void checkEventFireFlooding(){
        if(WarehousingDepartment.getInstance().checkFreeStorageThreshold()){
            double probability = WarehousingDepartment.getInstance().getDaysSinceFreeStorageThreshold() * 0.01;
            if(probability > 1.0){
                probability = 1.0;
            }
            if(RandomNumberGenerator.getRandomInt(0, (int)Math.round(1 / probability) - 1) == 0){
                WarehousingDepartment.getInstance().decreaseStoredUnitsRel(0.30);
                externalEvents.add(ExternalEvent.EVENT_14);
            }

        }
    }

    //TODO Future work
    private void checkEventProblemsWithCustoms(){

    }

    /**
     * Checks if there was a change of power, see p.98
     * This event can only occur once in the game.
     */
    private void checkEventChangeOfPower(){
        if((RandomNumberGenerator.getRandomInt(0, 9999) == 0) && (!this.changeOfPower)){
            FinanceDepartment.getInstance().increaseTaxRate(0.05);
            externalEvents.add(ExternalEvent.EVENT_16);
            this.changeOfPower = true;
        }
    }

    /**
     * Checks if the event 'eco activists' occurred, see p.98
     * This event can only occur again if the impact of the last eco activists ended.
     * @param gameDate The current date in the game.
     */
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

    /**
     * Checks which external events occurred, adds them to the externalEventsHistory, and returns them, see p.98
     * @param gameDate The current date in the game.
     * @return Returns a list of all external events that occurred
     */
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

    public static ExternalEvents createInstance() {
        return new ExternalEvents();
    }
}
