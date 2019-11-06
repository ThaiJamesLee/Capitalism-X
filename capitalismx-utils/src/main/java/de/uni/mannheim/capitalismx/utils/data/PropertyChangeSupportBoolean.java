package de.uni.mannheim.capitalismx.utils.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class PropertyChangeSupportBoolean implements Serializable {

    private boolean value;

    private String propertyChangedName;


    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public PropertyChangeSupportBoolean() {}

    /**
     * The change listener will receive the fired events.
     * @param listener Add a PropertyChangeListener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void setValue(boolean value) {
        boolean oldValue = this.value;
        this.value = value;
        propertyChangeSupport.firePropertyChange(propertyChangedName, oldValue, this.value);
    }

    public boolean getValue() {
        return value;
    }

    public String getPropertyChangedName() {
        return propertyChangedName;
    }

    public void setPropertyChangedName(String propertyChangedName) {
        this.propertyChangedName = propertyChangedName;
    }

}
