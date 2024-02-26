package com.Troops;

import com.MenuScreens.TeamScreen;
import com.Server.Client;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.TDGame;

import java.util.ArrayList;

public abstract class BaseTroop extends Actor {
    protected Animation<TextureRegion> baseAnimation;
    public final float TROOP_WIDTH = 1f;
    public final float TROOP_HEIGHT = 1f;
    protected Rectangle typeHitbox = new Rectangle();
    public Rectangle hitbox = new Rectangle();
    protected float stateTime = 0;
    protected float hp;
    protected float dmg;
    public boolean troopPlaced;
    public float troopCost;
    protected int troopRender = 2;
    private boolean locked = false;
    protected boolean useMouseCoords;
    protected Texture typeText;
    float x, y;
    public BaseTroop(float x, float y, float hp, float troopCost, float dmg, boolean useMouseCoords) { //basetroop padre tropas heredan de esto
        this.hp = hp;
        this.dmg = dmg;
        this.x =x;
        this.y=y;
        this.troopCost = troopCost;
        this.useMouseCoords = useMouseCoords;
    }

    public void takeDamage(float damage, ArrayList<BaseTroop> tempArr) { //daño
        hp -= damage;
        if (hp <= 0) {
            tempArr.add(this);
            hitbox.setSize(0,0);
            troopRender=0;
        }
    }

    public void placeTroop(Viewport viewport, TeamScreen.Team team, ArrayList<BaseTroop> troopArr) {
        Vector2 pos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        if (!troopPlaced) {
            if (useMouseCoords) {
                typeHitbox.set(pos.x, pos.y-1, TROOP_WIDTH/2, TROOP_HEIGHT/2);
                hitbox.set(pos.x - 1, pos.y - 1, TROOP_WIDTH, TROOP_HEIGHT);
            } else {
                hitbox.set(x, y, TROOP_WIDTH, TROOP_HEIGHT);
                typeHitbox.set(pos.x, pos.y-1, TROOP_WIDTH/2, TROOP_HEIGHT/2);

            }
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !troopPlaced) {
                troopPlaced = true;
                if (team.equals(TeamScreen.Team.SLIME)) {
                    Client.placeTroopServer(hitbox, "slime");
                    TDGame.assets.slimeplaced.play();
                    troopArr.add(this);
                } else {
                    Client.placeTroopServer(hitbox, "boulder");
                    TDGame.assets.boulderPlaced.play();
                    troopArr.add(this);
                }
            }
        }
    }
    public void render() {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = baseAnimation.getKeyFrame(stateTime, true);
        TDGame.batch.draw(currentFrame, hitbox.x, hitbox.y,troopRender,troopRender);
        TDGame.batch.draw(typeText, hitbox.x+1, hitbox.y+1.5f, (float) troopRender /4, (float) troopRender /4);

    }

    public abstract void update(Viewport vp, ArrayList<BaseTroop> tempArr);
    public abstract void update(Viewport vp, ArrayList troopArr, ArrayList tempArr, boolean boulderReached);
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    public float getHp() {
        return hp;
    }
}