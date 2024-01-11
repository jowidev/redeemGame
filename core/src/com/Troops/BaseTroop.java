    package com.Troops;

    import com.Server.Client;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Input;
    import com.badlogic.gdx.graphics.g2d.Animation;
    import com.badlogic.gdx.graphics.g2d.TextureRegion;
    import com.badlogic.gdx.math.Rectangle;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.scenes.scene2d.Actor;
    import com.badlogic.gdx.utils.viewport.Viewport;

    public class BaseTroop extends Actor {
        protected float hp;
        protected Animation<TextureRegion> baseAnimation;
        public final float TROOP_WIDTH = 1f;
        public final float TROOP_HEIGHT = 1f;
        public Rectangle hitbox = new Rectangle();
        protected boolean troopOnMouse;
        public BaseTroop(int x, int y, float hp) {
            hitbox.set(x, y, TROOP_WIDTH, TROOP_HEIGHT);
            this.hp = hp;

        }

        public void takeDamage(float damage) {
            hp -= damage;
            if (hp == 0) {
                System.out.println("muerto");
                hitbox.setPosition(-10,-10);
            }
        }


        public void placeTroop(Viewport viewport) {
            if (!troopOnMouse) {
                Vector2 pos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                hitbox.set(pos.x-1,pos.y-1, TROOP_WIDTH, TROOP_HEIGHT);
            }
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !troopOnMouse) {
                troopOnMouse = true;
                //TDGame.assets.slimeplaced.play(); depending on the team it should play the slimeplaced or the boulderplaced
            }
        }

        public void setHp(float hp) {this.hp = hp;}
        public float getHp() {return hp;}

    }