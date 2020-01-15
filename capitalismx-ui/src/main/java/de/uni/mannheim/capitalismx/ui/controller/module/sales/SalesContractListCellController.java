package de.uni.mannheim.capitalismx.ui.controller.module.sales;

import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class SalesContractListCellController {

    @FXML
    private Label contractName;

    @FXML
    private Label contractDeadline;

    @FXML
    private HBox contractCellHBox;

    private Contract c;
    @FXML
    public void showInfoPanel(){

    }

    public void setContractName(String contractName) {
        this.contractName.setText(contractName);
    }

    public void setContractDeadline(String contractDeadline) {
        this.contractDeadline.setText(contractDeadline);
    }

    public void setContract(Contract c){
        this.c = c;
    }

}
