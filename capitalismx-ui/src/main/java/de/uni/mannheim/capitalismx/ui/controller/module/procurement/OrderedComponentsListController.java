package de.uni.mannheim.capitalismx.ui.controller.module.procurement;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.*;
import de.uni.mannheim.capitalismx.ui.components.procurement.OrderedComponentsListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderedComponentsListController extends GameModuleController {

    @FXML
    ListView<ComponentOrder> orderedComponentsListView;

    @Override
    public void update() {
        ProcurementDepartment procurement = GameState.getInstance().getProcurementDepartment();

        List<ComponentOrder> componentOrders = procurement.getComponentOrders();
        orderedComponentsListView.getItems().clear();
        orderedComponentsListView.getItems().addAll(componentOrders);

        /**
         * Test the list view with a manually added component order.
         */
        ComponentOrder componentOrder = new ComponentOrder(GameState.getInstance().getGameDate(), new Component(ComponentType.T_CASE_LEVEL_1, SupplierCategory.CHEAP, GameState.getInstance().getGameDate()), 2);
        orderedComponentsListView.getItems().add(componentOrder);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderedComponentsListView.setCellFactory(orderedComponentsListView -> new OrderedComponentsListViewCell(orderedComponentsListView));
    }
}
