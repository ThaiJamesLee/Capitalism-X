package de.uni.mannheim.capitalismx.ui.components.finance;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This class represents a row in the operations table (quarterly figures) in the finance UI, e.g., the HR costs in the
 * last 4 quarters.
 *
 * @author sdupper
 */
public class OperationsTableViewCell implements Initializable {

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    @FXML
    private GridPane gridPane;

    /**
     * The parent vBox of this OperationsTableViewCell instance.
     */
    private VBox vBox;

    /**
     * The values for all columns in this row.
     */
    private String[] values;

    /**
     * The name of this row.
     */
    private String rowName;

    /**
     * The map of all OperationsTableViewCell controllers in the parent vBox.
     */
    private Map<String, OperationsTableViewCell> controllers;

    /**
     * Constructor
     * @param values The values for all columns in this row.
     * @param vBox The parent vBox of this OperationsTableViewCell instance.
     * @param controllers The map of all OperationsTableViewCell controllers in the parent vBox.
     */
    public OperationsTableViewCell(String[] values, VBox vBox, Map<String, OperationsTableViewCell> controllers){
        super();
        this.vBox = vBox;
        this.controllers = controllers;
        this.values = values;
    }

    public String getRowName() {
        return this.rowName;
    }

    public void setLabel1(String text){
        this.label1.setText(text);
    }

    public void setLabel2(String text){
        this.label2.setText(text);
    }

    public void setLabel3(String text){
        this.label3.setText(text);
    }

    public void setLabel4(String text){
        this.label4.setText(text);
    }

    public void setLabel5(String text){
        this.label5.setText(text);
    }

    /*
     * Adds this controller to the map of controllers in the parent vBox and ensures that all columns have the same
     * width and use the complete width of the parent vBox. Moreover, the row name and labels are initialized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controllers.put(values[0], this);

        //gridPane.prefWidthProperty().bind(vBox.prefWidthProperty());
        gridPane.prefWidthProperty().bind(vBox.widthProperty());
        //gridPane.setPrefHeight(10);
        label1.prefWidthProperty().bind(gridPane.widthProperty().divide(5));
        label2.prefWidthProperty().bind(gridPane.widthProperty().divide(5));
        label3.prefWidthProperty().bind(gridPane.widthProperty().divide(5));
        label4.prefWidthProperty().bind(gridPane.widthProperty().divide(5));
        label5.prefWidthProperty().bind(gridPane.widthProperty().divide(5));

        rowName = values[0];
        label1.setText(values[1]);
        label2.setText(values[2]);
        label3.setText(values[3]);
        label4.setText(values[4]);
        label5.setText(values[5]);
    }

    /**
     * Sets the css style of the labels for the header row.
     */
    public void setHeaderRow(){
        label2.getStyleClass().add("label_very_large");
        label3.getStyleClass().add("label_very_large");
        label4.getStyleClass().add("label_very_large");
        label5.getStyleClass().add("label_very_large");
    }

    /**
     * Sets the text color of the labels.
     * @param color The desired text color.
     */
    public void setColor(String color){
        label2.setStyle("-fx-text-fill: " + color + ";");
        label3.setStyle("-fx-text-fill: " + color + ";");
        label4.setStyle("-fx-text-fill: " + color + ";");
        label5.setStyle("-fx-text-fill: " + color + ";");
    }
}
