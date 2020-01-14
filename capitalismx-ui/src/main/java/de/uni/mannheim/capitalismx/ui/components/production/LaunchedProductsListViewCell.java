package de.uni.mannheim.capitalismx.ui.components.production;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;

public class LaunchedProductsListViewCell extends ListCell<Product> {

    @FXML
    private Label productLabel;

    @FXML
    private Button buyButton;

    @FXML
    private TextField quantityTextField;

    private FXMLLoader loader;

    private ListView<Product> launchedProductsListView;

    public LaunchedProductsListViewCell(ListView<Product> launchProductsListView) {
        this.launchedProductsListView = launchProductsListView;
    }

    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);
        if(empty || product == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/launched_products_list_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        GameController controller = GameController.getInstance();
        productLabel.setText("product.getProductName()");
    }
}
