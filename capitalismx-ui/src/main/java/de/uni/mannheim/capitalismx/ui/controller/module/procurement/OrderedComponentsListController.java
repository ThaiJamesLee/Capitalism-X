package de.uni.mannheim.capitalismx.ui.controller.module.procurement;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentOrder;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.ProcurementDepartment;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.ui.components.procurement.OrderedComponentsListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.ProcurementEventListener;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * Currently not used, but should work...
 */
@Deprecated
public class OrderedComponentsListController implements UpdateableController {

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
        ProcurementEventListener eventlistener = new ProcurementEventListener();
        ProcurementDepartment procurementDepartment = GameState.getInstance().getProcurementDepartment();
        procurementDepartment.getComponentOrdersChange().addPropertyChangeListener(eventlistener);
        procurementDepartment.registerPropertyChangeListener(eventlistener);
    }
}
