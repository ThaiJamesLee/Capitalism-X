package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.finance.finance.Finance;
import de.uni.mannheim.capitalismx.finance.finance.Investment;
import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.FinanceEventListener;
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
    MenuButton loanRequestMenuButton;

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

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        financeEventListener = new FinanceEventListener();

        Finance f = Finance.getInstance();
        f.addPropertyChangeListener(financeEventListener);

        cashLabel.setText(controller.getCash() + "");
        assetsLabel.setText(controller.getAssets() + "");
        liabilitiesLabel.setText(controller.getLiabilities() + "");
        netWorthLabel.setText(controller.getNetWorth() + "");

        loanRequestMenuButton.setOnMouseClicked(e -> {
            loanRequestMenuButton.getItems().removeAll(loanRequestMenuButton.getItems());
            ObservableList<BankingSystem.Loan> loanListObservable = FXCollections.observableArrayList();
            try {
                double loanAmount = Double.parseDouble(loanAmountTextField.getText());
                ArrayList<BankingSystem.Loan> loans = controller.generateLoanSelection(loanAmount);
                for(BankingSystem.Loan loan : loans){
                    loanListObservable.add(loan);
                }

                MenuItem[] loanMenuItems = new MenuItem[loans.size()];

                int i = 0;
                for(BankingSystem.Loan loan : loans) {
                    loanMenuItems[i] = new MenuItem("Loan " + i + " Interest Rate: " + loan.getInterestRate() +
                            " Duration: " + loan.getDuration());
                    loanMenuItems[i].setOnAction(e2 -> {
                        controller.addLoan(loan, GameState.getInstance().getGameDate());
                        //loanLabel.setText("Interest Rate: " + controller.getLoan().getInterestRate() + " Duration: " +
                         //       controller.getLoan().getDuration());
                        loanLabel.setText("" + controller.getLoan().getLoanAmount() + "CC - Duration: " +
                               controller.getLoan().getDuration() + " Months");
                        loanAmountTextField.clear();
                    });
                    i++;
                }

                loanRequestMenuButton.getItems().addAll(loanMenuItems);
            }catch (NumberFormatException exception){
                //TODO tooltip/popup
                System.out.println("Not a number");
            }
        });

        realEstateButton.setOnAction(e -> {
            Investment investment = controller.generateInvestmentSelection(Double.parseDouble(realEstateTextField.getText())).get(0);
            controller.addInvestment(investment);
            realEstateLabel.setText("Amount: " + investment.getAmount());
            realEstateTextField.clear();
            //TODO update GUI
            controller.calculateNetWorth(GameState.getInstance().getGameDate());
        });

        stocksButton.setOnAction(e -> {
            Investment investment = controller.generateInvestmentSelection(Double.parseDouble(stocksTextField.getText())).get(1);
            controller.addInvestment(investment);
            stocksLabel.setText("Amount: " + investment.getAmount());
            stocksTextField.clear();
            //TODO update GUI
            controller.calculateNetWorth(GameState.getInstance().getGameDate());
        });

        ventureCapitalButton.setOnAction(e -> {
            Investment investment = controller.generateInvestmentSelection(Double.parseDouble(ventureCapitalTextField.getText())).get(2);
            controller.addInvestment(investment);
            ventureCapitalLabel.setText("Amount: " + investment.getAmount());
            ventureCapitalTextField.clear();
            //TODO update GUI
            controller.calculateNetWorth(GameState.getInstance().getGameDate());
        });
    }

    public void setNetWorthLabel(String text){
        this.netWorthLabel.setText(text);
    }
}
