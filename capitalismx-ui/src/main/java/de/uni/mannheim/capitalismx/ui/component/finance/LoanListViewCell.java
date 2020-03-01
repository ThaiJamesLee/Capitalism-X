package de.uni.mannheim.capitalismx.ui.component.finance;

import java.io.IOException;
import java.text.NumberFormat;

import de.uni.mannheim.capitalismx.finance.finance.Loan;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.util.CapCoinFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

/**
 * This class represents an entry in the list of loans in the finance UI. It displays information like the duration
 * of a specific loan of the company.
 *
 * @author sdupper
 */
public class LoanListViewCell extends ListCell<Loan> {

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

    private FXMLLoader loader;

    public LoanListViewCell(){
    }

    /*
     * Generates an entry in the list of loans in the finance department for each new loan added to the loanListView
     * according to the loans's characteristics.
     */
    @Override
    protected void updateItem(Loan loan, boolean empty) {
        super.updateItem(loan, empty);
        if(empty || loan == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component/loan_request_list_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            gridPane.getStyleClass().clear();

            nameLabel.setText(loan.getName());
            interestRateLabel.setText(UIManager.getLocalisedString("finance.loan.box.interest")
                    + NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale()).format(loan.getInterestRate()));
            durationLabel.setText(UIManager.getLocalisedString("finance.loan.box.duration").replace("XXX",
                    loan.getRemainingDuration() + ""));
            loanAmountLabel.setText(UIManager.getLocalisedString("finance.loan.box.amount")
                    + CapCoinFormatter.getCapCoins(loan.getAnnualPrincipalBalance()));

            setText(null);
            setGraphic(gridPane);
        }
    }

}