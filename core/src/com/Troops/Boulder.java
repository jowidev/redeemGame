package com.Troops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.TDGame;
import com.Server.Client;
import com.sun.org.apache.bcel.internal.Const;

public class Boulder extends BaseTroop {
	private final TDGame game;
	public float stateTime;
	float boulderW, boulderH;



	public Boulder(TDGame game, int x, int y) {
		super(x, y, 150);
		stateTime = 0;
		this.game = game;
		baseAnimation = new Animation<TextureRegion>(.7f/7, game.assets.boulderwalk, PlayMode.LOOP);
	}



	public void HitboxCheck(Slime slime) {
		if (slime.hitbox!=null) {
			if (hitbox.overlaps(slime.hitbox)) {
				hitbox.x -=.0f*Gdx.graphics.getDeltaTime();
				slime.takeDamage(1);
			}
			else {
				hitbox.x -= 2*Gdx.graphics.getDeltaTime();
				if (hitbox.x==1) {
					System.out.println("+1");
				}
			}
		} else {
			hitbox.x -= 2*Gdx.graphics.getDeltaTime();
			if (hitbox.x==1) {
				System.out.println("+1");
			}
		}
	}

	public void update(Viewport viewport, Slime slime, Stage s) {
		placeTroop(viewport);

		if (slime != null) {
			HitboxCheck(slime);
		}	else {
			hitbox.x -= 2*Gdx.graphics.getDeltaTime();
			if (hitbox.x==1 * Constants.PIXELTOTILE ) {
				System.out.println("+1");
			}
		}
		Client.placeObject(troopOnMouse, hitbox, "boulder");
	}


	public void render() {
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion currentFrame = baseAnimation.getKeyFrame(stateTime, true);
		TDGame.batch.draw(currentFrame, hitbox.x, hitbox.y,2,2);
	}


}
