package de.uni.mannheim.capitalismx.ui.eventlisteners;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProcurementEventListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("componentOrdersChange")) {
            UIManager.getInstance().getGameView(GameViewType.WAREHOUSE).getModule(UIElementType.PROCUREMENT_ORDERED_COMPONENTS_OVERVIEW).getController().update();
        }
    }
}
