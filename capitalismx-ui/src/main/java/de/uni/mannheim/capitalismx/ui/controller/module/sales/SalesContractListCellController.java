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

    private int index;
    @FXML
    public void showInfoPanel(){

    }

    public void setContractName(String contractName) {
        this.contractName.setText(contractName);
    }

    public void setContractDeadline(String contractDeadline) {
        this.contractDeadline.setText(contractDeadline);
    }

    public void setIndex(int i){
        this.index = i;
    }

    public int getIndex(){
        return index;
    }

}
