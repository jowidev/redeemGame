package com.Troops.TeamTroops;

import com.MenuScreens.TeamScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class MoneySlime extends Slime {
    public static final float SLIME_MONEY = 5;
    public static final float SLIME_INTERVAL = 2.5f;
    public static final float COST = 15;
    private float timer;
    private float money;
    public MoneySlime(int x, int y, float money) {
        super(x, y, 75, COST, 0);
        this.money = money;
        troopCost = 15;
    }

    public void generateMoney() {
        timer += Gdx.graphics.getDeltaTime();

        if (timer >= SLIME_INTERVAL) {
            timer -= SLIME_INTERVAL;
            money += SLIME_MONEY;

        }
    }
    @Override
    public void update(Viewport vp, Boulder boulder,ArrayList troopArr) {
        placeTroop(vp, TeamScreen.Team.SLIME, troopArr);
        generateMoney();
    }
}