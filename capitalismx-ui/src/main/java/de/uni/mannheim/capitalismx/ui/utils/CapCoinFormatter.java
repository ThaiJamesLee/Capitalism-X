package de.uni.mannheim.capitalismx.ui.utils;

import java.text.NumberFormat;
import java.util.Locale;

import de.uni.mannheim.capitalismx.ui.application.UIManager;

/**
 * Helper class, that formats a given amount as the games currency CapCoins. Is
 * true to the {@link Locale}, that is currently set in the game.
 * 
 * @author Jonathan
 *
 */
public class CapCoinFormatter {

	public static final String CURRENCY_SYMBOL = " CC";

	public static String getCapCoins(int amount) {
		return NumberFormat.getIntegerInstance(UIManager.getResourceBundle().getLocale()).format(amount)
				+ CURRENCY_SYMBOL;
	}

	public static String getCapCoinsWithCents(double amount) { // TODO check if this works
		return String.format("%.2f",
				NumberFormat.getNumberInstance(UIManager.getResourceBundle().getLocale()).format(amount))
				+ CURRENCY_SYMBOL;
	}

	public static String getCapCoins(double amount) {
		return NumberFormat.getIntegerInstance(UIManager.getResourceBundle().getLocale()).format(amount)
				+ CURRENCY_SYMBOL;
	}

}
