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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SalesContractController extends GameModuleController {

    SalesEventListener contractListener;

    private Parent infoPane;
    private SalesContractInfoController infoPaneController;


    @FXML
    private ListView offeredContractsList;
    private ObservableList<Parent> offeredContracts = FXCollections.observableArrayList();
    @FXML
    private ListView acceptedContractsList;
    private ObservableList<Parent> acceptedContracts = FXCollections.observableArrayList();

    @FXML
    private AnchorPane contractInfoPane;

    @FXML
    public void acceptContract(){

    }

    @FXML
    public void terminateContract(){

    }

    public void addContract(Contract c, int i){
        FXMLLoader contractLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/sales_list_cell.fxml"));
        Parent contract;
        SalesContractListCellController cellController = new SalesContractListCellController();
        try{
            contract = contractLoader.load();
            cellController = contractLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        cellController.setContractName(c.getProduct().toString());
        cellController.setIndex(i);
        

    }

    public void removeContract(){
        
    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contractListener = new SalesEventListener();

        GameState.getInstance().getSalesDepartment().registerPropertyChangeListener(contractListener);

        FXMLLoader cellLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/sales_list_cell.fxml"));
        try {
            infoPane = cellLoader.load();
            infoPaneController = cellLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
