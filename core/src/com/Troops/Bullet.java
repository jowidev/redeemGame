package com.Troops;

import com.Troops.TeamTroops.BaseTroop;
import com.Troops.TeamTroops.Boulder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.TDGame;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

public class Bullet extends Actor {
    public Rectangle bulletHitbox= new Rectangle();
    public final Texture texture;
    public Bullet(float slimeX, float slimeY, ArrayList troopArr) {
        this.texture = TDGame.assets.bullet;

        bulletHitbox.set(slimeX,slimeY,.5f,1);
    }
    public void update(ArrayList<BaseTroop> troopArr, ArrayList<BaseTroop> tempArr, ArrayList<Bullet> tempBullet) {
        bulletHitbox.x+=.1f;
        for (BaseTroop troop : troopArr) {
            if (troop instanceof Boulder) {
                if (troop.hitbox.overlaps(bulletHitbox)) {
                    troop.takeDamage(20, tempArr);
                    tempBullet.add(this);
                }
            }
        }

    }
    public void draw() {
        TDGame.batch.draw(texture, bulletHitbox.x, bulletHitbox.y, .75f,.75f);
    }
}
