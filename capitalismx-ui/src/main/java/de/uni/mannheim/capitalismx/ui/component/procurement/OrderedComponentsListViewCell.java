package de.uni.mannheim.capitalismx.ui.component.procurement;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentOrder;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;

/**
 * A component order view cell.
 * It displays the component, quantity, order date and arrival date.
 *
 * @author dzhao
 */
@Deprecated
public class OrderedComponentsListViewCell extends ListCell<ComponentOrder
        > {

    /**
     * The Component label.
     */
    @FXML
    Label componentLabel;

    /**
     * The Quantity label.
     */
    @FXML
    Label quantityLabel;

    /**
     * The Order date label.
     */
    @FXML
    Label orderDateLabel;

    /**
     * The Arrival date label.
     */
    @FXML
    Label arrivalDateLabel;


    private FXMLLoader loader;

    private ListView<ComponentOrder> orderedComponentsListView;

    /**
     * Instantiates a new Ordered components list view cell.
     *
     * @param orderedComponentsListView the ordered components list view
     */
    public OrderedComponentsListViewCell(ListView<ComponentOrder> orderedComponentsListView) {
        this.orderedComponentsListView = orderedComponentsListView;
    }

    @Override
    protected void updateItem(ComponentOrder componentOrder, boolean empty) {
        super.updateItem(componentOrder, empty);
        if(empty || componentOrder == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component/ordered_components_list_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    setText(null);
                    setGraphic(loader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Component component = componentOrder.getOrderedComponent();
            componentLabel.setText(component.getComponentName(UIManager.getInstance().getLanguage()) + " - " + component.getSupplierCategoryShortForm());
            quantityLabel.setText("" + componentOrder.getOrderedQuantity());
            orderDateLabel.setText(componentOrder.getOrderDate().toString());
            arrivalDateLabel.setText(componentOrder.getArrivalDate().toString());
        }
    }
}
