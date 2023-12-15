package com.Troops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Constants;

public class GridStage extends Stage {
    private GridCell[][] gridCells;

    public GridStage() {
        gridCells = new GridCell[9][5];

        float cellWidth = Gdx.graphics.getWidth() * (Constants.pixeltotile * 3.25f) / 9;
        float cellHeight = Gdx.graphics.getHeight() * (Constants.pixeltotile * 5.25f) / 5;

        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 9; c++) {
                float cellX = c * cellWidth;
                float cellY = r * cellHeight;
                gridCells[c][r] = new GridCell(cellX, cellY,this);
                addActor(gridCells[c][r]);
            }
        }
    }
}
