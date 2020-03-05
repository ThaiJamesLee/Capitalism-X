package de.uni.mannheim.capitalismx.ui.controller.module.production;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.production.exceptions.NotEnoughTrainedEngineersException;
import de.uni.mannheim.capitalismx.production.investment.ProductionInvestment;
import de.uni.mannheim.capitalismx.production.investment.ProductionInvestmentLevel;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.general.GameAlert;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The production investment menu controller.
 * It handles the updating of the costs depending on the investment level chosen in their respective choice boxes, the updating of
 * the labels and the handling of investing into a specific production investment.
 *
 * @author dzhao
 */
public class ProductionInvestmentMenuController implements UpdateableController {

    @FXML
    private Label qualityAssuranceInvestmentLabel;

    @FXML
    private Label systemSecurityInvestmentLabel;

    @FXML
    private Label processAutomationInvestmentLabel;

    @FXML
    private Label qualityAssuranceCostLabel;

    @FXML
    private Label systemSecurityCostLabel;

    @FXML
    private Label processAutomationCostLabel;

    @FXML
    private Button qualityAssuranceInvestButton;

    @FXML
    private Button systemSecurityInvestButton;

    @FXML
    private Button processAutomationInvestButton;

    @FXML
    private ChoiceBox<ProductionInvestmentLevel> qualityAssuranceChoiceBox;

    @FXML
    private ChoiceBox<ProductionInvestmentLevel>  systemSecurityChoiceBox;

    @FXML
    private ChoiceBox<ProductionInvestmentLevel>  processAutomationChoiceBox;

    private ObservableList<ProductionInvestmentLevel> productionInvestmentLevelList;

    @Override
    public void update() {
        qualityAssuranceInvestmentLabel.setText(GameController.getInstance().getQualityAssurance().getName(UIManager.getInstance().getLanguage()));
        systemSecurityInvestmentLabel.setText(GameController.getInstance().getSystemSecurity().getName(UIManager.getInstance().getLanguage()));
        processAutomationInvestmentLabel.setText(GameController.getInstance().getProcessAutomation().getName(UIManager.getInstance().getLanguage()));
    }

    /**
     * Invest in quality assurance.
     */
    public void investInQualityAssurance() {
        try {
            GameController.getInstance().investInQualityAssurance(qualityAssuranceChoiceBox.getValue().getLevel());
            GameController.getInstance().decreaseCash(GameState.getInstance().getGameDate(), GameController.getInstance().getProductionInvestmentPrice(qualityAssuranceChoiceBox.getValue()));
            this.update();
        } catch (NotEnoughTrainedEngineersException e) {
            GameAlert error = new GameAlert(Alert.AlertType.WARNING, "Could not invest in Quality Assurance.", e.getMessage());
            error.showAndWait();
        }
    }

    /**
     * Invest in system security.
     */
    public void investInSystemSecurity() {
        try {
            GameController.getInstance().investInSystemSecurity(systemSecurityChoiceBox.getValue().getLevel());
            GameController.getInstance().decreaseCash(GameState.getInstance().getGameDate(), GameController.getInstance().getProductionInvestmentPrice(systemSecurityChoiceBox.getValue()));
            this.update();
        } catch (NotEnoughTrainedEngineersException e) {
            GameAlert error = new GameAlert(Alert.AlertType.WARNING, "Could not invest in System Security.", e.getMessage());
            error.showAndWait();
        }
    }

    /**
     * Invest in process automation.
     */
    public void investInProcessAutomation() {
        try {
            GameController.getInstance().investInProcessAutomation(processAutomationChoiceBox.getValue().getLevel());
            GameController.getInstance().decreaseCash(GameState.getInstance().getGameDate(), GameController.getInstance().getProductionInvestmentPrice(processAutomationChoiceBox.getValue()));
            this.update();
        } catch (NotEnoughTrainedEngineersException e) {
            GameAlert error = new GameAlert(Alert.AlertType.WARNING, "Could not invest in Process Automation.", e.getMessage());
            error.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productionInvestmentLevelList = FXCollections.observableArrayList();
        productionInvestmentLevelList.addAll(ProductionInvestmentLevel.values());
        qualityAssuranceChoiceBox.setItems(productionInvestmentLevelList);
        qualityAssuranceChoiceBox.setValue(productionInvestmentLevelList.get(0));
        qualityAssuranceCostLabel.setText(GameController.getInstance().getProductionInvestmentPrice(qualityAssuranceChoiceBox.getValue())+ "CC");
        qualityAssuranceChoiceBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            qualityAssuranceCostLabel.setText(GameController.getInstance().getProductionInvestmentPrice(newValue)+ "CC");
        });
        systemSecurityChoiceBox.setItems(productionInvestmentLevelList);
        systemSecurityChoiceBox.setValue(productionInvestmentLevelList.get(0));
        systemSecurityCostLabel.setText(GameController.getInstance().getProductionInvestmentPrice(systemSecurityChoiceBox.getValue())+ "CC");
        systemSecurityChoiceBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            systemSecurityCostLabel.setText(GameController.getInstance().getProductionInvestmentPrice(newValue)+ "CC");
        });
        processAutomationChoiceBox.setItems(productionInvestmentLevelList);
        processAutomationChoiceBox.setValue(productionInvestmentLevelList.get(0));
        processAutomationCostLabel.setText(GameController.getInstance().getProductionInvestmentPrice(processAutomationChoiceBox.getValue())+ "CC");
        processAutomationChoiceBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            processAutomationCostLabel.setText(GameController.getInstance().getProductionInvestmentPrice(newValue)+ "CC");
        });

        this.update();
    }
}
