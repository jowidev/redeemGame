package com.MenuScreens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Constants;

public class Grid extends Table {
    public Grid() {
        setFillParent(true);


        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                add()
                        .width(Gdx.graphics.getHeight()*(Constants.pixeltotile*4.25f))
                        .height(Gdx.graphics.getWidth()*(Constants.pixeltotile*4));
            }
            row();
        }

        setPosition(0, 0);
        setDebug(true);
    }

}