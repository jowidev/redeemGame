package com.Troops;

import com.MenuScreens.TeamScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TDGame;

import java.util.ArrayList;

public abstract class Slime extends BaseTroop {

	public Slime(float x, float y, float hp, float troopCost, float dmg, boolean useMouseCoords) {
		super(x, y,hp, troopCost, dmg, useMouseCoords);
		troopPlaced = false;
		baseAnimation = new Animation<TextureRegion>(0.033f, TDGame.assets.slimewalk, PlayMode.LOOP);

		//if (TDGame.assets.slimewalk.size==0)System.out.println("o");
	}



	@Override
	public void update(Viewport vp, Boulder boulder, ArrayList<BaseTroop> troopArr) {
		if (useMouseCoords) {
		placeTroop(vp, TeamScreen.Team.SLIME, troopArr);
		}

	}
	@Override
	public void update(Viewport vp, Slime slime, ArrayList troopArr, ArrayList tempArr, boolean points) {

	}


}