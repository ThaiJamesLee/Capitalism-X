package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.finance.finance.FinanceDepartment;
import de.uni.mannheim.capitalismx.finance.finance.Investment;
import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.GamePageController;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.FinanceEventListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
    Button realEstateButton;

    @FXML
    Label stocksLabel;

    @FXML
    TextField stocksTextField;

    @FXML
    Button stocksButton;

    @FXML
    Label ventureCapitalLabel;

    @FXML
    TextField ventureCapitalTextField;

    @FXML
    Button ventureCapitalButton;

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

        cashLabel.setText(controller.getCash() + "");
        assetsLabel.setText(controller.getAssets() + "");
        liabilitiesLabel.setText(controller.getLiabilities() + "");
        netWorthLabel.setText(controller.getNetWorth() + "");

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

        realEstateButton.setOnAction(e -> {
            Investment investment = controller.generateInvestmentSelection(Double.parseDouble(realEstateTextField.getText())).get(0);
            controller.addInvestment(investment);
            realEstateLabel.setText("Amount: " + investment.getAmount());
            realEstateTextField.clear();
            //TODO update GUI
            //controller.calculateNetWorth(GameState.getInstance().getGameDate());
        });

        stocksButton.setOnAction(e -> {
            Investment investment = controller.generateInvestmentSelection(Double.parseDouble(stocksTextField.getText())).get(1);
            controller.addInvestment(investment);
            stocksLabel.setText("Amount: " + investment.getAmount());
            stocksTextField.clear();
            //TODO update GUI
            //controller.calculateNetWorth(GameState.getInstance().getGameDate());
        });

        ventureCapitalButton.setOnAction(e -> {
            Investment investment = controller.generateInvestmentSelection(Double.parseDouble(ventureCapitalTextField.getText())).get(2);
            controller.addInvestment(investment);
            ventureCapitalLabel.setText("Amount: " + investment.getAmount());
            ventureCapitalTextField.clear();
            //TODO update GUI
            //controller.calculateNetWorth(GameState.getInstance().getGameDate());
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

    public double getLoanAmount() {
        return this.loanAmount;
    }

    public void addLoan(BankingSystem.Loan loan){
        loanLabel.setText("" + loan.getLoanAmount() + "CC - Duration: " +
                loan.getDuration() + " Months");
        loanAmountTextField.clear();
    }
}
