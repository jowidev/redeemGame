    package com.Troops;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.scenes.scene2d.Actor;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.scenes.scene2d.Touchable;
    import com.mygdx.game.Constants;

    public class GridCell extends Actor {
        public GridCell(float x, float y, Stage s) {
            float gridCellW = Gdx.graphics.getWidth() * (Constants.pixeltotile * 3.25f);
            float gridCellH = Gdx.graphics.getHeight() * (Constants.pixeltotile * 5.25f);
            setTouchable(Touchable.enabled);
            setColor(1,0,0,1);
            setBounds(x,y, gridCellW, gridCellH);
            s.addActor(this);

            setDebug(true);
        }
    }
