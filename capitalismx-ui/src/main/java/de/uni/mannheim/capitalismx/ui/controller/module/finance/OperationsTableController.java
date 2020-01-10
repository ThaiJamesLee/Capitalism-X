package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class OperationsTableController  extends GameModuleController {

    @FXML
    private TableView<Map.Entry<String, OperationsTableEntry>> operationsTable;

    private Map<String, OperationsTableEntry> data;

    public OperationsTableController() {
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operationsTable.setPlaceholder(new Label(UIManager.getLocalisedString("operations.table.placeholder")));

        TableColumn<Map.Entry<String, OperationsTableEntry>, String> rowName = new TableColumn<>("");
        TableColumn<Map.Entry<String, OperationsTableEntry>, String> col1 = new TableColumn<>("Q");
        TableColumn<Map.Entry<String, OperationsTableEntry>, String> col2 = new TableColumn<>("Q");
        TableColumn<Map.Entry<String, OperationsTableEntry>, String> col3 = new TableColumn<>("Q");
        TableColumn<Map.Entry<String, OperationsTableEntry>, String> col4 = new TableColumn<>("Q");

        rowName.prefWidthProperty().bind(operationsTable.widthProperty().divide(5));
        col1.prefWidthProperty().bind(operationsTable.widthProperty().divide(5));
        col2.prefWidthProperty().bind(operationsTable.widthProperty().divide(5));
        col3.prefWidthProperty().bind(operationsTable.widthProperty().divide(5));
        col4.prefWidthProperty().bind(operationsTable.widthProperty().divide(5));

        //TODO ensures that height specified in GameModuleDefinition is used
        operationsTable.setPrefHeight(1000);

        col1.setStyle( "-fx-alignment: CENTER;");
        col2.setStyle( "-fx-alignment: CENTER;");
        col3.setStyle( "-fx-alignment: CENTER;");
        col4.setStyle( "-fx-alignment: CENTER;");

        rowName.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Map.Entry<String, OperationsTableEntry>, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, OperationsTableEntry>, String> param) {
                        return param.getValue().getValue().rowName;
                    }
                });
        col1.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Map.Entry<String, OperationsTableEntry>, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, OperationsTableEntry>, String> param) {
                        return param.getValue().getValue().col1;
                    }
                });
        col2.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Map.Entry<String, OperationsTableEntry>, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, OperationsTableEntry>, String> param) {
                        return param.getValue().getValue().col2;
                    }
                });
        col3.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Map.Entry<String, OperationsTableEntry>, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, OperationsTableEntry>, String> param) {
                        return param.getValue().getValue().col3;
                    }
                });
        col4.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Map.Entry<String, OperationsTableEntry>, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, OperationsTableEntry>, String> param) {
                        return param.getValue().getValue().col4;
                    }
                });

        operationsTable.getColumns().addAll(rowName, col1, col2, col3, col4);


        data = new LinkedHashMap<>();
        data.put("sales", new OperationsTableEntry(UIManager.getLocalisedString("operations.table.sales"), 0, 0, 0 ,0));
        data.put("hrCosts", new OperationsTableEntry(UIManager.getLocalisedString("operations.table.hrCosts"), 0, 0, 0 ,0));
        data.put("warehouseCosts", new OperationsTableEntry(UIManager.getLocalisedString("operations.table.warehouseCosts"), 0, 0, 0 ,0));
        data.put("logisticsCosts", new OperationsTableEntry(UIManager.getLocalisedString("operations.table.logisticsCosts"), 0, 0, 0 ,0));
        data.put("productionCosts", new OperationsTableEntry(UIManager.getLocalisedString("operations.table.productionCosts"), 0, 0, 0 ,0));
        data.put("marketingCosts", new OperationsTableEntry(UIManager.getLocalisedString("operations.table.marketingCosts"), 0, 0, 0 ,0));
        data.put("supportCosts", new OperationsTableEntry(UIManager.getLocalisedString("operations.table.supportCosts"), 0, 0, 0 ,0));
        data.put("ebit", new OperationsTableEntry(UIManager.getLocalisedString("operations.table.ebit"), 0, 0, 0 ,0));
        data.put("tax", new OperationsTableEntry(UIManager.getLocalisedString("operations.table.tax"), 0, 0, 0 ,0));
        data.put("nopat", new OperationsTableEntry(UIManager.getLocalisedString("operations.table.nopat"), 0, 0, 0 ,0));
        //data.put("loan_interest", new OperationsTableEntry(UIManager.getLocalisedString("operations.table.loanInterest"), 0, 0, 0 ,0));

        ObservableList<Map.Entry<String,OperationsTableEntry>> dataObservable = FXCollections.observableArrayList(data.entrySet());
        operationsTable.setItems(dataObservable);
    }


    class OperationsTableEntry{
        final SimpleStringProperty rowName;
        final SimpleStringProperty col1;
        final SimpleStringProperty col2;
        final SimpleStringProperty col3;
        final SimpleStringProperty col4;

        public OperationsTableEntry(String rowName, double col1, double col2, double col3, double col4){
            this.rowName = new SimpleStringProperty(rowName);
            this.col1 = new SimpleStringProperty(String.valueOf(col1));
            this.col2 = new SimpleStringProperty(String.valueOf(col2));
            this.col3 = new SimpleStringProperty(String.valueOf(col3));
            this.col4 = new SimpleStringProperty(String.valueOf(col4));
        }

    }

    public void updateTable(String rowName, String[] cols, String[] colNames){
        Platform.runLater(new Runnable() {
            public void run() {
                data.get(rowName).col1.setValue(cols[0]);
                data.get(rowName).col2.setValue(cols[1]);
                data.get(rowName).col3.setValue(cols[2]);
                data.get(rowName).col4.setValue(cols[3]);

                for(int i = 0; i < colNames.length; i++){
                    operationsTable.getColumns().get(i + 1).setText(colNames[i]);
                }
            }
        });
    }

}