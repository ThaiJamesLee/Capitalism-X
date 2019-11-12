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
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
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

        realEstateButton.setOnAction(e -> {
            ArrayList<Investment> investmentSelection = controller.generateInvestmentSelection(Double.parseDouble(realEstateTextField.getText()));
            if(investmentSelection != null){
                Investment investment = investmentSelection.get(0);
                controller.addInvestment(investment);
                controller.increaseRealEstateInvestmentAmount(investment.getAmount());
                realEstateLabel.setText("Amount: " + DecimalRound.round(controller.getRealEstateInvestmentAmount(), 2));
                realEstateTextField.clear();
                //TODO update GUI
                //controller.calculateNetWorth(GameState.getInstance().getGameDate());
            }else{
                //TODO popup
                realEstateTextField.clear();
            }

        });

        stocksButton.setOnAction(e -> {
            ArrayList<Investment> investmentSelection = controller.generateInvestmentSelection(Double.parseDouble(stocksTextField.getText()));
            if(investmentSelection != null){
                Investment investment = investmentSelection.get(1);
                controller.addInvestment(investment);
                controller.increaseStocksInvestmentAmount(investment.getAmount());
                stocksLabel.setText("Amount: " + DecimalRound.round(controller.getStocksInvestmentAmount(), 2));
                stocksTextField.clear();
                //TODO update GUI
                //controller.calculateNetWorth(GameState.getInstance().getGameDate());
            }else{
                //TODO popup
                stocksTextField.clear();
            }
        });

        ventureCapitalButton.setOnAction(e -> {
            ArrayList<Investment> investmentSelection = controller.generateInvestmentSelection(Double.parseDouble(ventureCapitalTextField.getText()));
            if(investmentSelection != null){
                Investment investment = investmentSelection.get(2);
                controller.addInvestment(investment);
                controller.increaseVentureCapitalInvestmentAmount(investment.getAmount());
                ventureCapitalLabel.setText("Amount: " + DecimalRound.round(controller.getVentureCapitalInvestmentAmount(), 2));
                ventureCapitalTextField.clear();
                //TODO update GUI
                //controller.calculateNetWorth(GameState.getInstance().getGameDate());
            }else{
                //TODO popup
                ventureCapitalTextField.clear();
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
