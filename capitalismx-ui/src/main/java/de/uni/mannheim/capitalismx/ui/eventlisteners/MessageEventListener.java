package de.uni.mannheim.capitalismx.ui.eventlisteners;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.GamePageController;
import de.uni.mannheim.capitalismx.utils.data.MessageObject;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * This class listens to message events.
 * @author duly
 */
public class MessageEventListener implements PropertyChangeListener {


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(GameState.MESSAGE_LIST_CHANGED_EVENT)) {
            PropertyChangeSupportList<MessageObject> changedList = (PropertyChangeSupportList) evt.getSource();

            MessageObject NewMessage = ((MessageObject) evt.getNewValue());
            UIManager.getInstance().getGamePageController().getMessageController().addMessage(NewMessage);
        }
        //todo: add remove message functionality?

    }
}
