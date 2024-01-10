    package com.Troops;

    import com.Server.Client;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Input;
    import com.badlogic.gdx.graphics.g2d.Animation;
    import com.badlogic.gdx.graphics.g2d.TextureRegion;
    import com.badlogic.gdx.math.Rectangle;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.math.Vector3;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.utils.viewport.Viewport;

    public class BaseTroop {
        protected float hp;
        public Rectangle hitbox = new Rectangle();
        protected Animation<TextureRegion> baseAnimation;
        protected boolean troopOnMouse;
        public BaseTroop(int x, int y, float width, float height, float hp) {
            hitbox.set(x, y, width, height);
            this.hp = hp;

        }

        public void takeDamage(int damage) {
            hp -= damage;
            if (hp == 0) {
                System.out.println("muerto");
                hitbox.setPosition(-10,-10);
            }
        }

        public boolean placeTroop(Viewport viewport, boolean troopOnMouse, Stage stage ) {
            if(!troopOnMouse) {
                Vector2 position = viewport.unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));
                hitbox.x = position.x-1;
                hitbox.y = position.y-1;
                hitbox.setPosition(position.x, position.y); //ta mal

            }
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !troopOnMouse) {
                troopOnMouse = true;
                //GridCell cell = new GridCell(stage);
                //cell.snapToGrid(this);


            }

            return troopOnMouse;
        }

        public float getHp() {
            return hp;
        }

        public void setHp(float hp) {
            this.hp = hp;
        }

    }