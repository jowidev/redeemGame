package com.Troops.TeamTroops;

import com.Troops.Boulder;

public class FastBoulder extends Boulder {
    public static final float COST = 25;
    public FastBoulder(int x, int y, boolean useMouseCoords) {
        super(x, y, 150, COST, 1.5f, 1, useMouseCoords);
    }
}
