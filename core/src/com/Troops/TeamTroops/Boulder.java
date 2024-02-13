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
	public Boulder(int x, int y, float hp, float troopCost, float sp, float dmg) {
		super(x, y, hp, troopCost, dmg);
		this.sp = sp;
		baseAnimation = new Animation<TextureRegion>(.7f/7, TDGame.assets.boulderwalk, PlayMode.LOOP);

	}


	public boolean HitboxCheck(Slime slime, ArrayList<BaseTroop> troopArr, ArrayList tempArr, boolean boulderReached) {
		boolean isColliding = false;
		boolean reached = false;
		if (slime != null) {
			for (BaseTroop troop : troopArr) {
				if (troop instanceof Slime && troop.hitbox.overlaps(hitbox)) {
					troop.takeDamage(0.5f, tempArr);
					isColliding = true;
				}
			}
		}

		if (!isColliding) {
			reached = boulderMov(tempArr,boulderReached);
		}
		System.out.println(reached);
		return reached;
	}


	public boolean boulderMov(ArrayList<BaseTroop> arr, boolean points) {
		hitbox.x -= sp*Gdx.graphics.getDeltaTime();
		if (hitbox.x<=0&&hitbox.x>=-2) {
			arr.add(this);
			points = true;
		}
		return points;
	}

	@Override
	public void update(Viewport vp, Boulder boulder, ArrayList tempArr) {

	}

	@Override
	public void update(Viewport vp, Slime slime, ArrayList troopArr, ArrayList tempArr, boolean boulderReached) {
		placeTroop(vp, TeamScreen.Team.BOULDER, troopArr);
		HitboxCheck(slime, troopArr, tempArr, boulderReached);

	}

}

