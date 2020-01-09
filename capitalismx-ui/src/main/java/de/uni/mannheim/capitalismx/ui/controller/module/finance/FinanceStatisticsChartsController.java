package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FinanceStatisticsChartsController extends GameModuleController {

    @FXML
    LineChart<String, Number> cashChart;

    @FXML
    LineChart<String, Number> assetsChart;

    @FXML
    LineChart<String, Number> liabilitiesChart;

    @FXML
    LineChart<String, Number> netWorthChart;

    XYChart.Series<String, Number> cashSeries;
    XYChart.Series<String, Number> assetsSeries;
    XYChart.Series<String, Number> liabilitiesSeries;
    XYChart.Series<String, Number> netWorthSeries;

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //final CategoryAxis xAxisCash = new CategoryAxis();
        //final NumberAxis yAxisCash = new NumberAxis();
        //xAxisCash.setLabel("Last 12 Months");
        //cashChart = new LineChart<>(xAxisCash, yAxisCash);
        //XYChart.Series cashSeries = new XYChart.Series<Number, Number>();
        //cashSeries.setName("Test");

        cashSeries = new XYChart.Series();
        for(int i = 1; i < 13; i++){
            cashSeries.getData().add(new XYChart.Data(String.valueOf(i), 0));
        }
        assetsSeries = new XYChart.Series();
        for(int i = 1; i < 13; i++){
            assetsSeries.getData().add(new XYChart.Data(String.valueOf(i), 0));
        }
        liabilitiesSeries = new XYChart.Series();
        for(int i = 1; i < 13; i++){
            liabilitiesSeries.getData().add(new XYChart.Data(String.valueOf(i), 0));
        }
        netWorthSeries = new XYChart.Series();
        for(int i = 1; i < 13; i++){
            netWorthSeries.getData().add(new XYChart.Data(String.valueOf(i), 0));
        }

        cashChart.getData().add(cashSeries);
        assetsChart.getData().add(assetsSeries);
        liabilitiesChart.getData().add(liabilitiesSeries);
        netWorthChart.getData().add(netWorthSeries);
    }

    public void updateCharts(String rowName, String[] yValues, String[] xNames){
        Platform.runLater(new Runnable() {
            public void run() {
                switch (rowName){
                    case "cash":
                        for(int i = 0; i < yValues.length; i++){
                            cashSeries.getData().get(i).setXValue(xNames[i]);
                            cashSeries.getData().get(i).setYValue(Double.valueOf(yValues[i]));
                        }
                        break;
                    case "assets":
                        for(int i = 0; i < yValues.length; i++){
                            assetsSeries.getData().get(i).setXValue(xNames[i]);
                            assetsSeries.getData().get(i).setYValue(Double.valueOf(yValues[i]));
                        }
                        break;
                    case "liabilities":
                        for(int i = 0; i < yValues.length; i++){
                            liabilitiesSeries.getData().get(i).setXValue(xNames[i]);
                            liabilitiesSeries.getData().get(i).setYValue(Double.valueOf(yValues[i]));
                        }
                        break;
                    case "netWorth":
                        for(int i = 0; i < yValues.length; i++){
                            netWorthSeries.getData().get(i).setXValue(xNames[i]);
                            netWorthSeries.getData().get(i).setYValue(Double.valueOf(yValues[i]));
                        }
                        break;
                }
            }
        });
    }

}
