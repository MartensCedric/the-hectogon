package com.cedricmartens.hectogon.client.core.graphics.ui.chat;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.cedricmartens.hectogon.client.core.game.player.InputService;
import com.cedricmartens.hectogon.client.core.util.ServiceUtil;

public class ChatInput extends TextField
{
    private OnSend onSendAction = null;
    private OnFocusChange onFocusChange = null;

    public ChatInput(String text, Skin skin) {
        this(text, skin.get("default", TextFieldStyle.class));
    }

    public ChatInput(String text, final TextFieldStyle style) {
        super(text, style);
        addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused)
            {
                if(onFocusChange != null)
                    onFocusChange.focus(focused);
                super.keyboardFocusChanged(event, actor, focused);
            }
        });
    }

    @Override
    protected InputListener createInputListener() {
        return new ChatListener();
    }

    protected class ChatListener extends TextFieldClickListener
    {
        public ChatListener()
        {
            super();
        }
        @Override
        public boolean keyTyped(InputEvent event, char character) {
            InputService inputService = ServiceUtil.getServiceUtil().getInputService();
            if(inputService.toggleChatInput(character))
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

            super.keyTyped(event, character);
            return true;
        }
    }

    public void setOnSendAction(OnSend onSend)
    {
        this.onSendAction = onSend;
    }
    public void setOnFocusChange(OnFocusChange onFocusChange){ this.onFocusChange = onFocusChange; }
}
