package de.uni.mannheim.capitalismx.ui.components.logistics;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.LogisticsPartnerController;
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
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/logistics_partner_detail_list_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            GameController controller = GameController.getInstance();
            nameLabel.setText(externalPartner.getName());
            reliabilityIndexLabel.setText("Reliability: " + externalPartner.getReliabilityIndexPartner());
            ecoIndexLabel.setText("Eco Index: " + externalPartner.getEcoIndexPartner());
            qualityIndexLabel.setText("Quality Index: " + externalPartner.getQualityIndexPartner());
            contractualCostsLabel.setText("Contractual Costs: " + externalPartner.getContractualCost() + " CC");
            variableCostsLabel.setText("Delivery Costs: " + externalPartner.getVariableDeliveryCost() + " CC");
            gridPane.setOnMouseClicked(e -> {
                controller.addExternalPartner(externalPartner);
                LogisticsPartnerController uiController = (LogisticsPartnerController) UIManager.getInstance().getGameView(GameViewType.LOGISTIC).getModule(UIElementType.LOGISTICS_PARTNER_OVERVIEW).getController();
                uiController.addExternalPartner(externalPartner);
                
                logisticsPartnerDetailListView.getSelectionModel().clearSelection();
            });

            setText(null);
            setGraphic(gridPane);
        }
    }

}