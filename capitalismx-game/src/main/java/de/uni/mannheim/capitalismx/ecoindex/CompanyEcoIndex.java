package de.uni.mannheim.capitalismx.ecoindex;

import java.util.ArrayList;

public class CompanyEcoIndex {
    private static CompanyEcoIndex instance;

    private EcoIndex ecoIndex;
    private final int ECO_FLAT_TAX = 10000;

    private enum EcoIndex{
        GOOD(5, 80, 100),
        MODERATE(4, 60, 79),
        UNHEALTHY(3, 40, 59),
        VERY_UNHEALTHY(2, 20, 39),
        HAZARDOUS(1, 0, 19);

        private int index;
        private int min;
        private int max;
        private int points;

        private EcoIndex(int index, int min, int max){
            this.index = index;
            this.min = min;
            this.max = max;
        }

        public void setPoints(int points){
            this.points = points;
        }

        public int getIndex() {
            return this.index;
        }

        public int getMax() {
            return this.max;
        }

        public int getMin() {
            return this.min;
        }

        public int getPoints() {
            return this.points;
        }
    }

    private CompanyEcoIndex(){

    }

    public static synchronized CompanyEcoIndex getInstance() {
        if(CompanyEcoIndex.instance == null) {
            CompanyEcoIndex.instance = new CompanyEcoIndex();
        }
        return CompanyEcoIndex.instance;
    }

    private void checkMachinery(){
        ArrayList<Machinery> machines = Production.getInstance().getMachines();
        for(Machinery machinery : machines){
            machinery.depreciateMachinery(false);
            if(machinery.getYearsSinceLastInvestment() > 5){
                this.decreaseEcoIndex(1);
            }
        }
    }

    private void checkVehicles(){
        if(Logistics.getInstance().getInternalFleet().calculateExoIndexFleet() < 3){
            this.decreaseEcoIndex(1);
        }
    }

    //TODO
    private void checkRegulatoryLaws(){

    }

    //TODO
    private void checkEnvironmentalDisaster(){
        this.decreaseEcoIndex(2);
    }

    //TODO Marketing to increase ecoIndex

    //TODO Determine suitable additionalEcoTax values
    private double calculateEcoTax(){
        double additionalEcoTax = 0;
        if(this.ecoIndex.getIndex() ==4){
            additionalEcoTax = this.ECO_FLAT_TAX * 0.01;
        }else if(this.ecoIndex.getIndex() == 3){
            additionalEcoTax = this.ECO_FLAT_TAX * 0.03;
        }else if(this.ecoIndex.getIndex() == 2){
            additionalEcoTax = this.ECO_FLAT_TAX * 0.06;
        }
        return this.ECO_FLAT_TAX + additionalEcoTax;
    }

    //TODO
    private double calculateEcoCosts(){
        return this.calculateEcoTax() - (Production.getInstance().getProductionTechnology().getRange() + component eL) * 1000;
    }

    private void decreaseEcoIndex(int points){
        int newPoints = this.ecoIndex.getPoints() - points;
        if(newPoints < this.ecoIndex.getMin()){
            if(this.ecoIndex.getIndex() > 3){
                this.ecoIndex = EcoIndex.values()[this.ecoIndex.ordinal() + 1];
            }else{
                //TODO Game Over event

            }
        }
        this.ecoIndex.setPoints(newPoints);
    }

    private void increaseEcoIndex(int points){
        int newPoints = this.ecoIndex.getPoints() + points;
        if(newPoints > this.ecoIndex.getMax()){
            if(this.ecoIndex.getIndex() < 5){
                this.ecoIndex = EcoIndex.values()[this.ecoIndex.ordinal() - 1];
            }
        }
        this.ecoIndex.setPoints(newPoints);
    }

    //TODO unclear if points or index should be used according to documentation
    public boolean checkEcoIndexBelowThreshold(){
        if(this.ecoIndex.getPoints() < 40){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkGameOver(){
        if(this.ecoIndex.getPoints() < 10){
            return true;
        }else{
            return false;
        }
    }

    public EcoIndex getEcoIndex() {
        return this.ecoIndex;
    }
}
