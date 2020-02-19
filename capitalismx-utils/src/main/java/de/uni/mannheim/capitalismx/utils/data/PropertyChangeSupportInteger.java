package de.uni.mannheim.capitalismx.utils.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class PropertyChangeSupportInteger implements Serializable {

    private Integer value;

    private String propertyChangedName;

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public PropertyChangeSupportInteger() {}

    /**
     * The change listener will receive the fired events.
     * @param listener Add a PropertyChangeListener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void setValue(Integer value) {
        Integer oldValue = this.value;
        this.value = value;
        propertyChangeSupport.firePropertyChange(propertyChangedName, oldValue, this.value);
    }

    public Integer getValue() {
        return value;
    }

    public String getPropertyChangedName() {
        return propertyChangedName;
    }

    public void setPropertyChangedName(String propertyChangedName) {
        this.propertyChangedName = propertyChangedName;
    }
}
