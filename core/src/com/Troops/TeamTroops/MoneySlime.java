package com.Troops.TeamTroops;

import com.MenuScreens.TeamScreen;
import com.Troops.Boulder;
import com.Troops.Slime;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TDGame;

import java.util.ArrayList;

public class MoneySlime extends Slime {
    public static final float SLIME_MONEY = 5;
    public static final float SLIME_INTERVAL = 2.5f;
    public static final float COST = 15;
    private float timer;
    private float money;
    public MoneySlime(int x, int y, float money, boolean useMouseCoords) {
        super(x, y, 75, COST, 0,useMouseCoords);
        this.money = money;
        troopCost = 15;
        typeText = TDGame.assets.moneyImg;

    }

    public void generateMoney() {
        timer += Gdx.graphics.getDeltaTime();

        if (timer >= SLIME_INTERVAL) {
            timer -= SLIME_INTERVAL;
            money += SLIME_MONEY;

        }
    }
    @Override
    public void update(Viewport vp, ArrayList troopArr) {
        placeTroop(vp, TeamScreen.Team.SLIME, troopArr);
        generateMoney();
    }
}