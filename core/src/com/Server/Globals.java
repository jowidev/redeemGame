package com.Server;

import com.Troops.BaseTroop;
import com.Troops.Bullet;
import com.Troops.TeamTroops.*;
import com.mygdx.game.GameScreen;

import java.util.ArrayList;

public abstract class Globals { //utils para comunicar entre hilos
    public static final int INITIAL_MONEY = 175;
    // Server

    public static ServerThread server;
    public static ServerScreen serverScreen;

    public static float player0Money = INITIAL_MONEY;
    public static float player1Money = INITIAL_MONEY*1.5f;

    // Client
    public static Integer clientId;
    public static GameScreen clientScreen;

    public static int over = -1;
    public static boolean ready;


    public enum TROOP {
        SHIELD_SLIME,
        MONEY_SLIME,
        SHOOTER_SLIME,
        BASIC_BOULDER,
        FAST_BOULDER,
        ARMORED_BOULDER
    }

    public static BaseTroop troopFactory(TROOP troopType, float money, ArrayList<Bullet> bulletArr) {
        switch (troopType) {
            case SHIELD_SLIME:
                return new ShieldSlime(0, 0, true);
            case MONEY_SLIME:
                return new MoneySlime(0, 0, money, true);
            case SHOOTER_SLIME:
                return new ShooterSlime(0, 0, bulletArr, true);
            case BASIC_BOULDER:
                return new BasicBoulder(0, 0, true);
            case FAST_BOULDER:
                return new FastBoulder(0, 0, true);
            case ARMORED_BOULDER:
                return new ArmoredBoulder(0, 0, true);
            default:
                throw new IllegalArgumentException("Unsupported troop type: " + troopType);
        }
    }

    public static TROOP determineTroopType(BaseTroop troop) {
        if (troop instanceof ShieldSlime) {
            return TROOP.SHIELD_SLIME;
        } else if (troop instanceof MoneySlime) {
            return TROOP.MONEY_SLIME;
        } else if (troop instanceof ShooterSlime) {
            return TROOP.SHOOTER_SLIME;
        } else if (troop instanceof BasicBoulder) {
            return TROOP.BASIC_BOULDER;
        } else if (troop instanceof FastBoulder) {
            return TROOP.FAST_BOULDER;
        } else if (troop instanceof ArmoredBoulder) {
            return TROOP.ARMORED_BOULDER;
        }
        return TROOP.MONEY_SLIME;
    }


}
