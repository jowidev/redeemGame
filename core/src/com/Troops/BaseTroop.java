package com.Troops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BaseTroop {
    protected float hp;
    public Rectangle hitbox = new Rectangle();
    protected Animation<TextureRegion> baseAnimation;

    public BaseTroop(int x, int y, float width, float height, float hp) {
        hitbox.set(x, y, width, height);
        this.hp = hp;

    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp == 0) {
            System.out.println("muerto");
        }
    }

    public void placeTroop(Viewport viewport, boolean troopOnMouse) {
       if (!troopOnMouse) {
           Vector3 position = viewport.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
           hitbox.x = position.x-1;
           hitbox.y = position.y-1;
           hitbox.setPosition(position.x, position.y);
       }
       if (Gdx.input.isKeyJustPressed(Input.Buttons.LEFT) && !troopOnMouse) {
           troopOnMouse = true;

       }
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

}