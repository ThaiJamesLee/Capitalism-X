package de.uni.mannheim.capitalismx.ui.controller.overlay.finance;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.ui.components.finance.LoanRequestBox;
import de.uni.mannheim.capitalismx.ui.controller.overlay.GameOverlayController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class LoanRequestListController extends GameOverlayController {

    @FXML
    ListView<BankingSystem.Loan> loanRequestListView;
    
    @FXML
    private VBox loanVBox;
    
    private double loanAmount;

    @Override
    public void update() {
    	loanVBox.getChildren().clear();
        GameController controller = GameController.getInstance();

        ArrayList<BankingSystem.Loan> loans = controller.generateLoanSelection(loanAmount);

        for(BankingSystem.Loan loan : loans) {
        	LoanRequestBox loanRequestBox;
			try {
				loanRequestBox = new LoanRequestBox(loan);
				loanVBox.getChildren().add(loanRequestBox.getRoot());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    public void setLoanAmount(double amount) {
    	this.loanAmount = amount;
    	update();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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