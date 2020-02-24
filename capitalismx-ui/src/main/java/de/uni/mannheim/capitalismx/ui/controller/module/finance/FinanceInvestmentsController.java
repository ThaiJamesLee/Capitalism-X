package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.components.general.GameAlert;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.finance.finance.FinanceDepartment;
import de.uni.mannheim.capitalismx.finance.finance.Investment;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.popover.finance.LoanRequestListController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.FinanceEventListener;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import de.uni.mannheim.capitalismx.ui.utils.TextFieldHelper;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class represents the investments section in the finance UI. Users have the option to invest and disinvest into
 * real estate, stocks, and venture capital.
 *
 * @author sdupper
 */
public class FinanceInvestmentsController implements Initializable {

    @FXML
    Label realEstateLabel;

    @FXML
    TextField realEstateTextField;

    @FXML
    Button buyRealEstateButton;

    @FXML
    Button sellRealEstateButton;

    @FXML
    Label stocksLabel;

    @FXML
    TextField stocksTextField;

    @FXML
    Button buyStocksButton;

    @FXML
    Button sellStocksButton;

    @FXML
    Label ventureCapitalLabel;

    @FXML
    TextField ventureCapitalTextField;

    @FXML
    Button buyVentureCapitalButton;

    @FXML
    Button sellVentureCapitalButton;

    @FXML
    HBox hBoxRealEstate, hBoxStocks, hBoxVentureCapital;

    /**
     * The EventListener for finance events (changes relevant for the finance UI).
     */
    private FinanceEventListener financeEventListener;

    /*
     * Handles investments/disinvestments in real estate, stocks, and venture capital within the respective action
     * listeners. Shows popups in case of errors, e.g., not enough cash to invest.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        financeEventListener = new FinanceEventListener();

        FinanceDepartment f = GameState.getInstance().getFinanceDepartment();
        f.registerPropertyChangeListener(financeEventListener);

        TextFieldHelper.makeTextFieldNumeric(ventureCapitalTextField);
        TextFieldHelper.makeTextFieldNumeric(stocksTextField);
        TextFieldHelper.makeTextFieldNumeric(realEstateTextField);

        VBox.setVgrow(hBoxRealEstate, Priority.ALWAYS);
        VBox.setVgrow(hBoxStocks, Priority.ALWAYS);
        VBox.setVgrow(hBoxVentureCapital, Priority.ALWAYS);

        buyRealEstateButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(realEstateTextField.getText());
                boolean successful = controller.increaseInvestmentAmount(GameState.getInstance().getGameDate(), amount, Investment.InvestmentType.REAL_ESTATE);
                if (!successful) {
                    GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("finance.error.cash.title"),
                            UIManager.getLocalisedString("finance.error.cash.description"));
                    error.showAndWait();
                }
                realEstateTextField.clear();
            } catch (NumberFormatException exception) {
                GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("finance.error.number.title"),
                        UIManager.getLocalisedString("finance.error.number.description"));
                error.showAndWait();
            }
        });

        buyStocksButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(stocksTextField.getText());
                boolean successful = controller.increaseInvestmentAmount(GameState.getInstance().getGameDate(), amount, Investment.InvestmentType.STOCKS);
                if (!successful) {
                    GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("finance.error.cash.title"),
                            UIManager.getLocalisedString("finance.error.cash.description"));
                    error.showAndWait();                }
                stocksTextField.clear();
            } catch (NumberFormatException exception) {
                GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("finance.error.number.title"),
                        UIManager.getLocalisedString("finance.error.number.description"));
                error.showAndWait();
            }
        });

        buyVentureCapitalButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(ventureCapitalTextField.getText());
                boolean successful = controller.increaseInvestmentAmount(GameState.getInstance().getGameDate(), amount,
                        Investment.InvestmentType.VENTURE_CAPITAL);
                if (!successful) {
                    GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("finance.error.cash.title"),
                            UIManager.getLocalisedString("finance.error.cash.description"));
                    error.showAndWait();                }
                ventureCapitalTextField.clear();
            } catch (NumberFormatException exception) {
                GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("finance.error.number.title"),
                        UIManager.getLocalisedString("finance.error.number.description"));
                error.showAndWait();
            }
        });

        sellRealEstateButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(realEstateTextField.getText());
                boolean successful = controller.decreaseInvestmentAmount(GameState.getInstance().getGameDate(), amount, Investment.InvestmentType.REAL_ESTATE);
                if (!successful) {
                    GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("finance.error.cash2.title"),
                            UIManager.getLocalisedString("finance.error.cash2.description"));
                    error.showAndWait();                }
                realEstateTextField.clear();
            } catch (NumberFormatException exception) {
                GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("finance.error.number.title"),
                        UIManager.getLocalisedString("finance.error.number.description"));
                error.showAndWait();            }

        });

        sellStocksButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(stocksTextField.getText());
                boolean successful = controller.decreaseInvestmentAmount(GameState.getInstance().getGameDate(), amount, Investment.InvestmentType.STOCKS);
                if (!successful) {
                    GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("finance.error.cash2.title"),
                            UIManager.getLocalisedString("finance.error.cash2.description"));
                    error.showAndWait();                }
                stocksTextField.clear();
            } catch (NumberFormatException exception) {
                GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("finance.error.number.title"),
                        UIManager.getLocalisedString("finance.error.number.description"));
                error.showAndWait();            }

        });

        sellVentureCapitalButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(ventureCapitalTextField.getText());
                boolean successful = controller.decreaseInvestmentAmount(GameState.getInstance().getGameDate(), amount,
                        Investment.InvestmentType.VENTURE_CAPITAL);
                if (!successful) {
                    GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("finance.error.cash2.title"),
                            UIManager.getLocalisedString("finance.error.cash2.description"));
                    error.showAndWait();                }
                ventureCapitalTextField.clear();
            } catch (NumberFormatException exception) {
                GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("finance.error.number.title"),
                        UIManager.getLocalisedString("finance.error.number.description"));
                error.showAndWait();            }

        });
    }

    public void setRealEstateLabel(String text) {
        Platform.runLater(new Runnable() {
            public void run() {
                realEstateLabel.setText(text);
            }
        });
    }

    public void setStocksLabel(String text) {
        Platform.runLater(new Runnable() {
            public void run() {
                stocksLabel.setText(text);
            }
        });
    }

    public void setVentureCapitalLabel(String text) {
        Platform.runLater(new Runnable() {
            public void run() {
                ventureCapitalLabel.setText(text);
            }
        });
    }
}
