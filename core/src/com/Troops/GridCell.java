    package com.Troops;

    import com.Troops.TeamTroops.Slime;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.math.Vector;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.scenes.scene2d.Actor;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.scenes.scene2d.Touchable;
    import com.badlogic.gdx.utils.viewport.Viewport;
    import com.mygdx.game.Constants;

    public class GridCell extends Actor {
        private boolean isOcc = false;
        public float gridCellW = Gdx.graphics.getWidth() * (Constants.PIXELTOTILE * 3.25f);
        public float gridCellH = Gdx.graphics.getHeight() * (Constants.PIXELTOTILE * 5.25f);
        public GridCell(Stage s, Slime troop) {

            setTouchable(Touchable.enabled);
            setColor(1,0,0,1);
            setBounds(64,64, gridCellW, gridCellH);
            s.addActor(this);

            setDebug(true);
            touched(troop, s.getViewport());
        }


        public void touched(Slime troop, Viewport viewport) {
            if (troop != null && troop.troopOnMouse) {
                if (Gdx.input.isButtonJustPressed(0)) {
                    Vector2 mousePos = viewport.unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));
                    if (mousePos.x >= getX() && mousePos.x < getX() + gridCellW &&
                            mousePos.y >= getY() && mousePos.y < getY() + gridCellH) {
                        System.out.println("click");
                        Vector2 troopPos = viewport.unproject(new Vector2(getCenterX(),getCenterY()));
                        System.out.println(troopPos.x);
                        System.out.println(troopPos.y);
                        troop.hitbox.setPosition(troopPos.x,troopPos.y);
                    }
                }
            }
        }

//getX and getY
        private float[] getCenter() {
            return new float[] { getX() + getWidth() / 2, getY() + getHeight() / 2 };
        }

        private float getCenterX() {
            return getCenter()[0];
        }

        private float getCenterY() {
            return getCenter()[1];
        }

    }
