    package com.Troops;

    import com.Troops.TeamTroops.BaseTroop;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.scenes.scene2d.Actor;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.scenes.scene2d.Touchable;
    import com.badlogic.gdx.utils.viewport.Viewport;
    import com.mygdx.game.Constants;

    public class GridCell extends Actor {
        public Vector2 troopPos;
        public float gridCellW = Gdx.graphics.getWidth() * (Constants.PIXELTOTILE * 3.25f);
        public float gridCellH = Gdx.graphics.getHeight() * (Constants.PIXELTOTILE * 5.25f);
        public GridCell(float x, float y,Stage s) {

            setTouchable(Touchable.enabled);
            setColor(1,0,0,1);
            setBounds(x,y, gridCellW, gridCellH);
            s.addActor(this);
            troopPos = new Vector2(x,y);
            //setDebug(true);
        }


        public void touched(BaseTroop troop, Viewport viewport) {
            if (troop != null && troop.troopPlaced) {
                if (Gdx.input.isButtonJustPressed(0)) {
                    Vector2 mousePos = viewport.unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));
                    if (mousePos.x >= getX() && mousePos.x < getX() + gridCellW &&
                            mousePos.y >= getY() && mousePos.y < getY() + gridCellH) {
                        Vector2 troopPos = new Vector2(getCenterX()*(Constants.PIXELTOTILE/2),getCenterY()*(Constants.PIXELTOTILE/2));
                        troop.hitbox.setPosition(troopPos.x-1, troopPos.y-1);
                    } //no lo puedo creer que funciona
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
