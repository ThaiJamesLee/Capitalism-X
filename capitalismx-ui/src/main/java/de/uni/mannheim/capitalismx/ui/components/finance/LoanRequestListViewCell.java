package de.uni.mannheim.capitalismx.ui.components.finance;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.application.CapXApplication;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceOverviewController;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.TruckFleetController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class LoanRequestListViewCell extends ListCell<BankingSystem.Loan> {

    @FXML
    private Label nameLabel;

    @FXML
    private Label interestRateLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Label loanAmountLabel;

    @FXML
    GridPane gridPane;

    @FXML
    private Button raiseButton;

    private FXMLLoader loader;

    private ListView<BankingSystem.Loan> loanRequestListView;

    public LoanRequestListViewCell(ListView<BankingSystem.Loan> loanRequestListView){
        this.loanRequestListView = loanRequestListView;
    }

    @Override
    protected void updateItem(BankingSystem.Loan loan, boolean empty) {
        super.updateItem(loan, empty);
        if(empty || loan == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/loan_request_list_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            GameController controller = GameController.getInstance();
            nameLabel.setText(loan.getName());
            interestRateLabel.setText("Interest Rate: " + loan.getInterestRate());
            durationLabel.setText("Duration: " + loan.getDuration() + " Months");
            loanAmountLabel.setText("Loan Amount: " + loan.getLoanAmount());
            raiseButton.setOnAction(e -> {
                controller.addLoan(loan, GameState.getInstance().getGameDate());
                FinanceOverviewController financeOverviewController = (FinanceOverviewController) UIManager.getInstance().getGameView(GameViewType.FINANCES).getModule(UIElementType.FINANCE_OVERVIEW).getController();
                financeOverviewController.addLoan(loan);
                UIManager.getInstance().getGamePageController().resetOverlay();
            });

            setText(null);
            setGraphic(gridPane);
        }
    }

}