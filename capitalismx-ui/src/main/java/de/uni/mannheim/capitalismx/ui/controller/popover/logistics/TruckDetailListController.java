package de.uni.mannheim.capitalismx.ui.controller.popover.logistics;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.logistics.TruckDetailListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * This class represents the truck selection for the internal fleet in the logistics UI. It displays different trucks
 * that the user can buy and add to the internal fleet.
 *
 * @author sdupper
 */
public class TruckDetailListController implements UpdateableController {

    @FXML
    private ListView<Truck> truckDetailListView;

    @Override
    public void update() {
    }

    /*
     * Generates an entry in the truck selection for all available trucks.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        truckDetailListView.setCellFactory(truckListView -> new TruckDetailListViewCell(truckListView));

        List<Truck> trucks = controller.generateTruckSelection(UIManager.getResourceBundle().getLocale());

        for(Truck truck : trucks) {
            truckDetailListView.getItems().add(truck);
        }

    }

}