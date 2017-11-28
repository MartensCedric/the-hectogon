package com.cedricmartens.hectogon.client.core.ui.chat;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.cedricmartens.commons.chat.Message;

public class Chat extends ScrollPane
{
    private Table messageTable;
    private ChatStyle chatStyle;

    public Chat(Skin skin)
    {
        super(new Table(skin), skin);
        this.chatStyle = skin.get(ChatStyle.class);
        this.messageTable = (Table) getActor();
        this.setScrollingDisabled(true, false);
    }

    public void addMessage(Message message)
    {
        this.messageTable.add(message.toString()).width(getWidth());
        this.messageTable.row();
        this.layout();
        this.scrollTo(0, 0, 0, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //this.chatStyle.openChat.draw(batch, 0,0, 8,8);
    }

    static public class ChatStyle extends ScrollPane.ScrollPaneStyle
    {
        public Drawable openChat;
        public Drawable closeChat;

        public ChatStyle(Drawable openChat, Drawable closeChat, ScrollPaneStyle scrollPaneStyle)
        {
            super(scrollPaneStyle);
            this.openChat = openChat;
            this.closeChat = closeChat;
        }

        public ChatStyle(ChatStyle chatStyle)
        {
            this.openChat = chatStyle.openChat;
            this.closeChat = chatStyle.closeChat;
        }

        public ChatStyle()
        {

        }
    }
}

