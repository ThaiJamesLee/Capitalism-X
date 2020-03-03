package de.uni.mannheim.capitalismx.ui.eventlistener;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameViewType;
import de.uni.mannheim.capitalismx.ui.controller.gamepage.GamePageController;
import de.uni.mannheim.capitalismx.utils.data.MessageObject;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class listens to message events.
 * @author duly
 */
public class MessageEventListener implements PropertyChangeListener {


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(GameState.MESSAGE_LIST_CHANGED_EVENT)) {
            PropertyChangeSupportList<MessageObject> changedList = (PropertyChangeSupportList) evt.getSource();
            CopyOnWriteArrayList<MessageObject> newMessage = ((CopyOnWriteArrayList<MessageObject>) evt.getNewValue());

            for(MessageObject m : newMessage){
                UIManager.getInstance().getGamePageController().getMessageController().addMessage(m);
            }
            //MessageObject newMessage = new MessageObject("sen.event1", "01.01.1990", "sub.event1", "con.event1", true, 2);
            /*
            System.out.println(evt.getNewValue().getClass().toString());
            if(evt.getNewValue().getClass().toString().equals("class de.uni.mannheim.capitalismx.utils.data.MessageObject")){
                newMessage = ((MessageObject) evt.getNewValue());
                UIManager.getInstance().getGamePageController().getMessageController().addMessage(newMessage);
            }else if(evt.getNewValue().getClass().toString().equals("class java.util.ArrayList")){
                List<MessageObject> newMessages = ((List<MessageObject>) evt.getNewValue());
                List<MessageObject> oldMessages = ((List<MessageObject>) evt.getOldValue());
                newMessages.removeAll(oldMessages);
                for(MessageObject message : newMessages){
                    UIManager.getInstance().getGamePageController().getMessageController().addMessage(message);
                }
            }

             */


        }
        //todo: add remove message functionality?

    }
}
