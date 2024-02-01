package com.Troops;

import com.MenuScreens.TeamScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
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

	public void update(Viewport vp, Slime slime, ArrayList troopArr, ArrayList tempArr) {
		placeTroop(vp, TeamScreen.Team.BOULDER, troopArr);
		HitboxCheck(slime, troopArr, tempArr);

		//Client.placeObject(troopOnMouse, hitbox, "boulder");
	}
	public void HitboxCheck(Slime slime, ArrayList troopArr, ArrayList tempArr) {
		if (slime!=null) {
			if (hitbox.overlaps(slime.hitbox)) {
				slime.takeDamage(.5f, tempArr);
			}
			else {
				boulderMov(troopArr);
			}
		} else {
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
	public void update() {

	}
}

