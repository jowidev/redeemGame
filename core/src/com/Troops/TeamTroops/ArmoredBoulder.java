package com.Troops.TeamTroops;

import com.Troops.Boulder;
import com.mygdx.game.TDGame;

public class ArmoredBoulder extends Boulder {
    public static final float COST = 35;
    public ArmoredBoulder(int x, int y, boolean useMouseCoords) {
        super(x, y, 325, COST, .75f,1,useMouseCoords);
        typeText = TDGame.assets.armor;

    }
}
