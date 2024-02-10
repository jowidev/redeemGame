package com.Troops.TeamTroops;

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
	public Boulder(int x, int y, float hp, float troopCost, float sp) {
		super(x, y, hp, troopCost);
		this.sp = sp;
		baseAnimation = new Animation<TextureRegion>(.7f/7, TDGame.assets.boulderwalk, PlayMode.LOOP);

	}


	public void HitboxCheck(Slime slime, ArrayList<BaseTroop> troopArr, ArrayList tempArr, int points) {
		boolean isColliding = false;

		if (slime != null) {
			for (BaseTroop troop : troopArr) {
				if (troop instanceof Slime && troop.hitbox.overlaps(hitbox)) {
					troop.takeDamage(0.5f, tempArr);
					isColliding = true;
				}
			}
		}

		if (!isColliding) {
			boulderMov(tempArr,points);
		}
	}


	public int boulderMov(ArrayList<BaseTroop> arr, int points) {
		hitbox.x -= sp*Gdx.graphics.getDeltaTime();
		if (hitbox.x<=0&&hitbox.x>=-2) {
			arr.add(this);
			points+=1;
			System.out.println("llega");
		}
		return points;
	}

	@Override
	public void update(Viewport vp, Boulder boulder, ArrayList tempArr) {

	}

	@Override
	public void update(Viewport vp, Slime slime, ArrayList troopArr, ArrayList tempArr, int points) {
		placeTroop(vp, TeamScreen.Team.BOULDER, troopArr);
		HitboxCheck(slime, troopArr, tempArr, points);

	}

}

