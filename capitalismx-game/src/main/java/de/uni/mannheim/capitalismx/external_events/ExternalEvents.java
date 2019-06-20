package de.uni.mannheim.capitalismx.external_events;

import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author sdupper
 */
public class ExternalEvents {

    private static ExternalEvents instance;

    private ArrayList<ExternalEvent> externalEvents;

    private enum ExternalEvent{
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
        EVENT_19("Game OVer");

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

    }

    public static synchronized ExternalEvents getInstance() {
        if(ExternalEvents.instance == null) {
            ExternalEvents.instance = new ExternalEvents();
        }
        return ExternalEvents.instance;
    }

    private void checkEventProductionProblems(){
        if(Production.getInstance().checkProductionTechnologyBelowThreshold()){
            externalEvents.add(ExternalEvent.EVENT_1);
        }
    }

    private void checkEventNewTechnology(){
        if(Production.getInstance().checkBaseQualityAboveThreshold()){
            Customer.getInstance().increaseCustomerSatisfactionRel(0.3);
            externalEvents.add(ExternalEvent.EVENT_2);
        }
    }

    private void checkEventCompanyAcquisition(){
        if(Finance.getInstance().checkIncreasingNopat()){
            Finance.getInstance().acquireCompany();
            externalEvents.add(ExternalEvent.EVENT_3);
        }
    }

    private void checkEventCompanyOvertakesMarketShare(){
        if(RandomNumberGenerator.getRandomInt(0, 49) == 0){
            Finance.getInstance().decreaseNopatRelPermanently(0.10);
            externalEvents.add(ExternalEvent.EVENT_4);
        }
    }

    private void checkEventBrandReputationPlunges(){
        if(Customer.getInstance().checkCustomerSatisfactionBelowThreshold()){
            Marketing.getInstance().decreaseCompanyImageRel(0.20);
            externalEvents.add(ExternalEvent.EVENT_5);
        }
    }

    private void checkEventComputerVirusAttacks(){
        if(RandomNumberGenerator.getRandomInt(0, 19) == 0){
            Production.getInstance().decreaseProcessAutomationRel(0.50);
            externalEvents.add(ExternalEvent.EVENT_6);
        }
    }

    private void checkEventTaxChanges(){
        if(RandomNumberGenerator.getRandomInt(0, 19) == 0){
            if(RandomNumberGenerator.getRandomInt(0, 1) == 0){
                Finance.getInstance().increaseTaxRateRel(0.02);
                ExternalEvent.EVENT_7.setIncrease(true);
            }else{
                Finance.getInstance().decreaseTaxRateRel(0.02);
                ExternalEvent.EVENT_7.setIncrease(false);
            }
            externalEvents.add(ExternalEvent.EVENT_7);
        }
    }

    private void checkEventStricterEcoLaws(){
        if(CompanyEcoIndex.getInstance().checkEcoIndexBelowThreshold()){
            if(CompanyEcoIndex.getInstance().checkGameOver()){
                //TODO End game
                externalEvents.add(ExternalEvent.EVENT_19);
            }else{
                Finance.getInstance().nopatFine(0.10);
                externalEvents.add(ExternalEvent.EVENT_8);
            }
        }
    }

    private void checkEventInflationChanges(){
        //TODO probability between 0 and 2
        if(RandomNumberGenerator.getRandomInt(0, 49) == 0){
            if(RandomNumberGenerator.getRandomInt(0, 1) == 0){
                Finance.getInstance().increaseNopatRel(0.02);
                ExternalEvent.EVENT_9.setIncrease(true);
            }else{
                Finance.decreaseNopatRel(0.02);
                ExternalEvent.EVENT_9.setIncrease(false);
            }
            externalEvents.add(ExternalEvent.EVENT_9);
        }
    }

    private void checkEventStealing(){
        if(Marketing.getInstance().checkTotalJobSatisfactionBelowThreshold(0.50)){
            int numberOfEmployees = 0;
            for(Map.Entry<EmployeeType, Team> team : HRDepartment.getInstance().getTeams().entrySet()){
                numberOfEmployees += team.getValue().size();
            }
            Finance.getInstance().decreaseNopat(numberOfEmployees * 5000);
            externalEvents.add(ExternalEvent.EVENT_10);
        }
    }

    private void checkEventStrikes(){
        if(Marketing.getInstance().checkTotalJobSatisfactionBelowThreshold(0.10)){
            Customer.getInstance().decresePeriodicDemandAmountRel(0.8);
            externalEvents.add(ExternalEvent.EVENT_11);
        }
    }

    private void checkEventFlu(){
        if(RandomNumberGenerator.getRandomInt(0, 9) == 0){
            Production.getInstance().decreaseTotalEngineerQualityOfWorkRel(0.10);
            externalEvents.add(ExternalEvent.EVENT_12);
        }
    }

    private void checkEventHurricanesTornadoesEarthquakes(){
        if(CompanyEcoIndex.getInstance().checkEcoIndexBelowThreshold2()){
            Warehousing.getInstance().decreaseCapacity(2000);
            externalEvents.add(ExternalEvent.EVENT_13);
        }
    }

    private void checkEvenFireFlooding(){
        if(Warehousing.getInstance().checkFreeStorageTheshold()){
            if(RandomNumberGenerator.getRandomInt(0, (1 / (Warehousing.getInstance().getDaysSinceFreeStorageThreshold() * 0.01)) - 1) == 0){
                Warehousing.getInstance().decreaseStoredUnitsRel(0.30);
                externalEvents.add(ExternalEvent.EVENT_14);
            }

        }
    }

    //TODO Future work
    private void checkEventProblemsWithCustoms(){

    }

    //TODO
    private void checkEventChangeOfPower(){
        //if(RandomNumberGenerator.getRandomInt())
    }

    private void checkEventEcoActivists(){
        if(Logistics.getInstance().checkEcoIndexFleetBelowThreshold()){
            Logistics.getInstance().decreaseCapacityFleetRel(0.70);
            externalEvents.add(ExternalEvent.EVENT_17);
        }
    }

    //TODO Future work
    private void checkEventTensions(){

    }

    public ArrayList<ExternalEvent> checkEvents(){
        this.checkEventProductionProblems();
        this.checkEventNewTechnology();
        this.checkEventCompanyAcquisition();
        this.checkEventCompanyOvertakesMarketShare();
        this.checkEventBrandReputationPlunges();
        this.checkEventComputerVirusAttacks();
        this.checkEventTaxChanges();
        this.checkEventStricterEcoLaws();
        this.checkEventInflationChanges();
        this.checkEventStealing();
        this.checkEventStrikes();
        this.checkEventFlu();
        this.checkEventHurricanesTornadoesEarthquakes();
        this.checkEvenFireFlooding();
        this.checkEventProblemsWithCustoms();
        this.checkEventChangeOfPower();
        this.checkEventEcoActivists();
        this.checkEventTensions();
        return this.externalEvents;
    }
}
