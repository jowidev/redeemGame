package com.Troops.TeamTroops;

import com.Troops.Boulder;

public class ArmoredBoulder extends Boulder {
    public static final float COST = 35;
    public ArmoredBoulder(int x, int y, boolean useMouseCoords) {
        super(x, y, 300, COST, .75f,1,useMouseCoords);
    }
}
