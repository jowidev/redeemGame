package com.Troops.TeamTroops;

import com.MenuScreens.TeamScreen;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class ShooterSlime extends Slime {

    public ShooterSlime(int x, int y) {
        super(x, y,75,35);
    }
    public void shoot(Boulder boulder, ArrayList<BaseTroop> troopArr) {
        if (boulder != null) {
            for (BaseTroop troop : troopArr) {
                if (troop instanceof Boulder && troop.hitbox.x==this.hitbox.x) {
                    System.out.println("hoho");
                }
            }
        }
    }

    @Override
    public void update(Viewport vp, Boulder boulder, ArrayList troopArr) {
        placeTroop(vp, TeamScreen.Team.SLIME, troopArr);
        shoot(boulder, troopArr);
    }
}
