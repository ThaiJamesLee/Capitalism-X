package de.uni.mannheim.capitalismx.procurement.component;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

public enum SupplierCategory implements Serializable {
    PREMIUM("Premium Supplier"),
    REGULAR("Regular Supplier"),
    CHEAP("Cheap Supplier");

    private String category;

    SupplierCategory(String category) {
        this.category = category;
    }

    public String toString() {
        return this.category;
    }
    
//    public void translate(ResourceBundle bundle) {
//    	this.category= bundle.getString(this.name().toLowerCase());
//    }
    
    /**
     * Reads the properties file.
     * @param locale the locale of the properties file (English or German).
     * @return Returns the name of the employee type.
     */
    public String getName(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("procurement", locale);
        return bundle.getString(this.name().toLowerCase());
    }
}