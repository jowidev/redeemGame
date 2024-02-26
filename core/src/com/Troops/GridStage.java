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
                int id = r * 9 + c;
                boolean boulderCell = id % 9 == 6 ||id % 9 == 7 || id % 9 == 8;
                gridCells[c][r] = new GridCell( id, cellX,cellY,s, boulderCell);
            }
        }
    }

    public GridCell getCellById(int id) {
        int r = id / 9;
        int c = id % 9;
        return gridCells[c][r];
    }

    public GridCell getCellByCoords(float x, float y) {
        // Iterate through the gridCells array
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 9; c++) {
                GridCell cell = gridCells[c][r];

                // Check if the coordinates are within the bounds of the cell
                if (x >= cell.getX() && x < cell.getX() + cell.getWidth() &&
                        y >= cell.getY() && y < cell.getY() + cell.getHeight()) {
                    return cell;
                } //si hace click adentro de una cell devuelve la cell correspondiente
            }
        }

        return null; // No cell found at the specified coordinates
    }


}
