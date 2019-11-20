package de.uni.mannheim.capitalismx.ui.eventlisteners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.uni.mannheim.capitalismx.ui.application.UIManager;

/**
 * Ein GameState Event Listener.
 * @author duly
 */
public class GameStateEventListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("gameDate")) {
            //TODO do something with the changed value.
        	UIManager.getInstance().getGameHudController().update();
        }
    }
}
