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

    }

    public void updateCharts(String rowName, String[] yValues, String[] xNames){

    }

}
