package de.uni.mannheim.capitalismx.ui.controller;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.utils.MessageObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MessageContentController {

    @FXML
    private Label contentSender;
    @FXML
    private Label contentDate;
    @FXML
    private Label contentSubject;
    @FXML
    private Label contentContent;
    @FXML
    private VBox contentVBox;

    public void setContentSender(String sender){ contentSender.setText(sender); }
    public void setContentDate(String date){
        contentDate.setText(date);
    }
    public void setContentSubject(String subject){
        contentSubject.setText(subject);
    }
    public void setContentContent(String content){
        contentContent.setText(content);
    }

    public void addJumpButton(int jumpToView){
        GameViewType viewID = GameViewType.getTypeById(jumpToView);
        Button btn = new Button("Jump to Menu");
        btn.setOnAction(action -> {
            UIManager.getInstance().getGamePageController().switchView(viewID);
            UIManager.getInstance().getGamePageController().toggleMessageWindow();
        });
        contentVBox.getChildren().add(btn);

    }
}
