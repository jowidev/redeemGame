package com.Troops;

import com.MenuScreens.GameOverScreen;
import com.MenuScreens.TeamScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TDGame;

import java.util.ArrayList;

public abstract class Boulder extends BaseTroop {
	protected float sp;
	protected boolean useMouseCoords;
	public Boulder(int x, int y, float hp, float troopCost, float sp, float dmg, boolean useMouseCoords) {
		super(x, y, hp, troopCost, dmg, useMouseCoords);
		this.sp = sp;
		this.useMouseCoords = useMouseCoords;
		baseAnimation = new Animation<TextureRegion>(.7f/7, TDGame.assets.boulderwalk, PlayMode.LOOP);
	}


	public void HitboxCheck(ArrayList<BaseTroop> troopArr, ArrayList tempArr) {
		boolean isColliding = false;


		for (BaseTroop troop : troopArr) {
			if (troop instanceof Slime && troop.hitbox.overlaps(hitbox)) {
				troop.takeDamage(0.5f, tempArr);
				isColliding = true;
				break;
			}
		}
		if (!isColliding) {
			boulderMov(tempArr);
		}
	}


	public void boulderMov(ArrayList<BaseTroop> tempArr) {
		hitbox.x -= sp*Gdx.graphics.getDeltaTime();
		if (hitbox.x<=-2&&hp>0) {
			tempArr.add(this);
//			game.setScreen(new GameOverScreen(TeamScreen.Team.BOULDER, game));

		}
	}

	@Override
	public void update(Viewport vp, ArrayList tempArr){}
	@Override
	public void update(Viewport vp,ArrayList troopArr, ArrayList tempArr, boolean boulderReached) {
		HitboxCheck(troopArr, tempArr);
		if (useMouseCoords) placeTroop(vp, TeamScreen.Team.BOULDER, troopArr);
	}

}

