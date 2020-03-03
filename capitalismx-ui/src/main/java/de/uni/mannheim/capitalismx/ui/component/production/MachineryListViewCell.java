package de.uni.mannheim.capitalismx.ui.component.production;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.production.machinery.Machinery;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.util.CapCoinFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * A machinery view cell.
 * It displays the buy date, resale price, capacity and production technology.
 * It also allows the user to maintain, ugprade, and sell the machinery.
 *
 * @author dzhao
 */
public class MachineryListViewCell extends ListCell<Machinery> {

    @FXML
    private Label indexLabel;

    @FXML
    private Label valueLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label capacityLabel;

    @FXML
    private Label productionTechnologyLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button maintainAndRepairButton;

    @FXML
    private Button upgradeButton;

    @FXML
    private Button sellButton;

    private FXMLLoader loader;

    private ListView<Machinery> machineryListView;

    /**
     * Instantiates a new Machinery list view cell.
     *
     * @param machineryListView the machinery list view
     */
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
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component/machinery_list_cell.fxml"), UIManager.getResourceBundle());
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
            this.updateLabels(machinery);
            sellButton.setOnAction(e -> {
                controller.sellMachinery(machinery, GameState.getInstance().getGameDate());
                this.machineryListView.getItems().remove(machinery);
            });
            maintainAndRepairButton.setOnAction(e -> {
                controller.maintainAndRepairMachinery(machinery);
                this.updateLabels(machinery);
            });
            upgradeButton.setOnAction(e -> {
                controller.upgradeMachinery(machinery);
                this.updateLabels(machinery);
                if (!machinery.isUpgradeable()) {
                    this.upgradeButton.setDisable(true);
                }
            });

            setText(null);
            setGraphic(gridPane);
        }
    }

    /**
     * Update labels.
     *
     * @param machinery the machinery
     */
    public void updateLabels(Machinery machinery) {
        valueLabel.setText(CapCoinFormatter.getCapCoins(machinery.calculateResellPrice()));
        dateLabel.setText(machinery.getPurchaseDate() + "");
        capacityLabel.setText(machinery.getMachineryCapacity() + " Capacity");
        productionTechnologyLabel.setText(machinery.getProductionTechnology().toString());
    }
}
