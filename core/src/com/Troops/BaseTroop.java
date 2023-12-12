package com.Troops;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

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
        if (hp <= 0) {
            System.out.println("a");
        }
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

}