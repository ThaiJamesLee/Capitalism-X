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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
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

        FXMLLoader headerLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/operations_table_cell.fxml"));
        try {
            headerLoader.setController(new OperationsTableViewCell(new String[]{"header", "", "0", "0", "0", "0"}, operationsVBox, controllers));
            Parent headerRow = headerLoader.load();
            OperationsTableViewCell headerController = headerLoader.getController();
            operationsVBox.getChildren().add(headerRow);
            controllers.put("header", headerController);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] rowNames = {"sales", "hrCosts", "warehouseCosts", "logisticsCosts", "productionCosts", "marketingCosts", "supportCosts",
                "ebit", "tax", "nopat"};
        for(String rowName : rowNames){
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/operations_table_cell.fxml"));
            try {
                loader.setController(new OperationsTableViewCell(new String[]{rowName, UIManager.getLocalisedString("operations.table." + rowName), "0", "0", "0", "0"}, operationsVBox, controllers));
                Parent headerRow = loader.load();
                OperationsTableViewCell controller = loader.getController();
                operationsVBox.getChildren().add(headerRow);
                controllers.put(rowName, controller);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*revenueListView.prefHeightProperty().bind(operationsVBox.heightProperty().divide(5));
        expensesListView.prefHeightProperty().bind(operationsVBox.heightProperty().divide(5));
        ebitListView.prefHeightProperty().bind(operationsVBox.heightProperty().divide(5));
        taxListView.prefHeightProperty().bind(operationsVBox.heightProperty().divide(5));
        nopatListView.prefHeightProperty().bind(operationsVBox.heightProperty().divide(5));*/
        //revenueListView.setPrefHeight(50);
        //taxListView.setPrefHeight(200);
        //taxListView.setStyle("-fx-background-color: red;");
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