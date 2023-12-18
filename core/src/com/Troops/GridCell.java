    package com.Troops;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.scenes.scene2d.Actor;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.scenes.scene2d.Touchable;
    import com.mygdx.game.Constants;

    public class GridCell extends Actor {
        public float gridCellW = Gdx.graphics.getWidth() * (Constants.pixeltotile * 3.25f);
        public float gridCellH = Gdx.graphics.getHeight() * (Constants.pixeltotile * 5.25f);
        public GridCell(float x, float y, Stage s) {
            //gridCellW = Gdx.graphics.getWidth() * (Constants.pixeltotile * 3.25f);
            //gridCellH = Gdx.graphics.getHeight() * (Constants.pixeltotile * 5.25f);
            setTouchable(Touchable.enabled);
            setColor(1,0,0,1);
            setBounds(x,y, gridCellW, gridCellH);
            s.addActor(this);

            setDebug(true);
        }
        public void snapToGrid(BaseTroop troop) {
            // Calculate the grid position of the troop
            float gridX = (int)(troop.hitbox.getX() / gridCellW);
            float gridY = (int)(troop.hitbox.getY() / gridCellH);

            // Calculate the nearest grid cell to the troop
            int nearestX = (int)(gridX + 0.5f);
            int nearestY = (int)(gridY + 0.5f);

            // Set the troop's position to the nearest grid cell
            troop.hitbox.setPosition(nearestX * gridCellW, nearestY * gridCellH);
        }
    }
