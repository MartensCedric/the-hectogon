package com.cedricmartens.hectogon.client.core.ui.chat;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
public class ChatInput extends TextField
{
    private OnSend onSendAction = null;

    public ChatInput(String text, Skin skin) {
        this(text, skin.get("default", TextFieldStyle.class));
    }

    public ChatInput(String text, final TextFieldStyle style) {
        super(text, style);
    }

    @Override
    protected InputListener createInputListener() {
        return new ChatListener();
    }

    protected class ChatListener extends TextFieldClickListener
    {
        @Override
        public boolean keyTyped(InputEvent event, char character) {
            if(character == ENTER_DESKTOP)
            {
                if(ChatInput.this.onSendAction != null)
                {
                    ChatInput.this.onSendAction.send();
                }

                ChatInput.this.setText("");
                if (getStage() != null) getStage().setKeyboardFocus(null);
                getOnscreenKeyboard().show(false);
                return true;
            }

            return super.keyTyped(event, character);
        }
    }

    public void setOnSendAction(OnSend onSend)
    {
        this.onSendAction = onSend;
    }
}
