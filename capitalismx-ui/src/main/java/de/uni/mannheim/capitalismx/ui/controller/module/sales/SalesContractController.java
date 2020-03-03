package de.uni.mannheim.capitalismx.ui.controller.module.sales;

import de.uni.mannheim.capitalismx.department.WarehousingDepartment;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Unit;
import de.uni.mannheim.capitalismx.production.department.ProductionDepartment;
import de.uni.mannheim.capitalismx.production.product.Product;
import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import de.uni.mannheim.capitalismx.sales.department.SalesDepartment;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.eventlistener.SalesEventListener;
import de.uni.mannheim.capitalismx.ui.util.CapCoinFormatter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

import javax.tools.Tool;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class SalesContractController implements Initializable {

    private static final String LangFILE = "properties.main";

    SalesContractInfoController infoPaneController;
    private ArrayList<String> availableIDlist = new ArrayList<String>();
    private ArrayList<String> acceptedIDlist = new ArrayList<String>();
    private boolean firstClick;

    @FXML
    private ListView availableContractsList;
    private ObservableList<Parent> availableContracts = FXCollections.observableArrayList();
    @FXML
    private ListView acceptedContractsList;
    private ObservableList<Parent> acceptedContracts = FXCollections.observableArrayList();

    @FXML
    private AnchorPane contractInfoPane;

    @FXML
    private Button acceptButton;

    @FXML
    private Button fulfillButton;

    @FXML
    private Button terminateButton;

    public SalesContractController(){

    }

    /**
     * This method is called when clicking on the "Accept Contract" button. A offered contract needs to be selected.
     * It calls {@link de.uni.mannheim.capitalismx.sales.department.SalesDepartment#addContractToActive(Contract, LocalDate)} with the selected contract and current game date.
     */
    @FXML
    public void acceptContract(){
        if(!availableContractsList.getSelectionModel().getSelectedIndices().isEmpty()) {
            int index = availableContractsList.getSelectionModel().getSelectedIndex();
            GameController.getInstance().getSales().acceptContract(index);
            refreshAllContracts();
        }
    }

    /**
     * Calls {@link de.uni.mannheim.capitalismx.sales.department.SalesDepartment#contractDone(Contract, LocalDate)} to fulfill a selected accepted contract before the deadline.
     * The product that is specified in the contract must be in stock with the correct quantity in order to function.
     */
    @FXML
    public void fulfillContract(){
        if(!acceptedContractsList.getSelectionModel().getSelectedIndices().isEmpty()){
            int index = acceptedContractsList.getSelectionModel().getSelectedIndex();
            GameController.getInstance().getSales().fulfillContract(index);
            refreshAcceptedContracts();
            // System.out.println("Active Contract Number: " + salesDep.getActiveContracts().size());
        }
    }

    /**
     * Terminates a selected contract from the list of accepted contracts.
     */
    @FXML
    public void terminateContract(){
        if(!acceptedContractsList.getSelectionModel().getSelectedIndices().isEmpty()) {
            int index = acceptedContractsList.getSelectionModel().getSelectedIndex();
            GameController.getInstance().getSales().terminateContract(index);
            refreshAcceptedContracts();
        }
    }




    /**
     * Receives a contract object and displays it in the UI under "Sales" -> "Contract Management" -> "Contract Offers"
     * @param c The finished contract that should be displayed in UI.
     * @param i the index at which it is inserted in the list
     */
    public void addContractOffer(Contract c, int i, boolean isAccepted){
        FXMLLoader contractLoader = new FXMLLoader(getClass().getClassLoader()
                .getResource("fxml/component/sales_list_cell.fxml"));

        Parent contract;
        SalesContractListCellController cellController;
        try{
            contract = contractLoader.load();
            cellController = contractLoader.getController();
            cellController.setContractName(c.getProduct().toString());
            cellController.setContractDeadline(String.valueOf(c.getTimeToFinish() + " Months"));
            cellController.setID(c.getuId());

            if(isAccepted){
                acceptedIDlist.add(i, c.getuId());
                acceptedContracts.add(i, contract);
                acceptedContractsList.setItems(acceptedContracts);
            } else {
                availableIDlist.add(i, c.getuId());
                availableContracts.add(i, contract);
                availableContractsList.setItems(availableContracts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * This methods displays the info panel for a "Offered Contracts" list entry that was cicked.
     * This method is called by
     * @param ID ID of contract whose info should be displayed
     */
    public void showInfoPanel(String ID){
        System.out.println("showInfoPanel Check");
        //GameState.getInstance().getSalesDepartment().getAvailableContracts().getList().contains();
        SalesDepartment salesDep = GameState.getInstance().getSalesDepartment();
        for(Contract c : salesDep.getAvailableContracts().getList()){
            if (c.getuId().equals(ID)) {
                infoPanelHandler(c);
            }
        }
        for(Contract c : salesDep.getActiveContracts().getList()){
            if (c.getuId().equals(ID)) {
                infoPanelHandler(c);
            }
        }
        /*
        for(Contract c : ((ArrayList<Contract>)GameState.getInstance().getSalesDepartment().getAvailableContracts().getList())) {
            System.out.println("First Loop Check");
            if (c.getuId().equals(ID)) {

            }
        }

         */
    }

    /**
     * This method is called by {@link #showInfoPanel(String ID)} and loads the ino panels FXML file the first time it is called and sets the information afterwards.
     * @param c the contract whose information should be displayed.
     */
    public void infoPanelHandler(Contract c){
        Parent infoPane;
        if(firstClick){
            firstClick = false;
            System.out.println("Loop - If Check");
            //ResourceBundle bundle = ResourceBundle.getBundle("properties.main");

            FXMLLoader infoLoader = new FXMLLoader(getClass().getClassLoader()
                    .getResource("fxml/component/sales_info_panel.fxml"), UIManager.getResourceBundle());
            try {
                infoPane = infoLoader.load();
                infoPaneController = infoLoader.getController();
                contractInfoPane.getChildren().add(infoPane);
                System.out.println("Info Panel should show now");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        infoPaneController.setInfoPanel(
                "" + c.getProduct(),
                "" + c.getContractor(),
                "" + c.getNumProducts(),
                "" + CapCoinFormatter.getCapCoins(c.getNumProducts() * c.getPricePerProd()),
                "" + CapCoinFormatter.getCapCoins(c.getPricePerProd()),
                "" + c.getTimeToFinish() + " Months",
                "" + c.getContractStart(),
                "" + c.getContractDoneDate(),
                "" + CapCoinFormatter.getCapCoins(c.getPenalty())
        );
        System.out.println("Info for: " + c.getContractor());
    }

    /**
     * Refreshes the List of offered and accepted contracts.
     */
    public void refreshAllContracts(){
        refreshAvailableContracts();
        refreshAcceptedContracts();
    }

    /**
     * Refreshes the list of offered contracts by deleting the list and re-adding all entries again
     */
    public void refreshAvailableContracts(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                removeAllAvailableContracts();
                if(GameState.getInstance() == null || GameState.getInstance().getSalesDepartment() == null){

                } else {
                    if (((ArrayList<Contract>) GameState.getInstance().getSalesDepartment().getAvailableContracts().getList()).size() > 0) {
                        ArrayList<Contract> contractList = ((ArrayList<Contract>) GameState.getInstance().getSalesDepartment().getAvailableContracts().getList());
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
    public void refreshAcceptedContracts(){
        /*
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

         */
                removeAllAcceptedContracts();
                if(GameState.getInstance() == null || GameState.getInstance().getSalesDepartment() == null){

                } else {
                    if (((CopyOnWriteArrayList<Contract>) GameState.getInstance().getSalesDepartment().getActiveContracts().getList()).size() > 0) {
                        CopyOnWriteArrayList<Contract> contractList = ((CopyOnWriteArrayList<Contract>) GameState.getInstance().getSalesDepartment().getActiveContracts().getList());
                        for (int i = 0; i < contractList.size(); i++) {
                            addContractOffer(contractList.get(i), i, true);
                        }
                    }
                }
                /*
            }
        });

                 */
    }

    /**
     * Removes all available contracts and generates new ones for a fee. It works by calling {@link de.uni.mannheim.capitalismx.sales.department.SalesDepartment#refreshAvailableContracts(LocalDate, ProductionDepartment, Map)}
     */
    @FXML
    public void regenerateAvailableContracts(){
        System.out.println("Create new Contracts");
                removeAllAvailableContracts();
                System.out.println("Create new Contraaaaaaaacts");
                try{
                    GameController.getInstance().getSales().refreshContracts();
                    refreshAvailableContracts();
                    System.out.println("Create new Contraaaaaaaaaaaaaaaaaaaaacts");
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

    /**
     * Removes all entries in the list of accepted contracts.
     */
    public void removeAllAcceptedContracts(){
        if(acceptedContracts.size() > 0){
            acceptedContracts.clear();
            acceptedIDlist.clear();
            acceptedContractsList.setItems(acceptedContracts);
        }
    }

    /**
     * Removes all entries in the list of offered contracts.
     */
    public void removeAllAvailableContracts(){
        if(availableContracts.size() > 0){
            availableContracts.clear();
            availableIDlist.clear();
            availableContractsList.setItems(availableContracts);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstClick = true;
        //infoPaneController = new SalesContractInfoController();

        acceptButton.setTooltip(
                new Tooltip("Accepts a selected contract from the list of offered contracts.")
        );

        fulfillButton.setTooltip(
                new Tooltip("Fulfills the contract by delivering the finished products. This will remove the products from the warehouse.")
        );

        terminateButton.setTooltip(
                new Tooltip("Terminates an already accepted contract.")
        );

/*
        FXMLLoader cellLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component/sales_list_cell.fxml"));
        try {
            infoPane = cellLoader.load();
            infoPaneController = cellLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        infoPaneController = new SalesContractInfoController();

    }
}
