package com.Troops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TDGame;
import com.Server.Client;

public class Boulder extends BaseTroop {
	private final TDGame game;
	public float stateTime;
	float boulderW, boulderH;



	public Boulder(TDGame game, int x, int y) {
		super(x, y, 0.5f, 2, 150);
		stateTime = 0;
		this.game = game;
		boulderW = 1;
		boulderH = 1;
		baseAnimation = new Animation<TextureRegion>(.7f/7, game.assets.boulderwalk, PlayMode.LOOP);
		hitbox.set(x,y, boulderW, boulderH);
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
		troopOnMouse = placeTroop(viewport, troopOnMouse, s);

		/*

		if(!troopOnMouse) {
			Vector3 position = viewport.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
			hitbox.x = position.x-1;
			hitbox.y = position.y-1;
			hitbox.setPosition(position.x, position.y);
		}
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !troopOnMouse) {
			troopOnMouse = true;
			game.assets.boulderPlaced.play();
		}*/
		if (slime != null) {
			HitboxCheck(slime);
		}	else {
			hitbox.x -= 2*Gdx.graphics.getDeltaTime();
			if (hitbox.x==1) {
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
