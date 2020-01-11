package de.uni.mannheim.capitalismx.ui.components.finance;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.domain.employee.Employee;
import de.uni.mannheim.capitalismx.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.hr.RecruitingListController;
import de.uni.mannheim.capitalismx.ui.utils.GraphicHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author sdupper
 *
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

    private VBox vBox;

    private String[] values;

    private String rowName;

    private Map<String, OperationsTableViewCell> controllers;

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

        gridPane.setOnMouseClicked(e -> {

        });
    }

    public void setHeaderRow(){
        label2.getStyleClass().add("label_very_large");
        label3.getStyleClass().add("label_very_large");
        label4.getStyleClass().add("label_very_large");
        label5.getStyleClass().add("label_very_large");
    }

    public void setColor(String color){
        label2.setStyle("-fx-text-fill: " + color + ";");
        label3.setStyle("-fx-text-fill: " + color + ";");
        label4.setStyle("-fx-text-fill: " + color + ";");
        label5.setStyle("-fx-text-fill: " + color + ";");
    }
}
