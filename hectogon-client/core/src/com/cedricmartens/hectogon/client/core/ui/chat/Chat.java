package com.cedricmartens.hectogon.client.core.ui.chat;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.cedricmartens.commons.chat.Message;

public class Chat extends ScrollPane
{
    private Table messageTable;
    private ChatStyle chatStyle;
    private boolean isOpen;
    private ImageButton toggleWindow;
    private boolean finishInit = false;

    public Chat(Skin skin)
    {
        super(new Table(skin), skin);
        this.chatStyle = skin.get(ChatStyle.class);
        this.messageTable = (Table) getActor();
        this.setScrollingDisabled(true, false);
        this.isOpen = true;

        this.toggleWindow = new ImageButton(chatStyle.closeChat, chatStyle.closeChatDown);
        this.toggleWindow.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleChatBox();
            }
        });

        finishInit = true;
    }

    public void toggleChatBox()
    {
        isOpen = !isOpen;
        sizeChanged();
        if(isOpen)
        {
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.up = chatStyle.closeChat;
            style.over = chatStyle.closeChatDown;
            toggleWindow.setStyle(style);
        }else{
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            style.up = chatStyle.openChat;
            style.over = chatStyle.openChatDown;
            toggleWindow.setStyle(style);
        }
    }

    @Override
    public float getHeight() {
        if(!isOpen)
            return 0;
        return super.getHeight();
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        if(finishInit)
            updateUI();
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        if(finishInit)
            updateUI();
    }

    private void updateUI()
    {
        this.toggleWindow.setX(getWidth() + getX() - 8);
        this.toggleWindow.setY(getHeight() + getY() - 8);
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        stage.addActor(toggleWindow);
    }

    public void addMessage(Message message)
    {
        this.messageTable.add(message.toString()).width(getWidth());
        this.messageTable.row();
        this.layout();
        this.scrollTo(0, 0, 0, 0);
    }

    static public class ChatStyle extends ScrollPane.ScrollPaneStyle
    {
        public Drawable openChat;
        public Drawable openChatDown;

        public Drawable closeChat;
        public Drawable closeChatDown;

        public ChatStyle(ChatStyle chatStyle)
        {
            this.openChat = chatStyle.openChat;
            this.openChatDown = chatStyle.openChatDown;

            this.closeChat = chatStyle.closeChat;
            this.closeChatDown = chatStyle.closeChatDown;
        }

        public ChatStyle()
        {

        }
    }
}

