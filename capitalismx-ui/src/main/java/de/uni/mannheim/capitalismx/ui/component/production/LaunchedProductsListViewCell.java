package de.uni.mannheim.capitalismx.ui.component.production;

import java.io.IOException;
import java.util.Optional;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.production.exceptions.ComponentLockedException;
import de.uni.mannheim.capitalismx.production.exceptions.NotEnoughComponentsException;
import de.uni.mannheim.capitalismx.production.exceptions.NotEnoughFreeStorageException;
import de.uni.mannheim.capitalismx.production.exceptions.NotEnoughMachineCapacityException;
import de.uni.mannheim.capitalismx.production.product.Product;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.general.GameAlert;
import de.uni.mannheim.capitalismx.ui.util.TextFieldHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * View cell of launched products.
 * Allows the user to see the components of the product and to produce the product.
 *
 * @author dzhao
 */
public class LaunchedProductsListViewCell extends ListCell<Product> {


    private GridPane gridPane;

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

	/**
	 * Instantiates a new Launched products list view cell.
	 *
	 * @param launchProductsListView the launch products list view
	 */
	public LaunchedProductsListViewCell(ListView<Product> launchProductsListView) {
		this.launchedProductsListView = launchProductsListView;
	}

	private Product product;

	@Override
	protected void updateItem(Product product, boolean empty) {
		super.updateItem(product, empty);
		this.product = product;
		if (empty || product == null) {
			setText(null);
			setGraphic(null);
		} else {
			if (loader == null) {
				loader = new FXMLLoader(
						getClass().getClassLoader().getResource("fxml/component/launched_products_list_cell.fxml"),
						UIManager.getResourceBundle());
				loader.setController(this);

				try {
					gridPane = loader.load();
					TextFieldHelper.makeTextFieldNumeric(quantityTextField);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			setText(null);
			setGraphic(gridPane);
			productLabel.setText(product.getProductName());
			componentsVBox.getChildren().clear();
			for (Component component : product.getComponents()) {
				componentsVBox.getChildren()
						.add(new Label(component.getComponentName(UIManager.getInstance().getLanguage()) + " - "
								+ component.getSupplierCategoryShortForm()));
			}
		}
	}

	/**
	 * Produces products.
	 * If an exception is caught a game alert is shown with the option to produce less products.
	 */
	public void produceProduct() {
		int quantity = 0;
		if (!quantityTextField.getText().equals("")) {
			quantity = Integer.valueOf(quantityTextField.getText());
			try {
				GameController.getInstance().produceProduct(product, quantity);
			} catch (Exception e) {
				GameAlert error = new GameAlert(Alert.AlertType.CONFIRMATION,
						"Could not produce " + quantity + " unit(s).", "");
				String exceptionMessage = e.getMessage();
				int newQuantity = 0;
				if (e instanceof NotEnoughFreeStorageException) {
					NotEnoughFreeStorageException exception = (NotEnoughFreeStorageException) e;
					error.setContentText(exceptionMessage + "\nDo you want to produce " + exception.getFreeStorage()
							+ " unit(s) instead?");
					newQuantity = exception.getFreeStorage();
				} else if (e instanceof NotEnoughMachineCapacityException) {
					NotEnoughMachineCapacityException exception = (NotEnoughMachineCapacityException) e;
					error.setContentText(exceptionMessage + "\nDo you want to produce " + exception.getMachineCapacity()
							+ " unit(s) instead?");
					newQuantity = exception.getMachineCapacity();
				} else if (e instanceof NotEnoughComponentsException) {
					NotEnoughComponentsException exception = (NotEnoughComponentsException) e;
					error.setContentText(exceptionMessage + "\nDo you want to produce "
							+ exception.getMaxmimumProducable() + " unit(s) instead?");
					newQuantity = exception.getMaxmimumProducable();
				} else if (e instanceof ComponentLockedException){
					error.setContentText(e.getMessage());
				}
				else {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				Optional<ButtonType> result = error.showAndWait();
				if (result.get() == ButtonType.OK) {
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
