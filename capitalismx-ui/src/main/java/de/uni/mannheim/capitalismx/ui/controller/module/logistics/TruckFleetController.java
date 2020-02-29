package de.uni.mannheim.capitalismx.ui.controller.module.logistics;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.finance.finance.Loan;
import javafx.application.Platform;
import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.logistics.TruckListViewCell;
import de.uni.mannheim.capitalismx.ui.util.PopOverFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * This class represents the internal fleet in the logistics UI. It allows the user to add new trucks to the internal
 * fleet.
 *
 * @author sdupper
 */
public class TruckFleetController implements Initializable {

	@FXML
	ListView<Truck> truckFleetListView;

	@FXML
	private Button buyTruckButton;

	/**
	 * The popover for the truck selection.
	 */
	private PopOver popover;

	public TruckFleetController() {
	}

	/*
	 * Displays information about the trucks currently in the internal fleet and generates the truck selection popover.
	 * The popover is displayed after clicking the buyTruckButton.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GameController controller = GameController.getInstance();

		//load internal fleet
		for(Truck truck : controller.getInternalFleet().getTrucks()){
			truckFleetListView.getItems().add(truck);
		}

		truckFleetListView.setCellFactory(truckListView -> new TruckListViewCell(truckFleetListView));
		truckFleetListView.setPlaceholder(new Label(UIManager.getLocalisedString("list.placeholder.truck")));

		PopOverFactory helper = new PopOverFactory();
		helper.createStandardOverlay("fxml/popover/truck_detail_list.fxml");
		popover = helper.getPopover();

		buyTruckButton.setOnAction(e -> {
			showPopover();
		});
	}

	/**
	 * Updates the list of trucks in the internal fleet when a new truck is added.
	 * @param truck The new truck.
	 */
	public void addTruck(Truck truck) {
		truckFleetListView.getItems().add(truck);
		popover.hide();
	}

	/**
	 * Displays the truck selection popover.
	 */
	private void showPopover() {
		popover.show(UIManager.getInstance().getStage());
	}

	/**
	 * Updates information like resell price of the specified truck.
	 * @param truck The truck to be updated.
	 */
	public void updateTruck(Truck truck){
		Platform.runLater(new Runnable() {
			public void run() {
				int index = truckFleetListView.getItems().indexOf(truck);
				truckFleetListView.getItems().remove(truck);
				truckFleetListView.getItems().add(index, truck);
			}
		});
	}

}