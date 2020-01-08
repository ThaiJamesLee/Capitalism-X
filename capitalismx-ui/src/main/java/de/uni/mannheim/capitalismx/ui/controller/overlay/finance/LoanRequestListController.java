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
    
    private double loanAmount;

    @Override
    public void update() {
    	loanRequestListView.getItems().clear();
        GameController controller = GameController.getInstance();

        ArrayList<BankingSystem.Loan> loans = controller.generateLoanSelection(loanAmount);

        for(BankingSystem.Loan loan : loans) {
            loanRequestListView.getItems().add(loan);
        }
    }
    
    public void setLoanAmount(double amount) {
    	this.loanAmount = amount;
    	update();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loanRequestListView.setCellFactory(loanRequestListView -> new LoanRequestListViewCell(loanRequestListView));
    
        update();
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