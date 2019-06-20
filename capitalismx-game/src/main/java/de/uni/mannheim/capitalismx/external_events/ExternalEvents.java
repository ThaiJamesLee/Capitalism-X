package de.uni.mannheim.capitalismx.external_events;

import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author sdupper
 */
public class ExternalEvents {
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

    private void checkEventProductionProblems(Production production){
        if(production.checkProductionTechnologyBelowThreshold()){
            externalEvents.add(ExternalEvent.EVENT_1);
        }
    }

    private void checkEventNewTechnology(Production production, Customer customer){
        if(production.checkBaseQualityAboveThreshold()){
            customer.increaseCustomerSatisfactionRel(0.3);
            externalEvents.add(ExternalEvent.EVENT_2);
        }
    }

    private void checkEventCompanyAcquisition(Finance finance){
        if(finance.checkIncreasingNopat()){
            finance.acquireCompany();
            externalEvents.add(ExternalEvent.EVENT_3);
        }
    }

    private void checkEventCompanyOvertakesMarketShare(Finance finance){
        if(RandomNumberGenerator.getRandomInt(0, 49) == 0){
            finance.decreaseNopatRelPermanently(0.10);
            externalEvents.add(ExternalEvent.EVENT_4);
        }
    }

    private void checkEventBrandReputationPlunges(Customer customer, Marketing marketing){
        if(customer.checkCustomerSatisfactionBelowThreshold()){
            marketing.decreaseCompanyImageRel(0.20);
            externalEvents.add(ExternalEvent.EVENT_5);
        }
    }

    private void checkEventComputerVirusAttacks(Production production){
        if(RandomNumberGenerator.getRandomInt(0, 19) == 0){
            production.decreaseProcessAutomationRel(0.50);
            externalEvents.add(ExternalEvent.EVENT_6);
        }
    }

    private void checkEventTaxChanges(Finance finance){
        if(RandomNumberGenerator.getRandomInt(0, 19) == 0){
            if(RandomNumberGenerator.getRandomInt(0, 1) == 0){
                finance.increaseTaxRateRel(0.02);
                ExternalEvent.EVENT_7.setIncrease(true);
            }else{
                finance.decreaseTaxRateRel(0.02);
                ExternalEvent.EVENT_7.setIncrease(false);
            }
            externalEvents.add(ExternalEvent.EVENT_7);
        }
    }

    private void checkEventStricterEcoLaws(CompanyEcoIndex companyEcoIndex, Finance finance){
        if(companyEcoIndex.checkEcoIndexBelowThreshold()){
            if(companyEcoIndex.checkGameOver()){
                //TODO End game
                externalEvents.add(ExternalEvent.EVENT_19);
            }else{
                finance.decreaseNopatRel(0.10);
                externalEvents.add(ExternalEvent.EVENT_8);
            }
        }
    }

    private void checkEventInflationChanges(Finance finance){
        //TODO probability between 0 and 2
        if(RandomNumberGenerator.getRandomInt(0, 49) == 0){
            if(RandomNumberGenerator.getRandomInt(0, 1) == 0){
                finance.increaseNopatRel(0.02);
                ExternalEvent.EVENT_9.setIncrease(true);
            }else{
                finance.decreaseNopatRel(0.02);
                ExternalEvent.EVENT_9.setIncrease(false);
            }
            externalEvents.add(ExternalEvent.EVENT_9);
        }
    }

    private void checkEventStealing(Marketing marketing, Finance finance, HRDepartment hrDepartment){
        if(marketing.checkTotalJobSatisfactionBelowThreshold(0.50)){
            int numberOfEmployees = 0;
            for(Map.Entry<EmployeeType, Team> team : hrDepartment.getTeams().entrySet()){
                numberOfEmployees += team.getValue().size();
            }
            finance.decreaseNopat(numberOfEmployees * 5000);
            externalEvents.add(ExternalEvent.EVENT_10);
        }
    }

    private void checkEventStrikes(Marketing marketing, Customer customer){
        if(marketing.checkTotalJobSatisfactionBelowThreshold(0.10)){
            customer.decresePeriodicDemandAmountRel(0.8);
            externalEvents.add(ExternalEvent.EVENT_11);
        }
    }

    private void checkEventFlu(Production production){
        if(RandomNumberGenerator.getRandomInt(0, 9) == 0){
            production.decreaseTotalEngineerQualityOfWorkRel(0.10);
            externalEvents.add(ExternalEvent.EVENT_12);
        }
    }

    private void checkEventHurricanesTornadoesEarthquakes(CompanyEcoIndex companyEcoIndex, Warehouse warehouse){
        if(companyEcoIndex.checkEcoIndexBelowThreshold2()){
            warehouse.decreaseCapacity(2000);
            externalEvents.add(ExternalEvent.EVENT_13);
        }
    }

    private void checkEvenFireFlooding(Warehouse warehouse){
        if(warehouse.checkFreeStorageTheshold()){
            warehouse.decreaseStoredUnitsRel(0.30);
            externalEvents.add(ExternalEvent.EVENT_14);
        }
    }

    //TODO Future work
    private void checkEventProblemsWithCustoms(){

    }

    //TODO
    private void checkEventChangeOfPower(Finance finance){
        //if(RandomNumberGenerator.getRandomInt())
    }

    private void checkEventEcoActivists(Logistics logistics){
        if(logistics.checkEcoIndexFleetBelowThreshold()){
            logistics.decreaseCapacityFleetRel(0.70);
            externalEvents.add(ExternalEvent.EVENT_17);
        }
    }

    //TODO Future work
    private void checkEventTensions(){

    }

    public ArrayList<ExternalEvent> checkEvents(Production production, Customer customer, Finance finance, Marketing marketing, CompanyEcoIndex companyEcoIndex, HRDepartment hrDepartment,
                                                Warehouse warehouse, Logistics logistics){
        this.checkEventProductionProblems(production);
        this.checkEventNewTechnology(production, customer);
        this.checkEventCompanyAcquisition(finance);
        this.checkEventCompanyOvertakesMarketShare(finance);
        this.checkEventBrandReputationPlunges(customer, marketing);
        this.checkEventComputerVirusAttacks(production);
        this.checkEventTaxChanges(finance);
        this.checkEventStricterEcoLaws(companyEcoIndex, finance);
        this.checkEventInflationChanges(finance);
        this.checkEventStealing(marketing, finance, hrDepartment);
        this.checkEventStrikes(marketing, customer);
        this.checkEventFlu(production);
        this.checkEventHurricanesTornadoesEarthquakes(companyEcoIndex, warehouse);
        this.checkEvenFireFlooding(warehouse);
        this.checkEventProblemsWithCustoms();
        this.checkEventChangeOfPower(finance);
        this.checkEventEcoActivists(logistics);
        this.checkEventTensions();
        return this.externalEvents;
    }
}
