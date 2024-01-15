package com.Troops;

import com.MenuScreens.TeamSelScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.GameScreen;
import com.mygdx.game.TDGame;
import com.Server.Client;
import com.sun.org.apache.bcel.internal.Const;

import java.util.ArrayList;

public class Boulder extends BaseTroop {
	protected float BOULDER_SP = .5f*Gdx.graphics.getDeltaTime();
	public Boulder(int x, int y) {
		super(x, y, 150);
		baseAnimation = new Animation<TextureRegion>(.7f/7, TDGame.assets.boulderwalk, PlayMode.LOOP);

	}
	public void update(Viewport vp, Slime slime, ArrayList troopArr, ArrayList tempArr) {
		placeTroop(vp, TeamSelScreen.Team.BOULDER, troopArr);
		if (slime != null) {
			HitboxCheck(slime, troopArr, tempArr);
		}
		//Client.placeObject(troopOnMouse, hitbox, "boulder");
	}
	public void HitboxCheck(Slime slime, ArrayList troopArr, ArrayList tempArr) {
		if (slime!=null) {
			System.out.println("t");
			if (hitbox.overlaps(slime.hitbox)) {
				slime.takeDamage(.5f, tempArr);
			}
			else {
				boulderMov(troopArr);
			}
		} else {
			System.out.println("q");
			boulderMov(troopArr);
		}
	}

	public void boulderMov(ArrayList arr) {
		hitbox.x -= .8f*Gdx.graphics.getDeltaTime();
		if (hitbox.x==-1*Constants.PIXELTOTILE) {
			arr.remove(this);
		}
	}
}

