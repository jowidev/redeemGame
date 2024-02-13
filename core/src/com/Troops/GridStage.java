package com.Troops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Constants;

public class GridStage extends Actor {


    public GridCell[][] gridCells;

    public GridStage(Stage s) {
        gridCells = new GridCell[9][5];
        float cellW = Gdx.graphics.getWidth() * (Constants.PIXELTOTILE * 3.21f);
        float cellH = Gdx.graphics.getHeight() * (Constants.PIXELTOTILE * 5.4f);
        float pX = Gdx.graphics.getWidth() * (Constants.PIXELTOTILE * 1.52f);
        float pY =Gdx.graphics.getWidth() * (Constants.PIXELTOTILE * 1.7f);
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 9; c++) {
                float cellX = pX + c * cellW;
                float cellY = pY + r * cellH;

                gridCells[c][r] = new GridCell(cellX,cellY,s);
            }
        }
    }
}
