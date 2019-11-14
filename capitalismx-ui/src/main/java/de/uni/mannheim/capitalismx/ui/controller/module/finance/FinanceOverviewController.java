package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.finance.finance.FinanceDepartment;
import de.uni.mannheim.capitalismx.finance.finance.Investment;
import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.FinanceEventListener;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FinanceOverviewController extends GameModuleController {

    @FXML
    Label cashLabel;

    @FXML
    Label assetsLabel;

    @FXML
    Label liabilitiesLabel;

    @FXML
    Label netWorthLabel;

    @FXML
    Button loanRequestButton;

    @FXML
    TextField loanAmountTextField;

    @FXML
    Label loanLabel;

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

    private FinanceEventListener financeEventListener;

    private double loanAmount;

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        financeEventListener = new FinanceEventListener();

        FinanceDepartment f = GameState.getInstance().getFinanceDepartment();
        f.addPropertyChangeListener(financeEventListener);

        cashLabel.setText(DecimalRound.round(controller.getCash(), 2) + "");
        assetsLabel.setText(DecimalRound.round(controller.getAssets(), 2) + "");
        liabilitiesLabel.setText(DecimalRound.round(controller.getLiabilities(), 2) + "");
        netWorthLabel.setText(DecimalRound.round(controller.getNetWorth(), 2) + "");

        loanRequestButton.setOnAction(e -> {
            try {
                this.loanAmount = Double.parseDouble(loanAmountTextField.getText());
                UIManager.getInstance().getGamePageController().showOverlay(UIElementType.FINANCE_OVERVIEW);
                //this.loanAmount = NULL;
                loanAmountTextField.clear();
            }catch (NumberFormatException exception){
                //TODO tooltip/popup
                System.out.println("Not a valid loan amount!");
            }
        });

        buyRealEstateButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(realEstateTextField.getText());
                boolean successful = controller.increaseInvestmentAmount(amount, Investment.InvestmentType.REAL_ESTATE);
                if(!successful){
                    //TODO popup not enough cash
                }
                realEstateTextField.clear();
            }catch (NumberFormatException exception) {
                //TODO invalid input
            }
        });

        buyStocksButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(stocksTextField.getText());
                boolean successful = controller.increaseInvestmentAmount(amount, Investment.InvestmentType.STOCKS);
                if(!successful){
                    //TODO popup not enough cash
                }
                stocksTextField.clear();
            }catch (NumberFormatException exception) {
                //TODO invalid input
            }
        });

        buyVentureCapitalButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(ventureCapitalTextField.getText());
                boolean successful = controller.increaseInvestmentAmount(amount, Investment.InvestmentType.VENTURE_CAPITAL);
                if(!successful){
                    //TODO popup not enough cash
                }
                ventureCapitalTextField.clear();
            }catch (NumberFormatException exception) {
                //TODO invalid input
            }
        });

        sellRealEstateButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(realEstateTextField.getText());
                boolean successful = controller.decreaseInvestmentAmount(amount, Investment.InvestmentType.REAL_ESTATE);
                if(!successful){
                    //TODO popup not enough cash
                }
                realEstateTextField.clear();
            }catch (NumberFormatException exception) {
                //TODO invalid input
            }

        });

        sellStocksButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(stocksTextField.getText());
                boolean successful = controller.decreaseInvestmentAmount(amount, Investment.InvestmentType.STOCKS);
                if(!successful){
                    //TODO popup not enough cash
                }
                stocksTextField.clear();
            }catch (NumberFormatException exception) {
                //TODO invalid input
            }

        });

        sellVentureCapitalButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(ventureCapitalTextField.getText());
                boolean successful = controller.decreaseInvestmentAmount(amount, Investment.InvestmentType.VENTURE_CAPITAL);
                if(!successful){
                    //TODO popup not enough cash
                }
                ventureCapitalTextField.clear();
            }catch (NumberFormatException exception) {
                //TODO invalid input
            }

        });
    }

    public void setNetWorthLabel(String text){
        Platform.runLater(new Runnable() {
            public void run() {
                netWorthLabel.setText(text);
            }
        });
    }

    public void setCashLabel(String text){
        Platform.runLater(new Runnable() {
            public void run() {
                cashLabel.setText(text);
            }
        });
    }

    public void setAssetsLabel(String text){
        Platform.runLater(new Runnable() {
            public void run() {
                assetsLabel.setText(text);
            }
        });
    }

    public void setLiabilitiesLabel(String text){
        Platform.runLater(new Runnable() {
            public void run() {
                liabilitiesLabel.setText(text);
            }
        });
    }

    public void setRealEstateLabel(String text){
        Platform.runLater(new Runnable() {
            public void run() {
                realEstateLabel.setText(text);
            }
        });
    }

    public void setStocksLabel(String text){
        Platform.runLater(new Runnable() {
            public void run() {
                stocksLabel.setText(text);
            }
        });
    }

    public void setVentureCapitalLabel(String text){
        Platform.runLater(new Runnable() {
            public void run() {
                ventureCapitalLabel.setText(text);
            }
        });
    }

    public double getLoanAmount() {
        return this.loanAmount;
    }

    public void addLoan(BankingSystem.Loan loan){
        Platform.runLater(new Runnable() {
            public void run() {
                loanLabel.setText("" + DecimalRound.round(loan.getLoanAmount(), 2) + "CC - Duration: " +
                        loan.getDuration() + " Months");
                loanAmountTextField.clear();
            }
        });
    }
}
