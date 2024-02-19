package com.Troops.TeamTroops;

import com.Troops.Slime;

public class ShieldSlime extends Slime {
    public static final float COST = 20;

    public ShieldSlime(int x, int y, boolean useMouseCoords) {
        super(x, y,275,COST,1,useMouseCoords);
    }
}
