package de.uni.mannheim.capitalismx.ui.controller.module.sales;

import de.uni.mannheim.capitalismx.ui.components.GameModuleDefinition;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SalesContractController extends GameModuleController {

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

    public void addContract(){
        
    }

    public void removeContract(){

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
