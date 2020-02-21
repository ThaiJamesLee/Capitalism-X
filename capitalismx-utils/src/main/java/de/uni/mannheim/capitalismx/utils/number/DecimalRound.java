package de.uni.mannheim.capitalismx.utils.number;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Rounds decimal numbers.
 */
public class DecimalRound {

    //https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places

    /**
     * Rounds decimal numbers.
     * @param value The value to round.
     * @param places The desired number of decimal places.
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
