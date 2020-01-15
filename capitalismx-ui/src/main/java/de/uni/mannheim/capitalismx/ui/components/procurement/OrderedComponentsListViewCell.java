package de.uni.mannheim.capitalismx.ui.components.procurement;

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

public class OrderedComponentsListViewCell extends ListCell<ComponentOrder
        > {

    @FXML
    Label componentLabel;

    @FXML
    Label quantityLabel;

    @FXML
    Label orderDateLabel;

    @FXML
    Label arrivalDateLabel;


    private FXMLLoader loader;

    private ListView<ComponentOrder> orderedComponentsListView;

    public OrderedComponentsListViewCell(ListView<ComponentOrder> orderedComponentsListView) {
        this.orderedComponentsListView = orderedComponentsListView;
    }

    protected void updateItem(ComponentOrder componentOrder, boolean empty) {
        super.updateItem(componentOrder, empty);
        if(empty || componentOrder == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/ordered_components_list_cell.fxml"), UIManager.getResourceBundle());
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
