package com.Troops.TeamTroops;

import com.MenuScreens.TeamScreen;
import com.Troops.BaseTroop;
import com.Troops.Boulder;
import com.Troops.Bullet;
import com.Troops.Slime;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;

public class ShooterSlime extends Slime {
    float timer = 0;
    private ArrayList<Bullet> bulletArr;
    public static final float COST = 25;

    public ShooterSlime(int x, int y, ArrayList<Bullet> bulletArr, boolean useMouseCoords) {
        super(x, y, 75, COST,0, useMouseCoords);
        this.bulletArr = bulletArr;
    }

    @Override
    public void update(Viewport vp, Boulder boulder, ArrayList<BaseTroop> troopArr) {
        placeTroop(vp, TeamScreen.Team.SLIME, troopArr);
        shoot(boulder, troopArr);
    }

    public void shoot(Boulder boulder, ArrayList<BaseTroop> troopArr) {
        if (boulder != null) {
            for (BaseTroop troop : troopArr) {
                if (troop instanceof Boulder) {
                    float slimeCenterX = this.hitbox.x + this.hitbox.width / 2;
                    float slimeCenterY = this.hitbox.y + this.hitbox.height / 2;
                    float boulderCenterX = troop.hitbox.x + troop.hitbox.width / 2;
                    float boulderCenterY = troop.hitbox.y + troop.hitbox.height / 2;

                    float distanceX = boulderCenterX - slimeCenterX;
                    float distanceY = boulderCenterY - slimeCenterY;

                    if (distanceX > 0 && distanceY>=0&&distanceY<2) {
                        timer +=Gdx.graphics.getDeltaTime();
                        if (timer >= 2) {
                            timer -= 2;
                            bulletArr.add(new Bullet(slimeCenterX,slimeCenterY, troopArr));
                        }
                    }
                }
            }

        }
    }



}