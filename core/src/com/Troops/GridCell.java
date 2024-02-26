    package com.Troops;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.scenes.scene2d.Actor;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.scenes.scene2d.Touchable;
    import com.badlogic.gdx.utils.viewport.Viewport;
    import com.mygdx.game.Constants;

    public class GridCell extends Actor {

        private int id;
        private boolean boulderCell;
        public float gridCellW = Gdx.graphics.getWidth() * (Constants.PIXELTOTILE * 3.25f);
        public float gridCellH = Gdx.graphics.getHeight() * (Constants.PIXELTOTILE * 5.25f);
        public GridCell(int id, float x, float y,Stage s, boolean boulderCell) {
            setTouchable(Touchable.enabled);
            setBounds(x,y, gridCellW, gridCellH);
            s.addActor(this);
            this.id = id;
            this.boulderCell = boulderCell;
            //setDebug(true);
        }

        public boolean placeTroop(BaseTroop troop){
            if(troop == null || (troop instanceof Boulder != this.boulderCell)) return false;
            Vector2 troopPos = new Vector2(getCenterX()*(Constants.PIXELTOTILE/2),getCenterY()*(Constants.PIXELTOTILE/2));
            troop.hitbox.set(troopPos.x - 1, troopPos.y - 1, troop.TROOP_WIDTH, troop.TROOP_HEIGHT);
            troop.hitbox.setPosition(troopPos.x-1, troopPos.y-1);
            troop.setLocked(true);
            troop.troopPlaced = true;
            //no lo puedo creer que funciona
            return true;
        }
        private float[] getCenter() {
            return new float[] { getX() + getWidth() / 2, getY() + getHeight() / 2 };
        }

        private float getCenterX() {
            return getCenter()[0];
        }

        private float getCenterY() {
            return getCenter()[1];
        }


        public int getId() {
            return id;
        }

        public boolean isBoulderCell() {
            return boulderCell;
        }
    }
