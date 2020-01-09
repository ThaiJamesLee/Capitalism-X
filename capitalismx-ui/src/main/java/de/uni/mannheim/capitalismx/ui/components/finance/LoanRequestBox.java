package de.uni.mannheim.capitalismx.ui.components.finance;

import java.io.IOException;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem.Loan;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceOverviewController;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class LoanRequestBox {

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

	private Parent root;

	public Parent getRoot() {
		return root;
	}

	public LoanRequestBox(Loan loan) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/components/loan_request_list_cell.fxml"),
				UIManager.getResourceBundle());
		loader.setController(this);

		root = loader.load();
		
		GameController gameContr = GameController.getInstance();
        nameLabel.setText(loan.getName());
        interestRateLabel.setText(UIManager.getLocalisedString("finance.loan.box.interest") + loan.getInterestRate()); //TODO format numbers
        durationLabel.setText(UIManager.getLocalisedString("finance.loan.box.duration").replace("XXX", loan.getDuration() + ""));
        loanAmountLabel.setText(UIManager.getLocalisedString("finance.loan.box.amount") + CapCoinFormatter.getCapCoins(loan.getLoanAmount()));
        gridPane.setOnMouseClicked(e -> {
        	//TODO can this fail?
            gameContr.addLoan(loan, GameState.getInstance().getGameDate());
            FinanceOverviewController financeOverviewController = (FinanceOverviewController) UIManager.getInstance().getGameView(GameViewType.FINANCES).getModule(UIElementType.FINANCE_OVERVIEW).getController();
            financeOverviewController.addLoan(loan);
        });
	}

}
