package de.uni.mannheim.capitalismx.ui.eventlisteners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

/**
 * Ein GameState Event Listener.
 * @author duly
 */
public class GameStateEventListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("gameDate")) {
            //TODO do something with the changed value.
            LocalDate newDate = ((LocalDate)evt.getSource());

        }
    }
}
