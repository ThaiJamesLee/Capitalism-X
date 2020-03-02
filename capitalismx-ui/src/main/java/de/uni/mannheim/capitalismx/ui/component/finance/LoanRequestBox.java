package de.uni.mannheim.capitalismx.ui.component.finance;

import java.io.IOException;
import java.text.NumberFormat;

import de.uni.mannheim.capitalismx.finance.finance.Loan;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceBankingSystemController;
import de.uni.mannheim.capitalismx.ui.eventlistener.FinanceEventListener;
import de.uni.mannheim.capitalismx.ui.util.CapCoinFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Custom box that displays information about the possible {@link Loan}s, a
 * player can request.
 */
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

	/**
	 * Constructor that creates a new box, displaying information about the given
	 * {@link Loan}.
	 * 
	 * @param loan The {@link Loan} to create a new {@link LoanRequestBox} for.
	 * @throws IOException If the fxml could not be loaded.
	 */
	public LoanRequestBox(Loan loan) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/component/loan_request_list_cell.fxml"),
				UIManager.getResourceBundle());
		loader.setController(this);

		root = loader.load();

		GameController gameContr = GameController.getInstance();
		nameLabel.setText(loan.getName());
		interestRateLabel.setText(UIManager.getLocalisedString("finance.loan.box.interest") + NumberFormat
				.getPercentInstance(UIManager.getResourceBundle().getLocale()).format(loan.getInterestRate()));
		durationLabel.setText(
				UIManager.getLocalisedString("finance.loan.box.duration").replace("XXX", loan.getDuration() + ""));
		loanAmountLabel.setText(UIManager.getLocalisedString("finance.loan.box.amount")
				+ CapCoinFormatter.getCapCoins(loan.getLoanAmount()));
		gridPane.setOnMouseClicked(e -> {
			// TODO can this fail?
			loan.registerPropertyChangeListener(new FinanceEventListener(loan));
			gameContr.addLoan(loan, GameState.getInstance().getGameDate());
			FinanceBankingSystemController financeBankingSystemController = (FinanceBankingSystemController) UIManager
					.getInstance().getModule(GameModuleType.FINANCE_BANKING_SYSTEM).getController();
			financeBankingSystemController.addLoan(loan);
		});
	}

}
