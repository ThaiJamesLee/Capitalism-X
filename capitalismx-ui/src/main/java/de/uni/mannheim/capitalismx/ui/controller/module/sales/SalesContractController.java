package de.uni.mannheim.capitalismx.ui.controller.module.sales;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import de.uni.mannheim.capitalismx.ui.components.GameModuleDefinition;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.SalesEventListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SalesContractController extends GameModuleController {

    SalesEventListener contractListener;
    SalesContractInfoController infoPaneController;
    private ArrayList<String> IDlist = new ArrayList<String>();
    private boolean firstClick;

    @FXML
    private ListView offeredContractsList;
    private ObservableList<Parent> offeredContracts = FXCollections.observableArrayList();
    @FXML
    private ListView acceptedContractsList;
    private ObservableList<Parent> acceptedContracts = FXCollections.observableArrayList();

    @FXML
    private AnchorPane contractInfoPane;

    public SalesContractController(){

    }

    @FXML
    public void acceptContract(){

    }

    @FXML
    public void terminateContract(){

    }

    public void addContractOffer(Contract c, int i){
        FXMLLoader contractLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/sales_list_cell.fxml"));
        Parent contract = new Pane();
        SalesContractListCellController cellController = new SalesContractListCellController();
        try{
            contract = contractLoader.load();
            cellController = contractLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        cellController.setContractName(c.getProduct().toString());
        IDlist.add(c.getuId());
        cellController.setID(c.getuId());
        offeredContracts.add(0, contract);
        offeredContractsList.setItems(offeredContracts);
    }

    public void removeContract(){
        
    }

    public void showInfoPanel(String ID){
        for(Contract c : ((List<Contract>)GameState.getInstance().getSalesDepartment().getAvailableContracts())) {
            if (c.getuId().equals(ID)) {
                Parent infoPane;
                if(firstClick){
                    firstClick = false;

                    FXMLLoader infoLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/sales_info_panel.fxml"));
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
