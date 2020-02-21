package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.finance.OperationsTableViewCell;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class OperationsTableController  implements Initializable {

    @FXML
    private VBox operationsVBox;

    private Map<String, OperationsTableViewCell> controllers;

    public OperationsTableController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllers = new HashMap<>();

        FXMLLoader headerLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/operations_table_cell.fxml"));
        try {
            headerLoader.setController(new OperationsTableViewCell(new String[]{"header", "", "0", "0", "0", "0"}, operationsVBox, controllers));
            Parent headerRow = headerLoader.load();
            OperationsTableViewCell headerController = headerLoader.getController();
            headerController.setHeaderRow();
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

                if(rowName.contains("Costs") || rowName.equals("tax")){
                    controller.setColor("-fx-red");
                }else if(rowName.equals("sales")){
                    controller.setColor("-fx-green");
                }

                operationsVBox.getChildren().add(headerRow);
                controllers.put(rowName, controller);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Region[] regions = new Region[5];
        for(int i = 0; i < regions.length; i++){
            regions[i] = new Region();
            regions[i].getStyleClass().add("separator_horizontal");
        }
        operationsVBox.getChildren().add(10, regions[0]);
        operationsVBox.getChildren().add(9, regions[1]);
        operationsVBox.getChildren().add(8, regions[2]);
        operationsVBox.getChildren().add(2, regions[3]);
        operationsVBox.getChildren().add(1, regions[4]);
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