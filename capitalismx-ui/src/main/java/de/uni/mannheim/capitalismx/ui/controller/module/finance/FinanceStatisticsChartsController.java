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

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        final CategoryAxis xAxisCash = new CategoryAxis();
        final NumberAxis yAxisCash = new NumberAxis();
        //xAxisCash.setLabel("Last 12 Months");
        //cashChart = new LineChart<>(xAxisCash, yAxisCash);
        //XYChart.Series cashSeries = new XYChart.Series<Number, Number>();
        cashSeries = new XYChart.Series();
        //cashSeries.setName("Test");
        for(int i = 1; i < 13; i++){
            cashSeries.getData().add(new XYChart.Data(String.valueOf(i), 0));
        }

        cashChart.getData().add(cashSeries);
    }

    public void updateCharts(String rowName, String[] yValues, String[] xNames){
        Platform.runLater(new Runnable() {
            public void run() {
                //cashSeries.getData().add(new XYChart.Data("2", 14));
                if(rowName.equals("cash")){
                    //cashSeries.getData().clear();
                    //for(int i = 0; i < yValues.length; i++){
                    //    cashSeries.getData().add(new XYChart.Data(xNames[i], Double.valueOf(yValues[i])));
                    //}

                    for(int i = 0; i < yValues.length; i++){
                        cashSeries.getData().get(i).setXValue(xNames[i]);
                        cashSeries.getData().get(i).setYValue(Double.valueOf(yValues[i]));
                    }
                }
            }
        });
    }

}
