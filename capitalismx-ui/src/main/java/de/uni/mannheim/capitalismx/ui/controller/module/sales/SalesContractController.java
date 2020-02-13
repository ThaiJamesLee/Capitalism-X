package de.uni.mannheim.capitalismx.ui.controller.module.sales;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
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
import java.util.concurrent.CopyOnWriteArrayList;

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
        System.out.println("Accept Button Clicked");
    }

    @FXML
    public void terminateContract(){

    }

    /**
     *
     * @param c
     * @param i
     */
    public void addContractOffer(Contract c, int i){
        /*
        FXMLLoader contractLoader = new FXMLLoader(getClass().getClassLoader()
                .getResource("fxml/components/sales_list_cell.fxml"));
        Parent contract = new Pane();
        SalesContractListCellController cellController = new SalesContractListCellController();
        */
        FXMLLoader contractLoader = new FXMLLoader(getClass().getClassLoader()
                .getResource("fxml/components/sales_list_cell.fxml"));

        Parent contract;
        SalesContractListCellController cellController;
        try{

            //Parent contract = new Pane();
            //SalesContractListCellController cellController = new SalesContractListCellController();
            contract = contractLoader.load();
            cellController = contractLoader.getController();

            cellController.setContractName(c.getProduct().toString());
            IDlist.add(c.getuId());
            cellController.setID(c.getuId());
            offeredContracts.add(0, contract);
            offeredContractsList.setItems(offeredContracts);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        cellController.setContractName(c.getProduct().toString());
        IDlist.add(c.getuId());
        cellController.setID(c.getuId());
        offeredContracts.add(0, contract);
        offeredContractsList.setItems(offeredContracts);
        */
    }

    public void removeContract(){
        
    }

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
