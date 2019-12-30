package de.uni.mannheim.capitalismx.ui.utils;

public class CapCoinFormatter {

	public static final String CURRENCY_SYMBOL = "CC";
	
	public String getCapCoins(int amount) {
		return amount + CURRENCY_SYMBOL;
	}
	
	public String getCapCoins(double amount) {
		return String.format("%.2f", amount)+ CURRENCY_SYMBOL;
	}
	
}
