package de.uni.mannheim.capitalismx.ui.controller.overlay.finance;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.components.finance.LoanRequestListViewCell;
import de.uni.mannheim.capitalismx.ui.components.logistics.TruckDetailListViewCell;
import de.uni.mannheim.capitalismx.ui.components.logistics.TruckListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceOverviewController;
import de.uni.mannheim.capitalismx.ui.controller.overlay.GameOverlayController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

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