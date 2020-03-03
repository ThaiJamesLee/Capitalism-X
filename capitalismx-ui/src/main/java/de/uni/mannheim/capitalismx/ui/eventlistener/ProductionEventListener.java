package de.uni.mannheim.capitalismx.ui.eventlistener;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.component.GameViewType;
import de.uni.mannheim.capitalismx.ui.controller.module.production.ProduceProductController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProductionEventListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("launchedProductsChange")) {
            ((ProduceProductController)UIManager.getInstance().getModule(GameModuleType.PRODUCTION_PRODUCE_PRODUCT).getController()).update();
        }
    }
}
