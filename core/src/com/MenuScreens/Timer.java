package com.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.Assets;
import com.mygdx.game.Constants;
import com.mygdx.game.TDGame;

import static com.mygdx.game.Assets.SKIN;

public class Timer extends Table {
    private final Table timerTable;
    private final Skin skin;
    private Stack stack;
    private float time = 90;
    int showTime;
    public Timer(TDGame game) {
        Texture signImg = game.assets.timerBg;
        Image timerBg = new Image(signImg);
        skin = Assets.manager.get(SKIN);
        showTime = (int) time;
        timerTable = new Table();
        stack = new Stack();
        final Label numberLabel = new Label(String.valueOf(showTime), skin);
        numberLabel.addAction(new Action() {
            @Override
            public boolean act (float delta) {
                final int showTime= (int) time;
                if (time >= 0) {
                    numberLabel.setText(String.valueOf(showTime));
                    
                }
                return true;
            }
        });

        numberLabel.setAlignment(1);
        stack.add(timerBg);
        stack.add(numberLabel);
        timerTable.setSkin(skin);
        //timerTable.setDebug(true);
        setTablePos(timerBg);
        timer(game);
    }

    public float timer(TDGame game) {
        time -= Gdx.graphics.getDeltaTime();
        do {
            System.out.println(time);
        } while (time <= 0);
        if (time<=0) game.dispose();
        return time;
    }
    private void setTablePos(Image timerBg) {
        timerTable.center().top().
                padTop(16).setPosition((float) Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());

        timerTable.add(stack).
                width(Gdx.graphics.getWidth() * (Constants.PIXELTOTILE * 2))
                .height(Gdx.graphics.getHeight() * (Constants.PIXELTOTILE * 2));


    }
    public Table getTimerTable() {
        return timerTable;
    }
}

