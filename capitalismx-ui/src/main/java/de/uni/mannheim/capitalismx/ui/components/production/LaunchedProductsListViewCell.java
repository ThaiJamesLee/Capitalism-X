package de.uni.mannheim.capitalismx.ui.components.production;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.production.NotEnoughComponentsException;
import de.uni.mannheim.capitalismx.production.NotEnoughFreeStorageException;
import de.uni.mannheim.capitalismx.production.NotEnoughMachineCapacityException;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;

public class LaunchedProductsListViewCell extends ListCell<Product> {

    @FXML
    private Label productLabel;

    @FXML
    private Button produceButton;

    @FXML
    private VBox componentsVBox;

    @FXML
    private TextField quantityTextField;

    private FXMLLoader loader;

    private ListView<Product> launchedProductsListView;

    public LaunchedProductsListViewCell(ListView<Product> launchProductsListView) {
        this.launchedProductsListView = launchProductsListView;
    }

    private Product product;

    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);
        this.product = product;
        if(empty || product == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/launched_products_list_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    setText(null);
                    setGraphic(loader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            GameController controller = GameController.getInstance();
            productLabel.setText(product.getProductName());
            for(Component component : product.getComponents()) {
                componentsVBox.getChildren().add(new Label(component.getComponentName(UIManager.getInstance().getLanguage()) + " - " + component.getSupplierCategoryShortForm()));
            }
        }
    }

    public void produceProduct() {
        int quantity = 0;
        if(!quantityTextField.getText().equals("")) {
            quantity = Integer.valueOf(quantityTextField.getText());
            try {
                GameController.getInstance().produceProduct(product, quantity);
            } catch (Exception e) {
                GameAlert error = new GameAlert(Alert.AlertType.CONFIRMATION, "Could not produce " + quantity + "unit(s).", "");
                String exceptionMessage = e.getMessage();
                int newQuantity = 0;
                if(e instanceof NotEnoughFreeStorageException) {
                    NotEnoughFreeStorageException exception = (NotEnoughFreeStorageException) e;
                    error.setContentText(exceptionMessage + "\nDo you want to produce " +  exception.getFreeStorage() + " unit(s) instead?");
                    newQuantity = exception.getFreeStorage();
                } else if(e instanceof NotEnoughMachineCapacityException) {
                    NotEnoughMachineCapacityException exception = (NotEnoughMachineCapacityException) e;
                    error.setContentText(exceptionMessage + "\nDo you want to produce " +  exception.getMachineCapacity() + " unit(s) instead?");
                    newQuantity = exception.getMachineCapacity();
                } else if(e instanceof NotEnoughComponentsException) {
                    NotEnoughComponentsException exception = (NotEnoughComponentsException) e;
                    error.setContentText(exceptionMessage + "\nDo you want to produce " +  exception.getMaxmimumProducable() + " unit(s) instead?");
                    newQuantity = exception.getMaxmimumProducable();
                }
                Optional<ButtonType> result = error.showAndWait();
                if(result.get() == ButtonType.OK) {
                    try {
                        GameController.getInstance().produceProduct(product, newQuantity);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        System.out.println(e2.getMessage());
                    }
                }
            }
        }
    }
}
