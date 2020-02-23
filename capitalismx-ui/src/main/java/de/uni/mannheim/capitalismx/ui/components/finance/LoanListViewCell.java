package de.uni.mannheim.capitalismx.ui.components.finance;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.text.NumberFormat;

/**
 * This class represents an entry in the list of loans in the finance UI. It displays information like the duration
 * of a specific loan of the company.
 *
 * @author sdupper
 */
public class LoanListViewCell extends ListCell<BankingSystem.Loan> {

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

    /**
     * The ListView of all loans in the finance department.
     */
    private ListView<BankingSystem.Loan> loanListView;

    /**
     * Constructor
     * @param loanListView The ListView of all trucks in the internal logistics fleet.
     */
    public LoanListViewCell(ListView<BankingSystem.Loan> loanListView){
        this.loanListView = loanListView;
    }

    /*
     * Generates an entry in the list of loans in the finance deparment for each new loan added to the loanListView
     * according to the loans's characteristics.
     */
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

            gridPane.getStyleClass().clear();

            nameLabel.setText(loan.getName());
            interestRateLabel.setText(UIManager.getLocalisedString("finance.loan.box.interest") + NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale()).format(loan.getInterestRate()));
            durationLabel.setText(UIManager.getLocalisedString("finance.loan.box.duration").replace("XXX", loan.getDuration() + ""));
            loanAmountLabel.setText(UIManager.getLocalisedString("finance.loan.box.amount") + CapCoinFormatter.getCapCoins(loan.getLoanAmount()));

            setText(null);
            setGraphic(gridPane);
        }
    }

}