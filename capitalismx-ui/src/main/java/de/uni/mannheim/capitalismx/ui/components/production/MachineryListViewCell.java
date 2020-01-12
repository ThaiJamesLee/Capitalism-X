package de.uni.mannheim.capitalismx.ui.components.production;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.production.Machinery;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MachineryListViewCell extends ListCell<Machinery> {

    @FXML
    private Label indexLabel;

    @FXML
    private Label valueLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button sellButton;

    private FXMLLoader loader;

    private ListView<Machinery> machineryListView;

    public MachineryListViewCell(ListView<Machinery> machineryListView){
        this.machineryListView = machineryListView;
    }

    @Override
    protected void updateItem(Machinery machinery, boolean empty) {
        super.updateItem(machinery, empty);
        if(empty || machinery == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/truck_list_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            GameController controller = GameController.getInstance();
            //TODO
            //indexLabel.setText(controller.getMachines().indexOf(machinery) + "");
            valueLabel.setText(machinery.calculateResellPrice() + " CC");
            dateLabel.setText(machinery.getPurchaseDate() + "");
            sellButton.setOnAction(e -> {
                controller.sellMachinery(machinery);
                this.machineryListView.getItems().remove(machinery);
            });

            setText(null);
            setGraphic(gridPane);
        }
    }

}
