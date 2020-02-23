package de.uni.mannheim.capitalismx.ui.components.logistics;

import java.io.IOException;
import java.text.NumberFormat;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.logistic.logistics.exception.NotEnoughTruckCapacityException;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameModuleType;
import de.uni.mannheim.capitalismx.ui.components.general.GameAlert;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.TruckFleetController;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

/**
 * This class represents an entry in the truck selection in the logistics UI. It displays information like the price and
 * quality of a specific truck.
 *
 * @author sdupper
 */
public class TruckDetailListViewCell extends ListCell<Truck> {

	@FXML
	private Label nameLabel;

	@FXML
	private Label priceLabel;

	@FXML
	private Label ecoIndexLabel;

	@FXML
	private Label qualityIndexLabel;

	@FXML
	GridPane gridPane;

	@FXML
	private Label fixCostsLabel;

	private FXMLLoader loader;

    /**
     * The ListView of all available trucks.
     */
	private ListView<Truck> truckDetailListView;

    /**
     * Constructor
     * @param truckDetailListView The ListView of all available trucks.
     */
	public TruckDetailListViewCell(ListView<Truck> truckDetailListView) {
		this.truckDetailListView = truckDetailListView;
	}

    /*
     * Generates an entry in the truck selection for each new truck added to the truckDetailListView according to the
     * truck's characteristics. If the user clicks on one and has enough capacity, the truck is bought and added to the
     * internal fleet.
     */
	@Override
	protected void updateItem(Truck truck, boolean empty) {
		super.updateItem(truck, empty);
		if (empty || truck == null) {
			setText(null);
			setGraphic(null);
		} else {
			if (loader == null) {
				loader = new FXMLLoader(
						getClass().getClassLoader().getResource("fxml/components/truck_detail_list_cell.fxml"),
						UIManager.getResourceBundle());
				loader.setController(this);

				try {
					setGraphic(loader.load());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			GameController controller = GameController.getInstance();
			nameLabel.setText(truck.getName());
			priceLabel.setText("Price: " + CapCoinFormatter.getCapCoins(truck.getPurchasePrice()));
			ecoIndexLabel.setText("Eco Index: " + NumberFormat
					.getPercentInstance(UIManager.getResourceBundle().getLocale()).format(truck.getEcoIndex()));
			qualityIndexLabel.setText("Quality Index: " + NumberFormat
					.getPercentInstance(UIManager.getResourceBundle().getLocale()).format(truck.getQualityIndex()));
			fixCostsLabel.setText("Delivery Costs: " + CapCoinFormatter.getCapCoins(truck.getFixCostsDelivery()));
			// add click listener to cell
			gridPane.setOnMouseClicked(e -> {
				if (truck.getPurchasePrice() > GameController.getInstance().getCash()) {
					GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("trucks.error.cash.title"),
							UIManager.getLocalisedString("trucks.error.cash.description"));
					error.showAndWait();
				} else {
					try {
						controller.buyTruck(truck, GameState.getInstance().getGameDate());
						TruckFleetController uiController = (TruckFleetController) UIManager.getInstance()
								.getModule(GameModuleType.LOGISTICS_TRUCK_FLEET_OVERVIEW).getController();
						uiController.addTruck(truck);
						truckDetailListView.getSelectionModel().clearSelection();
					} catch (NotEnoughTruckCapacityException ex) {
						GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("trucks.error.capacity.title"),
								UIManager.getLocalisedString("trucks.error.capacity.description"));
						error.showAndWait();
					}
				}
			});

			setText(null);
		}
	}

}