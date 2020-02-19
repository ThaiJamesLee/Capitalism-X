package de.uni.mannheim.capitalismx.ui.eventlisteners;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.GameModuleType;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProductionEventListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("launchedProductsChange")) {
            UIManager.getInstance().getGameView(GameViewType.PRODUCTION).getModule(GameModuleType.PRODUCTION_PRODUCE_PRODUCT).getController().update();
        }
    }
}
