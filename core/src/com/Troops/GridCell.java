    package com.Troops;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Input;
    import com.badlogic.gdx.scenes.scene2d.Actor;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.scenes.scene2d.Touchable;
    import com.mygdx.game.Constants;

    public class GridCell extends Actor {
        private boolean isOcc = false;
        public float gridCellW = Gdx.graphics.getWidth() * (Constants.PIXELTOTILE * 3.25f);
        public float gridCellH = Gdx.graphics.getHeight() * (Constants.PIXELTOTILE * 5.25f);
        public GridCell(Stage s) {

            setTouchable(Touchable.enabled);
            setColor(1,0,0,1);
            setBounds(64,64, gridCellW, gridCellH);
            s.addActor(this);

            setDebug(true);
            touched();
        }

        public void touched() {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)&& !isOcc) {
                System.out.println("click");
                isOcc= true;
                System.out.println(isOcc);
            } else if (isOcc){
            }
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
        public void snapToGrid(BaseTroop troop) {
            float gridX = troop.hitbox.getX() * (gridCellW / 2);
            float gridY = troop.hitbox.getY() * (gridCellH / 2);

            float nearestX = gridX / (gridCellW / 2);
            float nearestY = gridY / (gridCellH / 2);

            troop.hitbox.setPosition(getCenterX(), getCenterY());
        }
    }
