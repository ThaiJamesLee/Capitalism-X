package de.uni.mannheim.capitalismx.ui.controller.module.logistics;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.logistics.TruckListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.util.Duration;

public class TruckFleetController extends GameModuleController {

	@FXML
	ListView<Truck> truckFleetListView;

	@FXML
	private Button buyTruckButton;

	private PopOver popover;

	public TruckFleetController() {
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GameController controller = GameController.getInstance();
		// ObservableList<Truck> truckFleetListObservable =
		// FXCollections.observableArrayList();
		ArrayList<Truck> trucks = controller.generateTruckSelection();
		/*
		 * for(Truck truck : trucks){ truckFleetListObservable.add(truck); }
		 */

		/*
		 * MenuItem[] truckMenuItems = new MenuItem[trucks.size()];
		 * 
		 * int i = 0; for(Truck truck : trucks) { truckMenuItems[i] = new
		 * MenuItem(truck.getName() + " Purchase Price: " + truck.getPurchasePrice());
		 * truckMenuItems[i].setOnAction(e -> { controller.addTruckToFleet(truck,
		 * GameState.getInstance().getGameDate());
		 * truckFleetListView.getItems().add(truck); //selectMenuItem((MenuItem)
		 * e.getSource()); }); //data.add("Partner " + i + " Contractual Costs: " +
		 * externalPartner.getContractualCost()); i++; }
		 * 
		 * truckSelectionDropdown.getItems().addAll(truckMenuItems);
		 */

		// truckFleetListView.setItems(truckFleetListObservable);
		truckFleetListView.setCellFactory(truckListView -> new TruckListViewCell(truckFleetListView));

		preparePopover();

		buyTruckButton.setOnAction(e -> {
			showPopover();
		});
	}

	public void addTruck(Truck truck) {
		truckFleetListView.getItems().add(truck);
	}

	/**
	 * Prepare the {@link PopOver} displaying the choice of trucks to buy.
	 */
	private void preparePopover() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getClassLoader().getResource("fxml/overlay/truck_detail_list.fxml"),
				UIManager.getResourceBundle());
		Parent root;
		try {
			root = loader.load();
			popover = new PopOver(root);
			popover.setArrowSize(0.0);
			popover.setArrowLocation(ArrowLocation.TOP_CENTER);
			popover.setArrowIndent(10.0);
			popover.setFadeInDuration(Duration.millis(50));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showPopover() {
		popover.show(truckFleetListView);
	}

}