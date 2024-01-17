package com.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Assets;
import com.mygdx.game.Constants;
import com.mygdx.game.TDGame;

import static com.mygdx.game.Assets.SKIN;

    public class Timer extends Table {
        private final Table timerTable;
        private final Stack stack;
        private float time = 90;
        private int showTime;
        private final Label numberLabel;
        private boolean running = true;
        public Timer(TDGame game) {
            Texture signImg = game.assets.timerBg;
            Image timerBg = new Image(signImg);
            Skin skin = Assets.manager.get(SKIN);
            showTime = (int) time;
            timerTable = new Table();
            stack = new Stack();
            numberLabel = new Label(String.valueOf(showTime), skin);

            numberLabel.setScale(2);
            numberLabel.setFontScale(1.25f);
            numberLabel.setAlignment(Align.center);
            stack.add(timerBg);
            stack.add(numberLabel);
            timerTable.setSkin(skin);
            setTablePos();
        }

        public void update(TDGame game, float delta) {
            if (running && time > 0) {
                reduceTime(delta);
            } else {
                if (running) {
                    game.pause();
                    running = false;
                }
            }
        }
        private void reduceTime(float delta) {
            time -= delta;
            showTime = (int) time;
            numberLabel.setText(String.valueOf(showTime));
            //System.out.println(time);
        }

        public void stop() {
            running = false;
        }

        public int getTime() {
            return (int) time;
        }

        private void setTablePos() {
            timerTable.center().top().padTop(16)
                    .setPosition((float) Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());

            timerTable.add(stack)
                    .width(Gdx.graphics.getWidth() * (Constants.PIXELTOTILE * 2))
                    .height(Gdx.graphics.getHeight() * (Constants.PIXELTOTILE * 2));
        }

        public Table getTimerTable() {
            return timerTable;
        }
    }

