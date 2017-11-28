package com.cedricmartens.hectogon.client.core.ui.chat;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.cedricmartens.commons.chat.Message;

import java.util.ArrayList;
import java.util.List;

public class Chat extends ScrollPane
{
    private List<Message> messageList;
    private Table messageTable;

    public Chat(Skin skin)
    {
        super(new Table(skin), skin);
        this.messageTable = (Table) getActor();
        this.messageList = new ArrayList<Message>();
        this.setScrollingDisabled(true, false);
    }

    public void addMessage(Message message)
    {
        this.messageList.add(message);
        this.messageTable.add(message.toString()).width(getWidth());
        this.messageTable.row();
        this.layout();
        this.scrollTo(0, 0, 0, 0);
    }
}
