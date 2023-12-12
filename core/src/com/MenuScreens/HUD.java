package com.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Assets;
import com.mygdx.game.Constants;
import com.mygdx.game.Gamemap;

import static com.mygdx.game.Assets.*;

public class HUD extends Table {
    private final Table slimeTable;
    private final Table timerTable;
    private final Table boulderTable;
    public Skin skin;
    public HUD(Gamemap game) {
        skin = Assets.manager.get(SKIN);

        Texture signImg = game.assets.timerBg;
        Image timerBg = new Image(signImg);

        Texture boulderCurr = game.assets.boulderCurr;
        Image boulderCurrImg = new Image(boulderCurr);

        Texture slimeCurr = game.assets.slimeCurr;
        Image slimeCurrImg = new Image(slimeCurr);

        Texture currBg = game.assets.currBg;
        Image currBgImg = new Image(currBg);

        Texture troopBgImg = game.assets.troopBg;
        Image troopBg = new Image(troopBgImg);

        //TextureRegionDrawable buttonImage = new TextureRegionDrawable(new TextureRegion(slimeCurrImg));
        //ImageButton imageButton = new ImageButton(buttonImage);



        slimeTable = new Table();
        timerTable = new Table();
        boulderTable = new Table();
        slimeTable.setTouchable(Touchable.enabled);
        boulderTable.setTouchable(Touchable.enabled);
        timerTable.setTouchable(Touchable.enabled);

        //slimeTable.setDebug(true);
        //timerTable.setDebug(true);
        //boulderTable.setDebug(true);

        slimeTable.top().left().padTop(24);
        timerTable.center().top().padTop(16);
        boulderTable.left().top().padTop(24);

        slimeTable.setPosition(0, Gdx.graphics.getHeight());
        timerTable.setPosition((float) Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        boulderTable.setPosition(Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() * (Constants.pixeltotile * 2f)), Gdx.graphics.getHeight());

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(slimeCurr));
        ImageButton playButton = new ImageButton(drawable);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button clicked!");
            }
        });
        playButton.setSize(100, 50);


        Stack SlimeStack = new Stack();
        SlimeStack.add(currBgImg);
        SlimeStack.add(slimeCurrImg);

        slimeTable.add(SlimeStack).
                width(Gdx.graphics.getWidth() * (Constants.pixeltotile * 1.5f)).
                height(Gdx.graphics.getHeight() * (Constants.pixeltotile * 2)).
                padLeft(16).padBottom(24);
        slimeTable.row();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 1; col++) {
                Stack slimeLoop = new Stack(); //no se pueden mostrar tablas simultaneamente ojo chatpgt ily
                slimeCurrImg = new Image(slimeCurr);
                currBgImg = new Image(troopBgImg);
                slimeLoop.add(currBgImg);
                slimeLoop.add(slimeCurrImg);
                slimeTable.add(slimeLoop)
                        .width(Gdx.graphics.getWidth() * (Constants.pixeltotile * 1.5f))
                        .height(Gdx.graphics.getHeight() * (Constants.pixeltotile * 2.5f))
                        .padLeft(16);
                slimeLoop.add(currBgImg);

            }
            slimeTable.row();
        }



        Stack boulderStack = new Stack();
        boulderStack.add(currBgImg);
        boulderStack.add(boulderCurrImg);

        boulderTable.add(boulderStack).
                width(Gdx.graphics.getWidth() * (Constants.pixeltotile * 1.5f))
                .height(Gdx.graphics.getHeight() * (Constants.pixeltotile * 2)).
                padRight(16).padBottom(24);
        boulderTable.row();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 1; col++) {
                Image boulderImage = new Image(boulderCurr);
                Stack boulderLoop = new Stack();
                currBgImg = new Image(troopBgImg);
                boulderLoop.add(currBgImg);
                boulderLoop.add(boulderImage);

                boulderTable.add(boulderLoop)
                        .width(Gdx.graphics.getWidth() * (Constants.pixeltotile * 1.5f))
                        .height(Gdx.graphics.getHeight() * (Constants.pixeltotile * 2.5f))
                        .padRight(16);
            }
            boulderTable.row();
        }

        timerTable.add(timerBg).
                width(Gdx.graphics.getWidth() * (Constants.pixeltotile * 2))
                .height(Gdx.graphics.getHeight() * (Constants.pixeltotile * 2)); //ACA SE AÑADE EL TIMER

    }


    public Table getSlimeTable() {
        return slimeTable;
    }
    public Table getTimerTable() {
        return timerTable;
    }
    public Table getBoulderHud() {
        return boulderTable;
    }
}
