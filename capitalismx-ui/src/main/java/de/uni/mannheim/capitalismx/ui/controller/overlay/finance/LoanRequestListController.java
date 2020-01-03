package de.uni.mannheim.capitalismx.ui.controller.overlay.finance;

import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.components.finance.LoanRequestListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceOverviewController;
import de.uni.mannheim.capitalismx.ui.controller.overlay.GameOverlayController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class LoanRequestListController extends GameOverlayController {

    @FXML
    ListView<BankingSystem.Loan> loanRequestListView;

    @Override
    public void update() {
        loanRequestListView.getItems().clear();
        GameController controller = GameController.getInstance();
        //loanRequestListView.setCellFactory(loanRequestListView -> new LoanRequestListViewCell(loanRequestListView));
        FinanceOverviewController financeOverviewController = (FinanceOverviewController) UIManager.getInstance().getGameView(GameViewType.FINANCES).getModule(UIElementType.FINANCE_OVERVIEW).getController();
        double loanAmount = financeOverviewController.getLoanAmount();

        ArrayList<BankingSystem.Loan> loans = controller.generateLoanSelection(loanAmount);

        for(BankingSystem.Loan loan : loans) {
            //controller.addTruckToFleet(truck, GameState.getInstance().getGameDate());
            loanRequestListView.getItems().add(loan);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loanRequestListView.setCellFactory(loanRequestListView -> new LoanRequestListViewCell(loanRequestListView));
    }

    @Override
    public Properties getProperties() {
        return this.properties;
    }

    @Override
    public void updateProperties(Properties properties) {
        this.properties = properties;
    }

}