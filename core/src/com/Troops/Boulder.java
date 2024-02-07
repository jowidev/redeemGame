package com.Troops;

import com.MenuScreens.TeamScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.GameScreen;
import com.mygdx.game.TDGame;

import java.util.ArrayList;

public class Boulder extends BaseTroop {
	protected float BOULDER_SP = 1*Gdx.graphics.getDeltaTime();
	public Boulder(int x, int y) {
		super(x, y, 150);
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
		hitbox.x -= BOULDER_SP;
		if (hitbox.x<=0&&hitbox.x>=-2) {
			arr.add(this);
			points+=1;
			System.out.println("llega");
		}
		return points;
	}

	@Override
	public void update(Viewport vp, ArrayList tempArr) {

	}

	@Override
	public void update(Viewport vp, Slime slime, ArrayList troopArr, ArrayList tempArr, int points) {
		placeTroop(vp, TeamScreen.Team.BOULDER, troopArr);
		HitboxCheck(slime, troopArr, tempArr, points);

		//Client.placeObject(troopOnMouse, hitbox, "boulder");
	}

}

