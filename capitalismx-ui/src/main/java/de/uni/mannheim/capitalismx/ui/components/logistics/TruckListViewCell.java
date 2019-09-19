package de.uni.mannheim.capitalismx.ui.components.logistics;

import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class TruckListViewCell extends ListCell<Truck> {

    @FXML
    private Label nameLabel;

    @FXML
    private Label wageLabel;

    @FXML
    private Label skillLabel;

    @FXML
    private GridPane gridPane;

    private FXMLLoader loader;

    @Override
    protected void updateItem(Truck truck, boolean empty) {
        super.updateItem(truck, empty);
        if(empty || truck == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/truck_list_cell.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            nameLabel.setText(truck.getPurchasePrice() + "");
            wageLabel.setText(truck.getCapacity() + " CC");
            skillLabel.setText(truck.getPurchasePrice() + "");

            setText(null);
            setGraphic(gridPane);
        }
    }

}