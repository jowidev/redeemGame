    package com.Troops;

    import com.MenuScreens.TeamSelScreen;
    import com.Server.Client;
    import com.badlogic.gdx.Game;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Input;
    import com.badlogic.gdx.graphics.g2d.Animation;
    import com.badlogic.gdx.graphics.g2d.TextureRegion;
    import com.badlogic.gdx.math.Rectangle;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.scenes.scene2d.Actor;
    import com.badlogic.gdx.utils.viewport.Viewport;
    import com.mygdx.game.GameScreen;
    import com.mygdx.game.TDGame;

    import java.util.ArrayList;

    import static com.badlogic.gdx.Input.Keys.L;

    public abstract class BaseTroop extends Actor {
        protected Animation<TextureRegion> baseAnimation;
        public final float TROOP_WIDTH = 1f;
        public final float TROOP_HEIGHT = 1f;
        public Rectangle hitbox = new Rectangle();
        protected float stateTime = 0;
        protected float hp;
        protected float sp, dmg;
        protected boolean troopOnMouse;
        public BaseTroop(int x, int y, float hp) {
            hitbox.set(x, y, TROOP_WIDTH, TROOP_HEIGHT);
            this.hp = hp;

        }

        public void takeDamage(float damage, ArrayList tempArr) {
            hp -= damage;
            System.out.println(this.hp);
            if (hp <= 0) {
                System.out.println("muerto");
                hitbox.setPosition(-10,-10);
                tempArr.add(this);
            }
        }

        public void placeTroop(Viewport viewport, TeamSelScreen.Team team, ArrayList troopArr) {
            Vector2 pos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if (!troopOnMouse) {

                hitbox.set(pos.x-1,pos.y-1, TROOP_WIDTH, TROOP_HEIGHT);
            }
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !troopOnMouse) {
                troopOnMouse = true;
                if (team.equals(TeamSelScreen.Team.SLIME)) {
                    TDGame.assets.slimeplaced.play();
                    troopArr.add(this);
                }
                else{
                    TDGame.assets.boulderPlaced.play();
                    troopArr.add(this);
                }
            }
        }

        public void render() {
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = baseAnimation.getKeyFrame(stateTime, true);
            TDGame.batch.draw(currentFrame, hitbox.x, hitbox.y,2,2);
        }


        public void setHp(float hp) {this.hp = hp;}
        public float getHp() {return hp;}

    }