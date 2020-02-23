package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.components.general.GameAlert;
import javafx.scene.control.Alert;
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
 * This class represents the banking system in the finance UI. Users have the option to request loans with different
 * characteristics, e.g., duration.
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
     * Handles loan requests within the respective action listener.
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

        TextFieldHelper.makeTextFieldNumeric(loanAmountTextField);

		//load loan data
        //TODO
        for(BankingSystem.Loan loan : controller.getLoans().values()){
            this.addLoan(loan);
        }

        loanRequestButton.setOnAction(e -> {
            try {
                this.loanAmount = Double.parseDouble(loanAmountTextField.getText());
                loanRequestListController.setLoanAmount(loanAmount);
                this.loanRequestPopover.show(loanRequestButton);
                // this.loanAmount = NULL;
                loanAmountTextField.clear();
            } catch (NumberFormatException exception) {
                GameAlert error = new GameAlert(Alert.AlertType.WARNING, UIManager.getLocalisedString("finance.error.number.title"),
                        UIManager.getLocalisedString("finance.error.number.description"));
                error.showAndWait();
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
