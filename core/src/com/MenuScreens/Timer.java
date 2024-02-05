package com.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Assets;
import com.mygdx.game.Constants;
import com.mygdx.game.TDGame;
import sun.tools.jconsole.Tab;

import static com.mygdx.game.Assets.SKIN;

    public class Timer extends Table {
        private final Table timerTable;
        private Table slimeTable;
        private Table boulderTable;
        private final Stack stack;
        private float time = 90;
        private int showTime;
        private final Label numberLabel;
        private boolean running = true;
        public Timer(TDGame game) {
            Texture signImg = game.assets.timerBg;
            Texture currBg = game.assets.currBg;
            Image timerBg = new Image(signImg);
            Skin skin = Assets.manager.get(SKIN);
            Image bgCurr = new Image(currBg);
            Image bgCurr2 = new Image(currBg);
            showTime = (int) time;
            timerTable = new Table();


            stack = new Stack();
            numberLabel = new Label(String.valueOf(showTime), skin);

            numberLabel.setScale(2);
            numberLabel.setFontScale(1.25f);
            numberLabel.setAlignment(Align.center);
            stack.add(timerBg);

            bothTables(bgCurr, bgCurr2);
            stack.add(numberLabel);
            timerTable.setSkin(skin);
            slimeTable.setSkin(skin);
            setTablePos();
        }

        public void bothTables(Image bgCurr, Image bgCurr2) {
            slimeTable = new Table();
            boulderTable = new Table();


            slimeTable.setDebug(true);
            slimeTable.setPosition(16, (float) Gdx.graphics.getHeight() - (float) Gdx.graphics.getHeight() /6f);
            slimeTable.setSize(64,48);
            slimeTable.add(bgCurr);


            boulderTable.add(bgCurr2);
            boulderTable.setDebug(true);
            boulderTable.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/15f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/6f);
            boulderTable.setSize(64,48);
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
        }

        public void stop() {
            running = false;
        }

        private void setTablePos() {
            timerTable.center().top().padTop(16)
                    .setPosition((float) Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());

            timerTable.add(stack)
                    .width(Gdx.graphics.getWidth() * (Constants.PIXELTOTILE * 2))
                    .height(Gdx.graphics.getHeight() * (Constants.PIXELTOTILE * 2));


        }

        public int getTime() {
            return (int) time;
        }
        public Table getSlimeTable() {
            return slimeTable;
        }
        public Table getBoulderTable() {
            return boulderTable;
        }
        public Table getTimerTable() {
            return timerTable;
        }
    }

