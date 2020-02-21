package de.uni.mannheim.capitalismx.ui.controller.module.sales;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import de.uni.mannheim.capitalismx.sales.department.SalesDepartment;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.SalesEventListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class SalesContractController extends GameModuleController {

    SalesEventListener contractListener;
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

    public SalesContractController(){

    }

    @FXML
    public void acceptContract(){
        int index = availableContractsList.getSelectionModel().getSelectedIndex();
        SalesDepartment salesDep = GameState.getInstance().getSalesDepartment();
        salesDep.addContractToActive(salesDep.getAvailableContracts().get(index), GameState.getInstance().getGameDate());
        refreshAvailableContracts();
        refreshAcceptedContracts();
    }

    /**
     * Removes a contract from the list of accepted contracts
     */
    @FXML
    public void terminateContract(){
        int index = acceptedContractsList.getSelectionModel().getSelectedIndex();
        SalesDepartment salesDep = GameState.getInstance().getSalesDepartment();
        /* todo: decrease cash as penalty */
    }

    public void refreshAvailableContracts(){
        removeAllAvailableContracts();
        ArrayList<Contract> contractList = ((ArrayList<Contract>) GameState.getInstance().getSalesDepartment().getAvailableContracts().getList());
        for(int i = 0; i < contractList.size(); i++){
            addContractOffer(contractList.get(i), i, false);
        }
    }

    public void refreshAcceptedContracts(){
        removeAllAcceptedContracts();
        CopyOnWriteArrayList<Contract> contractList = ((CopyOnWriteArrayList<Contract>)GameState.getInstance().getSalesDepartment().getActiveContracts().getList());
        for(int i = 0; i < contractList.size(); i++){
            addContractOffer(contractList.get(i), i, true);
        }
    }


    /**
     * Receives a contract object and displays it in the UI under "Sales" -> "Contract Management" -> "Contract Offers"
     * @param c The finished contract that should be displayed in UI.
     * @param i the index at which it is inserted
     */
    public void addContractOffer(Contract c, int i, boolean isAccepted){
        FXMLLoader contractLoader = new FXMLLoader(getClass().getClassLoader()
                .getResource("fxml/components/sales_list_cell.fxml"));

        Parent contract;
        SalesContractListCellController cellController;
        try{
            contract = contractLoader.load();
            cellController = contractLoader.getController();
            cellController.setContractName(c.getProduct().toString());
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
     * @param ID
     */
    public void showInfoPanel(String ID){
        System.out.println("showInfoPanel  Check");
        for(Contract c : ((ArrayList<Contract>)GameState.getInstance().getSalesDepartment().getAvailableContracts().getList())) {
            System.out.println("First Loop Check");
            if (c.getuId().equals(ID)) {
                Parent infoPane;
                if(firstClick){
                    firstClick = false;
                    System.out.println("Loop - If Check");
                    //ResourceBundle bundle = ResourceBundle.getBundle("properties.main");

                    FXMLLoader infoLoader = new FXMLLoader(getClass().getClassLoader()
                            .getResource("fxml/components/sales_info_panel.fxml"));
                    try {
                        infoPane = infoLoader.load();
                        infoPaneController = infoLoader.getController();
                        contractInfoPane.getChildren().add(infoPane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                infoPaneController.setInfoPanel(
                        "" + c.getProduct(),
                        "" + c.getContractor(),
                        "" + c.getNumProducts(),
                        "" + (c.getNumProducts() * c.getPricePerProd()),
                        "" + c.getPricePerProd(),
                        "" + c.getTimeToFinish(),
                        "" + c.getContractStart(),
                        "" + c.getContractDoneDate(),
                        "" + c.getPenalty()
                );
            }
        }

    }

    public void removeAllAcceptedContracts(){
        acceptedContracts.clear();
        acceptedIDlist.clear();
        acceptedContractsList.setItems(acceptedContracts);
    }

    public void removeAllAvailableContracts(){
        availableContracts.clear();
        availableIDlist.clear();
        availableContractsList.setItems(availableContracts);
    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstClick = true;
        contractListener = new SalesEventListener();
        //infoPaneController = new SalesContractInfoController();
        GameState.getInstance().getSalesDepartment().registerPropertyChangeListener(contractListener);
/*
        FXMLLoader cellLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/sales_list_cell.fxml"));
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
