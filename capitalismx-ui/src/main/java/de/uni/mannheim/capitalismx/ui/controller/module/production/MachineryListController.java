package de.uni.mannheim.capitalismx.ui.controller.module.production;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.production.Machinery;
import de.uni.mannheim.capitalismx.production.NoMachinerySlotsAvailableException;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameAlert;
import de.uni.mannheim.capitalismx.ui.components.production.MachineryListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class MachineryListController extends GameModuleController {

    @FXML
    ListView<Machinery> machineryListView;

    @FXML
    private Button buyButton;


    @Override
    public void update() {
        // TODO Auto-generated method stub
        ProductionDepartment productionDepartment = GameState.getInstance().getProductionDepartment();

        List<Machinery> machinery = productionDepartment.getMachines();
        machineryListView.setItems(FXCollections.observableList(machinery));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();

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
    }

}

