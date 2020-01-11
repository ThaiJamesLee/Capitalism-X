package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.finance.OperationsTableViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.controller.module.hr.EmployeeListController;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;

public class OperationsTableController  extends GameModuleController {

    @FXML
    private VBox operationsVBox;

    private Map<String, OperationsTableViewCell> controllers;

    public OperationsTableController() {
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllers = new HashMap<>();

        ListView<String[]> headerListView = new ListView<>();
        ObservableList<String[]> headerList = FXCollections.observableArrayList();
        headerListView.setItems(headerList);
        headerListView.setCellFactory(operationsListView -> new OperationsTableViewCell(operationsVBox, controllers));
        headerList.add(new String[]{"header", "", "0", "0", "0", "0"});

        ListView<String[]> revenueListView = new ListView<>();
        ObservableList<String[]> revenueList = FXCollections.observableArrayList();
        revenueListView.setItems(revenueList);
        revenueListView.setCellFactory(operationsListView -> new OperationsTableViewCell(operationsVBox, controllers));
        revenueList.add(new String[]{"sales", UIManager.getLocalisedString("operations.table.sales"), "0", "0", "0", "0"});

        /*ListView<String[]> expensesListView = new ListView<>();
        ObservableList<String[]> expensesList = FXCollections.observableArrayList();
        expensesListView.setItems(expensesList);
        expensesListView.setCellFactory(operationsListView -> new OperationsTableViewCell(operationsVBox, controllers));
        expensesList.add(new String[]{"hrCosts", UIManager.getLocalisedString("operations.table.hrCosts"), "0", "0", "0", "0"});
        expensesList.add(new String[]{"warehouseCosts", UIManager.getLocalisedString("operations.table.warehouseCosts"), "0", "0", "0", "0"});
        expensesList.add(new String[]{"logisticsCosts", UIManager.getLocalisedString("operations.table.logisticsCosts"), "0", "0", "0", "0"});
        expensesList.add(new String[]{"productionCosts", UIManager.getLocalisedString("operations.table.productionCosts"), "0", "0", "0", "0"});
        expensesList.add(new String[]{"marketingCosts", UIManager.getLocalisedString("operations.table.marketingCosts"), "0", "0", "0", "0"});
        expensesList.add(new String[]{"supportCosts", UIManager.getLocalisedString("operations.table.supportCosts"), "0", "0", "0", "0"});*/

        ListView<String[]> hrCostsListView = new ListView<>();
        ObservableList<String[]> hrCostsList = FXCollections.observableArrayList();
        hrCostsListView.setItems(hrCostsList);
        hrCostsListView.setCellFactory(operationsListView -> new OperationsTableViewCell(operationsVBox, controllers));
        hrCostsList.add(new String[]{"hrCosts", UIManager.getLocalisedString("operations.table.hrCosts"), "0", "0", "0", "0"});

        ListView<String[]> warehouseCostsListView = new ListView<>();
        ObservableList<String[]> warehouseCostsList = FXCollections.observableArrayList();
        warehouseCostsListView.setItems(warehouseCostsList);
        warehouseCostsListView.setCellFactory(operationsListView -> new OperationsTableViewCell(operationsVBox, controllers));
        warehouseCostsList.add(new String[]{"warehouseCosts", UIManager.getLocalisedString("operations.table.warehouseCosts"), "0", "0", "0", "0"});

        ListView<String[]> logisticsCostsListView = new ListView<>();
        ObservableList<String[]> logisticsCostsList = FXCollections.observableArrayList();
        logisticsCostsListView.setItems(logisticsCostsList);
        logisticsCostsListView.setCellFactory(operationsListView -> new OperationsTableViewCell(operationsVBox, controllers));
        logisticsCostsList.add(new String[]{"logisticsCosts", UIManager.getLocalisedString("operations.table.logisticsCosts"), "0", "0", "0", "0"});

        ListView<String[]> productionCostsListView = new ListView<>();
        ObservableList<String[]> productionCostsList = FXCollections.observableArrayList();
        productionCostsListView.setItems(productionCostsList);
        productionCostsListView.setCellFactory(operationsListView -> new OperationsTableViewCell(operationsVBox, controllers));
        productionCostsList.add(new String[]{"productionCosts", UIManager.getLocalisedString("operations.table.productionCosts"), "0", "0", "0", "0"});

        ListView<String[]> marketingCostsListView = new ListView<>();
        ObservableList<String[]> marketingCostsList = FXCollections.observableArrayList();
        marketingCostsListView.setItems(marketingCostsList);
        marketingCostsListView.setCellFactory(operationsListView -> new OperationsTableViewCell(operationsVBox, controllers));
        marketingCostsList.add(new String[]{"marketingCosts", UIManager.getLocalisedString("operations.table.marketingCosts"), "0", "0", "0", "0"});

        ListView<String[]> supportCostsListView = new ListView<>();
        ObservableList<String[]> supportCostsList = FXCollections.observableArrayList();
        supportCostsListView.setItems(supportCostsList);
        supportCostsListView.setCellFactory(operationsListView -> new OperationsTableViewCell(operationsVBox, controllers));
        supportCostsList.add(new String[]{"supportCosts", UIManager.getLocalisedString("operations.table.supportCosts"), "0", "0", "0", "0"});

        ListView<String[]> ebitListView = new ListView<>();
        ObservableList<String[]> ebitList = FXCollections.observableArrayList();
        ebitListView.setItems(ebitList);
        ebitListView.setCellFactory(operationsListView -> new OperationsTableViewCell(operationsVBox, controllers));
        ebitList.add(new String[]{"ebit", UIManager.getLocalisedString("operations.table.ebit"), "0", "0", "0", "0"});

        ListView<String[]> taxListView = new ListView<>();
        ObservableList<String[]> taxList = FXCollections.observableArrayList();
        taxListView.setItems(taxList);
        taxListView.setCellFactory(operationsListView -> new OperationsTableViewCell(operationsVBox, controllers));
        taxList.add(new String[]{"tax", UIManager.getLocalisedString("operations.table.tax"), "0", "0", "0", "0"});

        ListView<String[]> nopatListView = new ListView<>();
        ObservableList<String[]> nopatList = FXCollections.observableArrayList();
        nopatListView.setItems(nopatList);
        nopatListView.setCellFactory(operationsListView -> new OperationsTableViewCell(operationsVBox, controllers));
        nopatList.add(new String[]{"nopat", UIManager.getLocalisedString("operations.table.nopat"), "0", "0", "0", "0"});

        /*revenueListView.prefHeightProperty().bind(operationsVBox.heightProperty().divide(5));
        expensesListView.prefHeightProperty().bind(operationsVBox.heightProperty().divide(5));
        ebitListView.prefHeightProperty().bind(operationsVBox.heightProperty().divide(5));
        taxListView.prefHeightProperty().bind(operationsVBox.heightProperty().divide(5));
        nopatListView.prefHeightProperty().bind(operationsVBox.heightProperty().divide(5));*/
        //revenueListView.setPrefHeight(50);
        //taxListView.setPrefHeight(200);
        //taxListView.setStyle("-fx-background-color: red;");

        operationsVBox.getChildren().add(headerListView);
        operationsVBox.getChildren().add(revenueListView);
        //operationsVBox.getChildren().add(expensesListView);
        operationsVBox.getChildren().add(hrCostsListView);
        operationsVBox.getChildren().add(warehouseCostsListView);
        operationsVBox.getChildren().add(logisticsCostsListView);
        operationsVBox.getChildren().add(productionCostsListView);
        operationsVBox.getChildren().add(marketingCostsListView);
        operationsVBox.getChildren().add(supportCostsListView);

        operationsVBox.getChildren().add(ebitListView);
        operationsVBox.getChildren().add(taxListView);
        operationsVBox.getChildren().add(nopatListView);
    }


    public void updateTable(String rowName, String[] cols, String[] colNames){
        Platform.runLater(new Runnable() {
            public void run() {
                OperationsTableViewCell controllerHeader = controllers.get("header");
                if(controllerHeader != null){
                    controllerHeader.setLabel2(colNames[0]);
                    controllerHeader.setLabel3(colNames[1]);
                    controllerHeader.setLabel4(colNames[2]);
                    controllerHeader.setLabel5(colNames[3]);
                }

                OperationsTableViewCell controller = controllers.get(rowName);
                if(controller != null){
                    controller.setLabel2(cols[0]);
                    controller.setLabel3(cols[1]);
                    controller.setLabel4(cols[2]);
                    controller.setLabel5(cols[3]);
                }
            }
        });
    }

}