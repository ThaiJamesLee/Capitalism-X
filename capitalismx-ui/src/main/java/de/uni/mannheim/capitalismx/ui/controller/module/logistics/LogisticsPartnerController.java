package de.uni.mannheim.capitalismx.ui.controller.module.logistics;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.logistic.logistics.Logistics;
import de.uni.mannheim.capitalismx.ui.application.Main;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.OperationsTableController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LogisticsPartnerController  extends GameModuleController {

    @FXML
    private Button logisticsPartnerButton;

    @FXML
    private TextArea currentlyEmployingTextArea;

    public LogisticsPartnerController() {
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        if(controller.getExternalPartner() != null){
            currentlyEmployingTextArea.setText("Currently employing\n" + controller.getExternalPartner().getName() + " " + controller.getExternalPartner().getContractualCost());
        }else{
            currentlyEmployingTextArea.setText("Currently employing no external partner");
        }

        logisticsPartnerButton.setOnAction(e -> {
            Main.getManager().getGamePageController().showOverlay(UIElementType.LOGISTICS_PARTNER_OVERVIEW);
        });
    }

    public void addExternalPartner(ExternalPartner externalPartner){
        currentlyEmployingTextArea.setText("Currently employing\n" + externalPartner.getName());
    }

}