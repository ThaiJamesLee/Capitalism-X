package de.uni.mannheim.capitalismx.ui.utils;

import java.text.NumberFormat;

public class CapCoinFormatter {

	public static final String CURRENCY_SYMBOL = " CC";

	public static String getCapCoins(int amount) {
		return NumberFormat.getIntegerInstance().format(amount) + CURRENCY_SYMBOL;
	}

	public static String getCapCoinsWithCents(double amount) {
		return String.format("%.2f", NumberFormat.getIntegerInstance().format(amount)) + CURRENCY_SYMBOL;
	}

	public static String getCapCoins(double amount) {
		return NumberFormat.getIntegerInstance().format(amount) + CURRENCY_SYMBOL;
	}
	
}
