package com.Troops;

import com.MenuScreens.TeamScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TDGame;

import java.util.ArrayList;

public class Slime extends BaseTroop {
	public Slime( int x, int y) {
		super(x, y,100);
		troopOnMouse = false;
		baseAnimation = new Animation<TextureRegion>(0.033f, TDGame.assets.slimewalk, PlayMode.LOOP);

		//if (TDGame.assets.slimewalk.size==0)System.out.println("o");
	}

	public void update(Viewport vp, ArrayList array) {
		placeTroop(vp, TeamScreen.Team.SLIME, array);

		//Client.placeObject(troopOnMouse, hitbox, "slime");
	}
}
