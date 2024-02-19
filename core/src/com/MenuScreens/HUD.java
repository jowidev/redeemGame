package com.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Assets;
import com.mygdx.game.Constants;
import com.mygdx.game.TDGame;

import static com.mygdx.game.Assets.SKIN;

public class HUD extends Table {
    private float moneyTimer = 0; // Nuevo temporizador para el sistema de plata
    private static final float MONEY_INTERVAL = 3.0f; // Intervalo para agregar dinero (en segundos)
    private static final int MONEY_AMOUNT = 5; // Cantidad de dinero a agregar cada vez
    private static final int SLIME_BONUS_AMOUNT = 1;
    private final Table timerTable;
    private final Table slimeTable;
    private final Table boulderTable;
    private final Stack stack;
    private final Stack stack2;
    private final Stack stack3;
    private float time = 121;
    private int showTime;
    private float slimeMoney;
    private float boulderMoney;
    private final Label numberLabel;
    private boolean running = true;
    private Label slimeMoneyLabel;
    private Label boulderMoneyLabel;
    private Skin skin;
    private float money;

    public HUD(TDGame game, float money) {
        skin = Assets.manager.get(SKIN);
        Texture signImg = game.assets.timerBg;
        Texture currBg = game.assets.currBg;
        Image timerBg = new Image(signImg);
        Image bgCurr = new Image(currBg);
        Image bgCurr2 = new Image(currBg);
        showTime = (int) time;
        timerTable = new Table();
        this.money = money;
        this.slimeMoney = money;
        this.boulderMoney = money;

        stack = new Stack();
        numberLabel = new Label(String.valueOf(showTime), skin);
        slimeMoneyLabel = new Label("$" + money, skin);
        slimeMoneyLabel.setScale(2);
        slimeMoneyLabel.setFontScale(1.25f);
        slimeMoneyLabel.setAlignment(Align.center);
        boulderMoneyLabel = new Label("$" + money, skin);
        boulderMoneyLabel.setScale(2);
        boulderMoneyLabel.setFontScale(1.25f);
        boulderMoneyLabel.setAlignment(Align.center);
        numberLabel.setScale(2);
        numberLabel.setFontScale(1.25f);
        numberLabel.setAlignment(Align.center);
        stack.add(timerBg);
        stack2 = new Stack();
        stack3 = new Stack();
        stack2.add(bgCurr);
        stack3.add(bgCurr2);
        slimeTable = new Table();
        boulderTable = new Table();
        stack.add(numberLabel);
        stack2.add(slimeMoneyLabel); // esta linea hace que este en la tabla pero no hace nada
        stack3.add(boulderMoneyLabel);
        timerTable.setSkin(skin);
        slimeTable.setSkin(skin);
        boulderTable.setSkin(skin);
        setTablePos();
        bothTables();





    }

    public void bothTables() {

        slimeTable.setDebug(false);
        slimeTable.setPosition(16, (float) Gdx.graphics.getHeight() - (float) Gdx.graphics.getHeight() /6f);
        slimeTable.setSize(64,48);
        slimeTable.add(stack2)
                .width(Gdx.graphics.getWidth() * (Constants.PIXELTOTILE * 2))
                .height(Gdx.graphics.getHeight() * (Constants.PIXELTOTILE * 2));


        boulderTable.setDebug(false);
        boulderTable.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/15f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/6f);

        boulderTable.setSize(64,48);
        boulderTable.add(stack3)
                .width(Gdx.graphics.getWidth() * (Constants.PIXELTOTILE * 2))
                .height(Gdx.graphics.getHeight() * (Constants.PIXELTOTILE * 2));

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
    public void updateMoney(float delta, int numberOfSlimes, boolean hasMoneySlime) {
        moneyTimer += delta;

        if (moneyTimer >= MONEY_INTERVAL) {
            moneyTimer -= MONEY_INTERVAL;

            if (hasMoneySlime) {
                money += MONEY_AMOUNT + (numberOfSlimes * SLIME_BONUS_AMOUNT);
            } else {
                money += MONEY_AMOUNT;
            }
            slimeMoneyLabel.setText("$" + money);
            boulderMoneyLabel.setText("$" + money);
        }
    }
    public boolean hasEnoughMoney(float troopCost) {
        return money >= troopCost;
    }

    public void decreaseSlimeMoney(float troopCost) {
        money -= troopCost;
        slimeMoneyLabel.setText("$" + money);
    }

    //metodopara disminuir el dinero de los boulders
    public void decreaseBoulderMoney(float troopCost) {
        money -= troopCost;
        boulderMoneyLabel.setText("$" + money);
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