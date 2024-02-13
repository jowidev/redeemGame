package com.Troops.TeamTroops;

import com.Troops.Boulder;

public class BasicBoulder extends Boulder {
    public static final float COST = 15;

    public BasicBoulder(int x, int y, boolean useMouseCoords) {
        super(x, y, 150, COST,1f,1,useMouseCoords);
    }
}
