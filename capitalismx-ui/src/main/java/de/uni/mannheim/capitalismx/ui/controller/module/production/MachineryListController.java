package de.uni.mannheim.capitalismx.ui.controller.module.production;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.production.machinery.Machinery;
import de.uni.mannheim.capitalismx.production.exceptions.NoMachinerySlotsAvailableException;
import de.uni.mannheim.capitalismx.production.department.ProductionDepartment;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.general.GameAlert;
import de.uni.mannheim.capitalismx.ui.component.production.MachineryListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * The controller for the machinery list
 * It allows to buy a new machinery and it displays a game alert if there are not enough machinery slots available in
 * the production department.
 *
 * @author dzhao
 */
public class MachineryListController implements UpdateableController {

    /**
     * The Machinery list view.
     */
    @FXML
    ListView<Machinery> machineryListView;

    @FXML
    private Button buyButton;


    @Override
    public void update() {
        List<Machinery> machinery = GameController.getInstance().getMachines();
        machineryListView.setItems(FXCollections.observableList(machinery));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();

        String buyButtonText = UIManager.getLocalisedString("machinery.list.buy");
        buyButtonText = buyButtonText.replace("XXX", String.valueOf(controller.getMachineryPurchasePrice()));
        buyButton.setText(buyButtonText);

        buyButton.setOnAction(e -> {
            LocalDate gameDate = GameState.getInstance().getGameDate();
            try {
                controller.buyMachinery(new Machinery(gameDate), gameDate);
                this.update();
            } catch (NoMachinerySlotsAvailableException exception) {
                GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("production.alert.notEnoughMachineSlots.title"), UIManager.getLocalisedString("production.alert.notEnoughMachineSlots.contextText"));
                error.showAndWait();
                System.out.println(exception.getMessage());
            }
        });


        machineryListView.setCellFactory(machineryListView -> new MachineryListViewCell(machineryListView));

        this.update();
    }

}

