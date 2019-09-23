package de.uni.mannheim.capitalismx.ui.components.logistics;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.application.Main;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.LogisticsPartnerController;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.TruckFleetController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class LogisticsPartnerDetailListViewCell extends ListCell<ExternalPartner> {

    @FXML
    private Label nameLabel;

    @FXML
    private Label reliabilityIndexLabel;

    @FXML
    private Label ecoIndexLabel;

    @FXML
    private Label qualityIndexLabel;

    @FXML
    GridPane gridPane;

    @FXML
    private Label contractualCostsLabel;

    @FXML
    private Label variableCostsLabel;

    @FXML
    private Button buyButton;

    private FXMLLoader loader;

    private ListView<ExternalPartner> logisticsPartnerDetailListView;

    public LogisticsPartnerDetailListViewCell(ListView<ExternalPartner> logisticsPartnerDetailListView){
        this.logisticsPartnerDetailListView = logisticsPartnerDetailListView;
    }

    @Override
    protected void updateItem(ExternalPartner externalPartner, boolean empty) {
        super.updateItem(externalPartner, empty);
        if(empty || externalPartner == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/logistics_partner_detail_list_cell.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            GameController controller = GameController.getInstance();
            nameLabel.setText("Name: " + externalPartner.getName());
            reliabilityIndexLabel.setText("Reliability Index: " + externalPartner.getReliabilityIndexPartner());
            ecoIndexLabel.setText("Eco Index: " + externalPartner.getEcoIndexPartner());
            qualityIndexLabel.setText("Quality Index: " + externalPartner.getQualityIndexPartner());
            contractualCostsLabel.setText("Contractual Costs: " + externalPartner.getContractualCost() + " CC");
            variableCostsLabel.setText("Variable Delivery Costs: " + externalPartner.getVariableDeliveryCost() + " CC");
            buyButton.setOnAction(e -> {
                controller.addExternalPartner(externalPartner);
                LogisticsPartnerController uiController = (LogisticsPartnerController) Main.getManager().getGameView(GameViewType.LOGISTIC).getModule(UIElementType.LOGISTICS_PARTNER_OVERVIEW).getController();
                uiController.addExternalPartner(externalPartner);
            });

            setText(null);
            setGraphic(gridPane);
        }
    }

}