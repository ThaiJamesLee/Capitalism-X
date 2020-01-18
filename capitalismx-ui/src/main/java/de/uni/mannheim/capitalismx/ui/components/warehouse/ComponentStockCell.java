package de.uni.mannheim.capitalismx.ui.components.warehouse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.warehouse.StockManagementController;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * A visual representation of the current stock of all {@link Component}s of a
 * certain {@link ComponentType}. Allows trading these {@link Component}s as
 * well, as soon as they are unlocked by the year.
 * 
 * @author Jonathan
 *
 */
public class ComponentStockCell {

	private ComponentType type; // TODO necessary?

	private HashMap<SupplierCategory, Double> componentPrices; // TODO recalculate and store prices in the department,
																// as these prices do not affect the actual buying
																// process

	private HashMap<SupplierCategory, Component> components;

	WarehousingDepartment warehouse;

	@FXML
	private AnchorPane root;

	@FXML
	private Label componentType, cheapQualityAmount, regularQualityAmount, premiumQualityAmount;

	@FXML
	private Button cheapQualityTrade, regularQualityTrade, premiumQualityTrade;

	/**
	 * Constructor for a {@link ComponentStockCell}.
	 * 
	 * @param type The {@link ComponentType} to create a cell for.
	 */
	public ComponentStockCell(ComponentType type) {
		LocalDate gameDate = GameState.getInstance().getGameDate();

		warehouse = GameState.getInstance().getWarehousingDepartment();
		this.type = type;
		components = new HashMap<SupplierCategory, Component>();
		components.put(SupplierCategory.CHEAP, new Component(type, SupplierCategory.CHEAP, gameDate));
		components.put(SupplierCategory.REGULAR, new Component(type, SupplierCategory.REGULAR, gameDate));
		components.put(SupplierCategory.PREMIUM, new Component(type, SupplierCategory.PREMIUM, gameDate));

		// load fxml
		FXMLLoader loader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/components/component_stock_cell.fxml"),
				UIManager.getResourceBundle());
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// init labels and buttons
		componentType.setText(type.getName(UIManager.getResourceBundle().getLocale()));
		cheapQualityAmount.setText(warehouse.getAmountStored(components.get(SupplierCategory.CHEAP)) + "");
		regularQualityAmount.setText(warehouse.getAmountStored(components.get(SupplierCategory.REGULAR)) + "");
		premiumQualityAmount.setText(warehouse.getAmountStored(components.get(SupplierCategory.PREMIUM)) + "");
		// TODO add cost/locale to button text
		cheapQualityTrade.setOnAction(e -> {
			showTradeComponentMenu(SupplierCategory.CHEAP, cheapQualityTrade);
		});
		regularQualityTrade.setOnAction(e -> {
			showTradeComponentMenu(SupplierCategory.REGULAR, regularQualityTrade);
		});
		premiumQualityTrade.setOnAction(e -> {
			showTradeComponentMenu(SupplierCategory.PREMIUM, premiumQualityTrade);
		});

		componentPrices = new HashMap<SupplierCategory, Double>();
		updateQuarterlyComponentPrices(gameDate);

	}

	public AnchorPane getRoot() {
		return root;
	}

	/**
	 * Sets if the {@link Component} is available. (Enables/Disables the Buttons)
	 * 
	 * @param available Whether the {@link Component} is available.
	 */
	public void setComponentAvailable(boolean available) {
		cheapQualityTrade.setDisable(!available);
		regularQualityTrade.setDisable(!available);
		premiumQualityTrade.setDisable(!available);
	}

	/**
	 * Show the menu for trading components.
	 * 
	 * @param category The {@link SupplierCategory} to display the trade popover
	 *                 for.
	 * @param button   {@link Button} to display the {@link PopOver} on.
	 */
	private void showTradeComponentMenu(SupplierCategory category, Button button) {
		StockManagementController stockController = (StockManagementController) UIManager.getInstance()
				.getGameView(GameViewType.WAREHOUSE).getModule(UIElementType.WAREHOUSE_STOCK_MANAGEMENT)
				.getController();
		stockController.showTradePopover(components.get(category), button, componentPrices.get(category));
	}

	/**
	 * Update the calculation of the prices the player has to pay for the different
	 * {@link SupplierCategory}s of this {@link Component}. Currently updated every
	 * 3 months. TODO maybe change later?
	 * 
	 * @param date The date to calculate them for.
	 */
	public void updateQuarterlyComponentPrices(LocalDate date) {
		for (SupplierCategory supplier : SupplierCategory.values()) {
			componentPrices.put(supplier, components.get(supplier).calculateRandomizedBaseCost(date));
		}
	}

	/**
	 * Update the amount stored for the given {@link SupplierCategory}.
	 * 
	 * @param supplierCategory The {@link SupplierCategory}, whose {@link Label}
	 *                         needs to be updated.
	 */
	public void updateQuality(SupplierCategory supplierCategory) {
		switch (supplierCategory) {
		case CHEAP:
			cheapQualityAmount.setText(warehouse.getAmountStored(components.get(SupplierCategory.CHEAP)) + "");
			break;
		case REGULAR:
			regularQualityAmount.setText(warehouse.getAmountStored(components.get(SupplierCategory.REGULAR)) + "");
			break;
		case PREMIUM:
			premiumQualityAmount.setText(warehouse.getAmountStored(components.get(SupplierCategory.PREMIUM)) + "");
			break;
		default:
			break;
		}
	}

}
