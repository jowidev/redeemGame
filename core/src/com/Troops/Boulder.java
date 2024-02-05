package com.Troops;

import com.MenuScreens.TeamScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.TDGame;

import java.util.ArrayList;

public class Boulder extends BaseTroop {
	protected float BOULDER_SP = .5f*Gdx.graphics.getDeltaTime();
	public Boulder(int x, int y) {
		super(x, y, 150);
		baseAnimation = new Animation<TextureRegion>(.7f/7, TDGame.assets.boulderwalk, PlayMode.LOOP);

	}


	public void HitboxCheck(Slime slime, ArrayList<BaseTroop> troopArr, ArrayList tempArr) {
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
			boulderMov(troopArr);
		}
	}


	public void boulderMov(ArrayList arr) {
		hitbox.x -= .8f*Gdx.graphics.getDeltaTime();
		if (hitbox.x==0*Constants.PIXELTOTILE) {
			arr.remove(this);
			System.out.println("llega");
		}
	}

	@Override
	public void update(Viewport vp, ArrayList tempArr) {

	}

	@Override
	public void update(Viewport vp, Slime slime, ArrayList troopArr, ArrayList tempArr) {
		placeTroop(vp, TeamScreen.Team.BOULDER, troopArr);
		HitboxCheck(slime, troopArr, tempArr);

		//Client.placeObject(troopOnMouse, hitbox, "boulder");
	}

}

