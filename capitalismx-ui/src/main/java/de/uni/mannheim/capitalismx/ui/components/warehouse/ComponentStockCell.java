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
import de.uni.mannheim.capitalismx.ui.controller.component.TradeComponentPopoverController;
import de.uni.mannheim.capitalismx.ui.controller.module.warehouse.StockManagementController;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ComponentStockCell {

	private ComponentType type; // TODO necessary?
	
	private HashMap<SupplierCategory, Component> components;

	@FXML
	private AnchorPane root;
	
	private PopOver tradePopover;

	@FXML
	private Label componentType, cheapQualityAmount, regularQualityAmount, premiumQualityAmount;

	@FXML
	private Button cheapQualityTrade, regularQualityTrade, premiumQualityTrade;

	public ComponentStockCell(ComponentType type) {
		LocalDate gameDate = GameState.getInstance().getGameDate();

		WarehousingDepartment warehouse = GameState.getInstance().getWarehousingDepartment();
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
		//TODO add cost/locale to button text
		cheapQualityTrade.setOnAction(e -> {
			tradeComponents(SupplierCategory.CHEAP, cheapQualityTrade);
		});
		regularQualityTrade.setOnAction(e -> {
			tradeComponents(SupplierCategory.REGULAR, regularQualityTrade);
		});
		premiumQualityTrade.setOnAction(e -> {
			tradeComponents(SupplierCategory.PREMIUM, premiumQualityTrade);
		});

		// Prepare the Popover for the trade buttons
		FXMLLoader popoverLoader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/components/trade_component_popover.fxml"), UIManager.getResourceBundle());
		tradePopover = new PopOver();
		try {
			tradePopover.setDetachable(false);
			tradePopover.setContentNode(popoverLoader.load());
			tradePopover.setFadeInDuration(Duration.millis(50));
			TradeComponentPopoverController popOverController = ((TradeComponentPopoverController) popoverLoader.getController());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public AnchorPane getRoot() {
		return root;
	}

	private void tradeComponents(SupplierCategory category, Button button) {
		StockManagementController controller = (StockManagementController)UIManager.getInstance().getGameView(GameViewType.WAREHOUSE).getModule(UIElementType.WAREHOUSE_STOCK_MANAGEMENT).getController();
		controller.showTradePopover(components.get(category), button);
	}

}
