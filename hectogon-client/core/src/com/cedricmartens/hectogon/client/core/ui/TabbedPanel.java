package com.cedricmartens.hectogon.client.core.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TabbedPanel extends Table
{
    private String[] tabNames;
    private Actor[] tabContents;

    private int selectedTab;

    public TabbedPanel(String[] tabNames, Actor[] tabContents, Skin skin)
    {
        super(skin);
        this.tabNames = tabNames;
        this.tabContents = tabContents;
        this.selectedTab = 0;
        build();
    }

    public void setSelectedTabIndex(int index)
    {
        if(index < 0 || index >= tabNames.length)
            throw new IllegalArgumentException();

        selectedTab = index;
        build();
    }

    private void build()
    {
        clearChildren();
        for(int i = 0; i < tabNames.length; i++)
        {
            TextButton textButton = new TextButton(tabNames[i], getSkin());
            if(i == selectedTab)
            {
                textButton.setDisabled(true);
                textButton.setChecked(true);
            }

            final int finalI = i;
            textButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setSelectedTabIndex(finalI);
                    build();
                }
            });
            add(textButton).width(300);
        }
        row();
        add(tabContents[selectedTab]).colspan(tabContents.length);
    }
}
