package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.finance.finance.Finance;
import de.uni.mannheim.capitalismx.finance.finance.Investment;
import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.production.Production;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
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
                        loanLabel.setText("Interest Rate: " + controller.getLoan().getInterestRate() + " Duration: " +
                                controller.getLoan().getDuration());
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
}
