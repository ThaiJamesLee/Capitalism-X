package de.uni.mannheim.capitalismx.ui.controller.popover.finance;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.ui.components.finance.LoanRequestBox;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * This class represents the loan selection in the finance UI. It displays different loans that the user can choose from.
 *
 * @author sdupper
 */
public class LoanRequestListController implements UpdateableController {

    @FXML
    ListView<BankingSystem.Loan> loanRequestListView;
    
    @FXML
    private VBox loanVBox;
    
    private double loanAmount;

    /**
     * Generates an entry in the loan selection for all available loans.
     */
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

    /**
     * Sets the desired loan amount and updates the loan selection accordingly.
     * @param amount
     */
    public void setLoanAmount(double amount) {
    	this.loanAmount = amount;
    	update();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}