package de.uni.mannheim.capitalismx.ui.eventlisteners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FinanceEventListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("netWorth")) {
           double newVal = (Double) evt.getSource();
           //TODO do something
        }
    }
}
