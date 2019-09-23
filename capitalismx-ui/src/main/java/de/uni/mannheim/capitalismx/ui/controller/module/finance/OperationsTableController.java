package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.controller.module.hr.EmployeeListController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class OperationsTableController  extends GameModuleController {

    @FXML
    private TableView<OperationsTableEntry> operationsTable;

    public OperationsTableController() {
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operationsTable.setPlaceholder(new Label("The table is currently empty!"));

        TableColumn<OperationsTableEntry, String> rowName = new TableColumn<OperationsTableEntry, String>("");
        TableColumn<OperationsTableEntry, String> col1 = new TableColumn<OperationsTableEntry, String>("Q");
        TableColumn<OperationsTableEntry, String> col2 = new TableColumn<OperationsTableEntry, String>("Q");
        TableColumn<OperationsTableEntry, String> col3 = new TableColumn<OperationsTableEntry, String>("Q");
        TableColumn<OperationsTableEntry, String> col4 = new TableColumn<OperationsTableEntry, String>("Q");

        rowName.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OperationsTableEntry, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<OperationsTableEntry, String> param) {
                        return param.getValue().rowName;
                    }
                });
        col1.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OperationsTableEntry, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<OperationsTableEntry, String> param) {
                        return param.getValue().col1;
                    }
                });
        col2.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OperationsTableEntry, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<OperationsTableEntry, String> param) {
                        return param.getValue().col2;
                    }
                });
        col3.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OperationsTableEntry, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<OperationsTableEntry, String> param) {
                        return param.getValue().col3;
                    }
                });
        col4.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OperationsTableEntry, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<OperationsTableEntry, String> param) {
                        return param.getValue().col4;
                    }
                });

        operationsTable.getColumns().addAll(rowName, col1, col2, col3, col4);

        ObservableList<OperationsTableEntry> data = FXCollections.observableArrayList();
        data.add(new OperationsTableEntry("Sales", 0, 0, 0 ,0));
        data.add(new OperationsTableEntry("Salaries", 0, 0, 0 ,0));
        data.add(new OperationsTableEntry("Material", 0, 0, 0 ,0));
        data.add(new OperationsTableEntry("Machines", 0, 0, 0 ,0));
        data.add(new OperationsTableEntry("Logistics", 0, 0, 0 ,0));
        data.add(new OperationsTableEntry("Lobbying", 0, 0, 0 ,0));
        data.add(new OperationsTableEntry("Marketing", 0, 0, 0 ,0));
        data.add(new OperationsTableEntry("EBIT", 0, 0, 0 ,0));
        data.add(new OperationsTableEntry("Loan Interest", 0, 0, 0 ,0));
        data.add(new OperationsTableEntry("Taxes", 0, 0, 0 ,0));
        data.add(new OperationsTableEntry("NOPAT", 0, 0, 0 ,0));
        operationsTable.setItems(data);
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

}