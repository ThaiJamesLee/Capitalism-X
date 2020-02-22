package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import java.net.URL;
import java.util.ResourceBundle;

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
 * This class represents the banking system in the finance UI. Users have the option to request loans and invest into
 * real estate, stocks, and venture capital.
 *
 * @author sdupper
 */
public class FinanceBankingSystemController implements Initializable {

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

    /**
     * The EventListener for finance events (changes relevant for the finance UI).
     */
    private FinanceEventListener financeEventListener;

    /**
     * The controller of the loan request list with all available loans.
     */
    private LoanRequestListController loanRequestListController;

    /**
     * The popover for the loan selection.
     */
    private PopOver loanRequestPopover;

    private double loanAmount;

    /*
     * Handles loan requests and investments/disinvestments in real estate, stocks, and venture capital within the
     * respective action listeners.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        financeEventListener = new FinanceEventListener();

        FinanceDepartment f = GameState.getInstance().getFinanceDepartment();
        f.registerPropertyChangeListener(financeEventListener);

        // Prepare the Popover for the loan request
        PopOverFactory factory = new PopOverFactory();

        factory.createStandardPopover("fxml/popover/loan_request_list.fxml");
        loanRequestPopover = factory.getPopover();
        loanRequestListController = (LoanRequestListController)factory.getPopoverController();
        
		TextFieldHelper.makeTextFieldNumeric(ventureCapitalTextField);
		TextFieldHelper.makeTextFieldNumeric(loanAmountTextField);
		TextFieldHelper.makeTextFieldNumeric(stocksTextField);
		TextFieldHelper.makeTextFieldNumeric(realEstateTextField);

		//load loan data
        if(controller.getLoan() != null){
            this.addLoan(controller.getLoan());
        }

        loanRequestButton.setOnAction(e -> {
            try {
                this.loanAmount = Double.parseDouble(loanAmountTextField.getText());
                loanRequestListController.setLoanAmount(loanAmount);
                this.loanRequestPopover.show(loanRequestButton);
                // this.loanAmount = NULL;
                loanAmountTextField.clear();
            } catch (NumberFormatException exception) {
                // TODO tooltip/popup
                System.out.println("Not a valid loan amount!");
            }
        });

        buyRealEstateButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(realEstateTextField.getText());
                boolean successful = controller.increaseInvestmentAmount(GameState.getInstance().getGameDate(), amount, Investment.InvestmentType.REAL_ESTATE);
                if (!successful) {
                    // TODO popup not enough cash
                }
                realEstateTextField.clear();
                // TODO auskommentierten Code entfernen
//        realEstateButton.setOnAction(e -> {
//            ArrayList<Investment> investmentSelection = controller.generateInvestmentSelection(Double.parseDouble(realEstateTextField.getText()));
//            if(investmentSelection != null){
//                Investment investment = investmentSelection.get(0);
//                controller.addInvestment(investment);
//                realEstateLabel.setText(UIManager.getLocalisedString("finance.amountLabel.realEstate") + ": " + investment.getAmount());
//
//                realEstateTextField.clear();
            } catch (NumberFormatException exception) {
                // TODO invalid input
            }
        });

        buyStocksButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(stocksTextField.getText());
                boolean successful = controller.increaseInvestmentAmount(GameState.getInstance().getGameDate(), amount, Investment.InvestmentType.STOCKS);
                if (!successful) {
                    // TODO popup not enough cash
                }
                stocksTextField.clear();
            } catch (NumberFormatException exception) {
                // TODO invalid input
            }
        });

        buyVentureCapitalButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(ventureCapitalTextField.getText());
                boolean successful = controller.increaseInvestmentAmount(GameState.getInstance().getGameDate(), amount,
                        Investment.InvestmentType.VENTURE_CAPITAL);
                if (!successful) {
                    // TODO popup not enough cash
                }
                ventureCapitalTextField.clear();
            } catch (NumberFormatException exception) {
                // TODO invalid input
            }
        });

        sellRealEstateButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(realEstateTextField.getText());
                boolean successful = controller.decreaseInvestmentAmount(GameState.getInstance().getGameDate(), amount, Investment.InvestmentType.REAL_ESTATE);
                if (!successful) {
                    // TODO popup not enough cash
                }
                realEstateTextField.clear();
            } catch (NumberFormatException exception) {
                // TODO invalid input
            }

        });

        sellStocksButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(stocksTextField.getText());
                boolean successful = controller.decreaseInvestmentAmount(GameState.getInstance().getGameDate(), amount, Investment.InvestmentType.STOCKS);
                if (!successful) {
                    // TODO popup not enough cash
                }
                stocksTextField.clear();
//        stocksButton.setOnAction(e -> {
//            ArrayList<Investment> investmentSelection = controller.generateInvestmentSelection(Double.parseDouble(stocksTextField.getText()));
//            if(investmentSelection != null){
//                Investment investment = investmentSelection.get(1);
//                controller.addInvestment(investment);
//                stocksLabel.setText(UIManager.getLocalisedString("finance.amountLabel.stocks") + ": " + investment.getAmount());
//                stocksTextField.clear();
//                //TODO update GUI
//                //controller.calculateNetWorth(GameState.getInstance().getGameDate());
//            }else{
//                //TODO popup
//
//                stocksTextField.clear();
            } catch (NumberFormatException exception) {
                // TODO invalid input
            }

        });

        sellVentureCapitalButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(ventureCapitalTextField.getText());
                boolean successful = controller.decreaseInvestmentAmount(GameState.getInstance().getGameDate(), amount,
                        Investment.InvestmentType.VENTURE_CAPITAL);
                if (!successful) {
                    // TODO popup not enough cash
                }
                ventureCapitalTextField.clear();

//        ventureCapitalButton.setOnAction(e -> {
//            ArrayList<Investment> investmentSelection = controller.generateInvestmentSelection(Double.parseDouble(ventureCapitalTextField.getText()));
//            if(investmentSelection != null){
//                Investment investment = investmentSelection.get(2);
//                controller.addInvestment(investment);
//                ventureCapitalLabel.setText(UIManager.getLocalisedString("finance.amountLabel.ventureCapital") + ": " + investment.getAmount());
//                ventureCapitalTextField.clear();
//                //TODO update GUI
//                //controller.calculateNetWorth(GameState.getInstance().getGameDate());
//            }else{
//                //TODO popup
//
//                ventureCapitalTextField.clear();
            } catch (NumberFormatException exception) {
                // TODO invalid input
            }

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

    public double getLoanAmount() {
        return this.loanAmount;
    }

    /**
     * Updates the banking system UI when a new loan is added.
     * @param loan The new loan.
     */
    public void addLoan(BankingSystem.Loan loan) {
        Platform.runLater(new Runnable() {
            public void run() {

                loanLabel.setText("" + DecimalRound.round(loan.getLoanAmount(), 2)
                        + UIManager.getLocalisedString("finance.loanLabel.currenyUnit") + ": " + loan.getDuration()
                        + " " + UIManager.getLocalisedString("finance.loanLabel.durationUnit"));

                loanAmountTextField.clear();
                loanRequestPopover.hide();
            }
        });
    }

    /**
     * Updates the banking system UI when a loan is removed.
     */
    public void removeLoan(){
        Platform.runLater(new Runnable() {
            public void run() {

                loanLabel.setText("0.00");
                loanAmountTextField.clear();
            }
        });
    }
}
