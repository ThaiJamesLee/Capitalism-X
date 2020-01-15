package de.uni.mannheim.capitalismx.ui.eventlisteners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;

/**
 * 
 * @author Jonathan
 *
 */
public class WarehouseEventlistener implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("inventoryChange")) {
			UIManager.getInstance().getGameView(GameViewType.WAREHOUSE).getModule(UIElementType.WAREHOUSE_SEGMENTS).getController().update();
		}
	}

}
