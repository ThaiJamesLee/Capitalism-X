package de.uni.mannheim.capitalismx.ui.controller.module.sales;

import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SalesContractListCellController implements Initializable {

    @FXML
    private Label contractName;

    @FXML
    private Label contractDeadline;

    @FXML
    private HBox contractCellHBox;

    private String ID;

    @FXML
    public void showInfoPanel(){
        System.out.println("List Cell clicked");
        ((SalesContractController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(UIElementType.SALES_CONTRACT_OVERVIEW).getController()).showInfoPanel(ID);
    }

    public void setContractName(String contractName) {
        this.contractName.setText(contractName);
    }

    public void setContractDeadline(String contractDeadline) {
        this.contractDeadline.setText(contractDeadline);
    }

    public void setID(String i){
        this.ID = i;
    }

    public String getIndex(){
        return ID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        contractCellHBox.setOnMouseClicked(e -> {
            System.out.println("List Cell clicked");
            ((SalesContractController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(UIElementType.SALES_CONTRACT_OVERVIEW).getController()).showInfoPanel(ID);
        });

         */
    }
}
