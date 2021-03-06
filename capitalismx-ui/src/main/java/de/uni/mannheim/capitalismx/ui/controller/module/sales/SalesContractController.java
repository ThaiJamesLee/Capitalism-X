package de.uni.mannheim.capitalismx.ui.controller.module.sales;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.production.department.ProductionDepartment;
import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import de.uni.mannheim.capitalismx.sales.department.SalesDepartment;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.general.GameAlert;
import de.uni.mannheim.capitalismx.ui.util.CapCoinFormatter;
import de.uni.mannheim.capitalismx.utils.data.MessageObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

public class SalesContractController implements Initializable {

	private static final String LangFILE = "properties.main";

	SalesContractInfoController infoPaneController;
	private ArrayList<String> availableIDlist = new ArrayList<String>();
	private ArrayList<String> acceptedIDlist = new ArrayList<String>();
	private boolean firstClick;

	@FXML
	private ListView<Parent> availableContractsList;
	private ObservableList<Parent> availableContracts = FXCollections.observableArrayList();
	@FXML
	private ListView<Parent> acceptedContractsList;
	private ObservableList<Parent> acceptedContracts = FXCollections.observableArrayList();

	@FXML
	private AnchorPane contractInfoPane;

	@FXML
	private Button acceptButton;

	@FXML
	private Button fulfillButton;

	@FXML
	private Button terminateButton;

	@FXML
	private Button refreshButton;

	public SalesContractController() {

	}

	/**
	 * Testing Methods
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	private static int getRandomNumberInRange(int min, int max) {

		Random r = new Random();
		return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();

	}

	/**
	 * This method is called when clicking on the "Accept Contract" button. A
	 * offered contract needs to be selected. It calls
	 * {@link de.uni.mannheim.capitalismx.sales.department.SalesDepartment#addContractToActive(Contract, LocalDate)}
	 * with the selected contract and current game date.
	 */
	@FXML
	public void acceptContract() {
		if (GameState.getInstance().getSalesDepartment().getDoneContracts().size() < 1) {
			MessageObject m = new MessageObject("Your Friend Joe", "" + GameState.getInstance().getGameDate(),
					"Congratulations to your first contract!",
					"Hi!\n\n it's me your friend Joe! I just wanted to tell you how cool it is that you got your first contract!\n\nGood luck with that!\nJoe\n\nP.S.: Chek out this cool jumpy feature!",
					true, getRandomNumberInRange(1, 9));
			GameState.getInstance().getMessages().add(m);
		}
		if (!availableContractsList.getSelectionModel().getSelectedIndices().isEmpty()) {
			int index = availableContractsList.getSelectionModel().getSelectedIndex();
			GameController.getInstance().getSales().acceptContract(index);
			refreshAllContracts();
		}
	}

	/**
	 * Calls
	 * {@link de.uni.mannheim.capitalismx.sales.department.SalesDepartment#contractDone(Contract, LocalDate)}
	 * to fulfill a selected accepted contract before the deadline. The product that
	 * is specified in the contract must be in stock with the correct quantity in
	 * order to function.
	 */
	@FXML
	public void fulfillContract() {
		if (!acceptedContractsList.getSelectionModel().getSelectedIndices().isEmpty()) {
			int index = acceptedContractsList.getSelectionModel().getSelectedIndex();
			GameController.getInstance().getSales().fulfillContract(index);
			refreshAcceptedContracts();
		}
	}

	/**
	 * Terminates a selected contract from the list of accepted contracts.
	 */
	@FXML
	public void terminateContract() {
		if (!acceptedContractsList.getSelectionModel().getSelectedIndices().isEmpty()) {
			int index = acceptedContractsList.getSelectionModel().getSelectedIndex();
			GameController.getInstance().getSales().terminateContract(index);
			refreshAcceptedContracts();
		}
	}

	/**
	 * Receives a contract object and displays it in the UI under "Sales" ->
	 * "Contract Management" -> "Contract Offers"
	 * 
	 * @param c The finished contract that should be displayed in UI.
	 * @param i the index at which it is inserted in the list
	 */
	public void addContractOffer(Contract c, int i, boolean isAccepted) {
		FXMLLoader contractLoader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/component/sales_list_cell.fxml"));

		Parent contract;
		SalesContractListCellController cellController;
		try {
			contract = contractLoader.load();
			cellController = contractLoader.getController();
			cellController.setContractName(c.getProduct().toString());
			cellController.setContractDeadline(c.getTimeToFinish() + " Months");
			cellController.setID(c.getuId());

			if (isAccepted) {
				acceptedIDlist.add(i, c.getuId());
				acceptedContracts.add(i, contract);
				acceptedContractsList.setItems(acceptedContracts);
			} else {
				cellController.setContractDeadline(c.getTimeToFinish() + " Months");
				availableIDlist.add(i, c.getuId());
				availableContracts.add(i, contract);
				availableContractsList.setItems(availableContracts);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This methods displays the info panel for a "Offered Contracts" list entry
	 * that was cicked. This method is called by
	 * 
	 * @param ID ID of contract whose info should be displayed
	 */
	public void showInfoPanel(String ID) {
		// GameState.getInstance().getSalesDepartment().getAvailableContracts().getList().contains();
		SalesDepartment salesDep = GameState.getInstance().getSalesDepartment();
		for (Contract c : salesDep.getAvailableContracts().getList()) {
			if (c.getuId().equals(ID)) {
				infoPanelHandler(c);
			}
		}
		for (Contract c : salesDep.getActiveContracts().getList()) {
			if (c.getuId().equals(ID)) {
				infoPanelHandler(c);
			}
		}
		/*
		 * for(Contract c :
		 * ((ArrayList<Contract>)GameState.getInstance().getSalesDepartment().
		 * getAvailableContracts().getList())) { System.out.println("First Loop Check");
		 * if (c.getuId().equals(ID)) {
		 * 
		 * } }
		 * 
		 */
	}

	/**
	 * This method is called by {@link #showInfoPanel(String ID)} and loads the ino
	 * panels FXML file the first time it is called and sets the information
	 * afterwards.
	 * 
	 * @param c the contract whose information should be displayed.
	 */
	public void infoPanelHandler(Contract c) {
		Parent infoPane;
		if (firstClick) {
			firstClick = false;
			// ResourceBundle bundle = ResourceBundle.getBundle("properties.main");

			FXMLLoader infoLoader = new FXMLLoader(
					getClass().getClassLoader().getResource("fxml/component/sales_info_panel.fxml"),
					UIManager.getResourceBundle());
			try {
				infoPane = infoLoader.load();
				infoPaneController = infoLoader.getController();
				contractInfoPane.getChildren().add(infoPane);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		String deadlineDate;
		String contractStart;
		try{
			deadlineDate = "" + c.getContractStart().plusMonths(c.getTimeToFinish());
		} catch (Exception e){
			deadlineDate = "";
		}
		if(c.getContractStart() == null){
			contractStart = "";
		} else {
			contractStart = ""+c.getContractStart();
		}
		infoPaneController.setInfoPanel("" + c.getProduct(), "" + c.getContractor(), "" + c.getNumProducts(),
				"" + CapCoinFormatter.getCapCoins(c.getNumProducts() * c.getPricePerProd()),
				"" + CapCoinFormatter.getCapCoins(c.getPricePerProd()), "" + c.getTimeToFinish() + " Months",
				"" + contractStart, "" + deadlineDate,
				"" + CapCoinFormatter.getCapCoins(c.getPenalty()));
	}

	/**
	 * Refreshes the List of offered and accepted contracts.
	 */
	public void refreshAllContracts() {
		resetInfoPanel();
		refreshAvailableContracts();
		refreshAcceptedContracts();
	}

	private void resetInfoPanel() {
		infoPaneController.setInfoPanel("", "", "", "", "", "", "", "", "");
	}

	/**
	 * Refreshes the list of offered contracts by deleting the list and re-adding
	 * all entries again
	 */
	public void refreshAvailableContracts() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				removeAllAvailableContracts();
				if (GameState.getInstance() == null || GameState.getInstance().getSalesDepartment() == null || GameState.getInstance().getSalesDepartment().getAvailableContracts().getList().isEmpty()) {

				} else {
					if (((ArrayList<Contract>) GameState.getInstance().getSalesDepartment().getAvailableContracts()
							.getList()).size() > 0) {
						ArrayList<Contract> contractList = ((ArrayList<Contract>) GameState.getInstance()
								.getSalesDepartment().getAvailableContracts().getList());
						for (int i = 0; i < contractList.size(); i++) {
							addContractOffer(contractList.get(i), i, false);
						}
					}
				}

			}
		});
	}

	/**
	 * refreshes the list of accepted contracts.
	 */
	public void refreshAcceptedContracts() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				removeAllAcceptedContracts();
				if (GameState.getInstance() == null || GameState.getInstance().getSalesDepartment() == null) {

				} else {
					if (((CopyOnWriteArrayList<Contract>) GameState.getInstance().getSalesDepartment()
							.getActiveContracts().getList()).size() > 0) {
						CopyOnWriteArrayList<Contract> contractList = ((CopyOnWriteArrayList<Contract>) GameState
								.getInstance().getSalesDepartment().getActiveContracts().getList());
						for (int i = 0; i < contractList.size(); i++) {
							addContractOffer(contractList.get(i), i, true);
						}
					}
				}
			}
		});

	}

	/**
	 * Removes all available contracts and generates new ones for a fee. It works by
	 * calling
	 * {@link de.uni.mannheim.capitalismx.sales.department.SalesDepartment#refreshAvailableContracts(LocalDate, ProductionDepartment, Map)}
	 */
	@FXML
	public void regenerateAvailableContracts() {
		removeAllAvailableContracts();
		try {
			GameController.getInstance().getSales().refreshContracts();
			refreshAvailableContracts();
		} catch (Exception e) {
			GameAlert alert = new GameAlert(AlertType.WARNING, "Could not generate contracts.",
					"Make sure that all conditions to generate contracts are fulfilled and wait a few days.");
			alert.show();
			e.printStackTrace();
		}
	}

	/**
	 * Removes all entries in the list of accepted contracts.
	 */
	public void removeAllAcceptedContracts() {
		if (acceptedContracts.size() > 0) {
			acceptedContracts.clear();
			acceptedIDlist.clear();
			acceptedContractsList.setItems(acceptedContracts);
		}
	}

	/**
	 * Removes all entries in the list of offered contracts.
	 */
	public void removeAllAvailableContracts() {
		if (availableContracts.size() > 0) {
			availableContracts.clear();
			availableIDlist.clear();
			availableContractsList.setItems(availableContracts);
		}

	}

	/**
	 * Sets the tooltip that shows how much it costs to refresh
	 */
	public void setRefreshCostTooltip() {
		try {
			refreshButton.setTooltip(new Tooltip("Deletes current Contract offers and generates new ones for "
					+ GameController.getInstance().getSales().getRefreshCost() + "CC"));
		} catch (NullPointerException e) {
			refreshButton.setTooltip(new Tooltip("Deletes current Contract offers and generates new ones"));
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		firstClick = true;
		// infoPaneController = new SalesContractInfoController();

		// prepare dynamic disabling of buttons
		acceptButton.setDisable(true);
		terminateButton.setDisable(true);
		fulfillButton.setDisable(true);
		acceptedContractsList.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue == null) {
						terminateButton.setDisable(true);
						fulfillButton.setDisable(true);
					} else {
						availableContractsList.getSelectionModel().clearSelection();
						terminateButton.setDisable(false);
						fulfillButton.setDisable(false);
					}
				});
		availableContractsList.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue == null) {
						acceptButton.setDisable(true);
					} else {
						acceptButton.setDisable(false);
						acceptedContractsList.getSelectionModel().clearSelection();
					}
				});

		// load from gamestate when loading a game
		refreshAcceptedContracts();
		refreshAvailableContracts();

		acceptButton.setTooltip(new Tooltip("Accepts a selected contract from the list of offered contracts."));

		fulfillButton.setTooltip(new Tooltip(
				"Fulfills the contract by delivering the finished products. This will remove the products from the warehouse."));

		terminateButton.setTooltip(new Tooltip("Terminates an already accepted contract."));

		setRefreshCostTooltip();

		/*
		 * FXMLLoader cellLoader = new
		 * FXMLLoader(getClass().getClassLoader().getResource(
		 * "fxml/component/sales_list_cell.fxml")); try { infoPane = cellLoader.load();
		 * infoPaneController = cellLoader.getController(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
		infoPaneController = new SalesContractInfoController();

	}
}
