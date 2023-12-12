package com.Troops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Gamemap;
import com.Server.Client;

public class Boulder extends BaseTroop {
	private final Gamemap game;
	public float stateTime;
	private boolean boulderOnMouse;
	float boulderW, boulderH;


	//public Rectangle boulderHitbox = new Rectangle(); //shape crea el coso
	// shaperenderer muestra el coso
	public Boulder(Gamemap game, int x, int y) {
		super(x, y, 2, 2, 150);
		stateTime = 0;
		this.game = game;
		boulderW = 1;
		boulderH = 1;
		baseAnimation = new Animation<TextureRegion>(.7f/7, game.assets.boulderwalk, PlayMode.LOOP);
		hitbox.set(x,y, boulderW, boulderH);
	}



	public void HitboxCheck(Slime slime) {

		if (hitbox.overlaps(slime.slimeHitbox)) {
				hitbox.x -=.0f*Gdx.graphics.getDeltaTime();
				slime.takeDamage(1);
		}
		else {
			hitbox.x -= 2*Gdx.graphics.getDeltaTime();
			if (hitbox.x==1) {
				System.out.println("+1");
			}
		}
	}

	public void update(Viewport viewport, Slime slime) {
		if(!boulderOnMouse) {
			Vector3 position = viewport.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
			hitbox.x = position.x-1;
			hitbox.y = position.y-1;
			hitbox.setPosition(position.x, position.y);
		}
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !boulderOnMouse) {
			boulderOnMouse = true;
			game.assets.boulderPlaced.play();
		}
		HitboxCheck(slime);
		Client.placeBoulder(boulderOnMouse, hitbox);
	}


	public void render() {
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion currentFrame = baseAnimation.getKeyFrame(stateTime, true);
		Gamemap.batch.draw(currentFrame, hitbox.x, hitbox.y,2,2);
	}


}
