package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import java.net.URL;
import java.util.ResourceBundle;

public class FinanceStatisticsChartsController extends GameModuleController {

    @FXML
    BarChart<CategoryAxis, NumberAxis> salesChart;

    @FXML
    BarChart<CategoryAxis, NumberAxis> salariesChart;

    @FXML
    BarChart<CategoryAxis, NumberAxis> loansChart;

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

}
