package de.uni.mannheim.capitalismx.ui.controller.module.finance;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import javafx.scene.control.*;
import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.finance.finance.FinanceDepartment;
import de.uni.mannheim.capitalismx.finance.finance.Investment;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.finance.LoanListViewCell;
import de.uni.mannheim.capitalismx.ui.component.general.GameAlert;
import de.uni.mannheim.capitalismx.ui.component.logistics.TruckListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.popover.finance.LoanRequestListController;
import de.uni.mannheim.capitalismx.ui.eventlistener.FinanceEventListener;
import de.uni.mannheim.capitalismx.ui.util.PopOverFactory;
import de.uni.mannheim.capitalismx.ui.util.TextFieldHelper;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
    ListView<BankingSystem.Loan> loanListView;

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
        for(BankingSystem.Loan loan : controller.getLoans()){
            this.loanListView.getItems().add(loan);
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

        loanListView.setCellFactory(listView -> new LoanListViewCell(loanListView));
        loanListView.setPlaceholder(new Label(UIManager.getLocalisedString("list.placeholder.loan")));
    }

    public double getLoanAmount() {
        return this.loanAmount;
    }

    /**
     * Updates the banking system UI when a new loan is added.
     * @param loan The new loan.
     */
    public void addLoan(BankingSystem.Loan loan) {
        this.loanListView.getItems().add(loan);
        loanRequestPopover.hide();
    }

    /**
     * Updates the banking system UI when a loan is removed.
     */
    public void removeLoan(){
        Platform.runLater(new Runnable() {
            public void run() {
                loanListView.getItems().clear();
                for(BankingSystem.Loan loan : GameController.getInstance().getLoans()){
                    loanListView.getItems().add(loan);
                }
            }
        });
    }

    /**
     * Updates information like remaining duration and loan amount of the specified loan.
     * @param loan The loan to be updated.
     */
    public void updateLoan(BankingSystem.Loan loan){
        Platform.runLater(new Runnable() {
            public void run() {
                int index = loanListView.getItems().indexOf(loan);
                loanListView.getItems().remove(loan);
                loanListView.getItems().add(index, loan);
            }
        });
    }
}
