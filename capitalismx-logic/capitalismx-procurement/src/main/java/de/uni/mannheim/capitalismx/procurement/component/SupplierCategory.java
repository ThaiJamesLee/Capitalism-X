package de.uni.mannheim.capitalismx.procurement.component;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The enum Supplier category.
 *
 * @author dzhao
 */
public enum SupplierCategory implements Serializable {

    PREMIUM("Premium Supplier"),
    REGULAR("Regular Supplier"),
    CHEAP("Cheap Supplier");

    private String category;

    /**
     * Instantiates a supplier category.
     *
     * @param category
     */
    SupplierCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return this.category;
    }

    /**
     * Reads the properties file.
     *
     * @param locale the locale of the properties file (English or German).
     * @return Returns the name of the employee type.
     */
    public String getName(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("procurement", locale);
        return bundle.getString(this.name().toLowerCase());
    }
}