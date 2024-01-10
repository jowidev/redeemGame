package com.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.Constants;
import com.mygdx.game.TDGame;

public class HUD extends Table {
    private final Table timerTable;

    public HUD(TDGame game) {
        Texture signImg = game.assets.timerBg;
        Image timerBg = new Image(signImg);

        timerTable = new Table();
        //timerTable.setDebug(true);

        timerTable.center().top().
                padTop(16).setPosition((float) Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());

        timerTable.add(timerBg).
                width(Gdx.graphics.getWidth() * (Constants.pixeltotile * 2))
                .height(Gdx.graphics.getHeight() * (Constants.pixeltotile * 2));

    }

    public Table getTimerTable() {
        return timerTable;
    }
}

