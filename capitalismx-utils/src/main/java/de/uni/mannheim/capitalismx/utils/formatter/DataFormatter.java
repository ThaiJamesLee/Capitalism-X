package de.uni.mannheim.capitalismx.utils.formatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class handles formatting and casting of data formats.
 * @author duly
 *
 * @since 1.0.0
 */
public class DataFormatter {

    /**
     * Number of doubles must match the number of keys.
     * @param value The string that contains comma separated doubles.
     * @param keys the list of keys for the map.
     * @return Returns the map.
     */
    public static Map<String, Double> stringToStringDoubleMap(List<String> keys, String value) {
        String[] values = value.split(",");
        Double[] dArray = stringArrayToDoubleArray(values);

        if(keys.size() != dArray.length) {
            throw new NumberFormatException("Size of keys must match number of doubles!");
        } else {
            Map<String, Double> map = new HashMap<>();
            for(int i=0; i<dArray.length; i++) {
                map.put(keys.get(i), dArray[i]);
            }

            return map;
        }
    }

    public static Double[] stringArrayToDoubleArray(String[] array) {
        Double[] dArray = new Double[array.length];

        for(int i = 0; i < array.length; i++) {
            dArray[i] = Double.parseDouble(array[i]);
        }
        return dArray;
    }
}
