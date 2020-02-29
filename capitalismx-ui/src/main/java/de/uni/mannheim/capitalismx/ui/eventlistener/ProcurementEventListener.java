package de.uni.mannheim.capitalismx.ui.eventlistener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProcurementEventListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("componentOrdersChange")) {
//            UIManager.getInstance().getGameView(GameViewType.WAREHOUSE).getModule(GameModuleType.PROCUREMENT_ORDERED_COMPONENTS_OVERVIEW).getController().update();
        }
    }
}
