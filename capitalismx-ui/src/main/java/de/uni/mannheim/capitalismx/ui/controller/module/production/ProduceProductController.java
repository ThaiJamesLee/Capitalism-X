package de.uni.mannheim.capitalismx.ui.controller.module.production;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.production.LaunchedProductsListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.ProductionEventListener;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ProduceProductController extends GameModuleController {

	@FXML
	ListView<Product> launchedProductsListView;

	@FXML
	private Button introduceProductButton;

	private PopOver introduceProductPopover;

	@Override
	public void update() {
		ProductionDepartment production = GameState.getInstance().getProductionDepartment();

		List<Product> launchedProducts = production.getLaunchedProducts();
		launchedProductsListView.setItems(FXCollections.observableList(launchedProducts));

		/**
		 * Test the list view with a manually added product.
		 */
		/*
		 * LocalDate gameDate = GameState.getInstance().getGameDate(); List<Component>
		 * components = new ArrayList<>(); components.add(new
		 * Component(ComponentType.T_DISPLAY_LEVEL_1, SupplierCategory.CHEAP,
		 * gameDate)); components.add(new Component(ComponentType.T_CASE_LEVEL_1,
		 * SupplierCategory.CHEAP, gameDate)); components.add(new
		 * Component(ComponentType.T_SOUND_LEVEL_1, SupplierCategory.CHEAP, gameDate));
		 * components.add(new Component(ComponentType.T_OS_LEVEL_1,
		 * SupplierCategory.CHEAP, gameDate)); try { Product p = new Product("CAPTV",
		 * ProductCategory.TELEVISION, components);
		 * launchedProductsListView.getItems().add(p); } catch
		 * (InvalidSetOfComponentsException e) { e.printStackTrace(); }
		 */
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		launchedProductsListView
				.setCellFactory(launchedProductsListView -> new LaunchedProductsListViewCell(launchedProductsListView));
		ProductionEventListener eventListener = new ProductionEventListener();
		ProductionDepartment productionDepartment = GameState.getInstance().getProductionDepartment();
		productionDepartment.getLaunchedProductsChange().addPropertyChangeListener(eventListener);

		PopOverFactory factory = new PopOverFactory();
		factory.createStandardOverlay("fxml/module/introduce_product_menu.fxml");
		introduceProductPopover = factory.getPopover();
	}

	/**
	 * Show the popover for introducing a new {@link Product}.
	 */
	@FXML
	public void introduceProduct() {
		introduceProductPopover.show(UIManager.getInstance().getStage());
	}

	/**
	 * Hide the popover for introducing a new {@link Product}.
	 */
	public void hidePopOver() {
		introduceProductPopover.hide();
	}
}
