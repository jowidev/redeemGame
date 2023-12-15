package com.Troops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Constants;

public class GridStage extends Actor {
    private GridCell[][] gridCells;

    public GridStage(Stage s) {
        gridCells = new GridCell[9][5];
        float cellW = Gdx.graphics.getWidth() * (Constants.pixeltotile * 3.2f);
        float cellH = Gdx.graphics.getHeight() * (Constants.pixeltotile * 5.35f);
        float pX = Gdx.graphics.getWidth() * (Constants.pixeltotile * 1.51f);
        float pY =Gdx.graphics.getWidth() * (Constants.pixeltotile * 1.5f);
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 9; c++) {
                float cellX = pX + c * cellW;
                float cellY = pY + r * cellH;
                gridCells[c][r] = new GridCell(cellX, cellY, s);

            }
        }
    }
}
