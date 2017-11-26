package com.cedricmartens.commons.chat;

import com.cedricmartens.commons.User;

public class Message
{
    private User sender;
    private String contents;
    private ChatType chatType;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    @Override
    public String toString() {
        return sender.getUsername() + ": " + contents;
    }
}
