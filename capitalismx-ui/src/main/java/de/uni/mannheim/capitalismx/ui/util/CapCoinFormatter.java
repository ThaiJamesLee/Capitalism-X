package de.uni.mannheim.capitalismx.ui.util;

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

	/**
	 * Get the given amount of CapCoins as a formatted Currency-String.
	 * 
	 * @param amount The amount of CapCoins to format.
	 * @return Formatted Currency-{@link String}.
	 */
	public static String getCapCoins(int amount) {
		return NumberFormat.getIntegerInstance(UIManager.getResourceBundle().getLocale()).format(amount)
				+ CURRENCY_SYMBOL;
	}

	/**
	 * Get the given amount of CapCoins as a formatted Currency-String.
	 * 
	 * @param amount The amount of CapCoins to format.
	 * @return Formatted Currency-{@link String}.
	 */
	public static String getCapCoins(double amount) {
		return NumberFormat.getIntegerInstance(UIManager.getResourceBundle().getLocale()).format(amount)
				+ CURRENCY_SYMBOL;
	}

}
