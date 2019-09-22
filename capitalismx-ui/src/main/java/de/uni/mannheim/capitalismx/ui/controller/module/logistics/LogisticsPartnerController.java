package de.uni.mannheim.capitalismx.ui.controller.module.logistics;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.logistic.logistics.Logistics;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.OperationsTableController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LogisticsPartnerController  extends GameModuleController {

    @FXML
    private MenuButton logisticsPartnerDropdown;

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
        ArrayList<ExternalPartner> externalPartners = controller.generateExternalPartnerSelection();
        //ObservableList<String> data = FXCollections.observableArrayList();

        MenuItem[] logisticsPartnerMenuItems = new MenuItem[externalPartners.size()];

        int i = 0;
        for(ExternalPartner externalPartner : externalPartners) {
            logisticsPartnerMenuItems[i] = new MenuItem(externalPartner.getName() + " Contractual Costs: " + externalPartner.getContractualCost());
            logisticsPartnerMenuItems[i].setOnAction(e -> {
                controller.addExternalPartner(externalPartner);
                currentlyEmployingTextArea.setText("Currently employing\n" + externalPartner.getName() + " " + controller.getExternalPartner().getContractualCost());
                //selectMenuItem((MenuItem) e.getSource());
            });
            //data.add("Partner " + i + " Contractual Costs: " + externalPartner.getContractualCost());
            i++;
        }

        logisticsPartnerDropdown.getItems().addAll(logisticsPartnerMenuItems);

    }

}