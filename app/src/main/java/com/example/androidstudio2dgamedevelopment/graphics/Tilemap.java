package com.example.androidstudio2dgamedevelopment.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.androidstudio2dgamedevelopment.GameDisplay;

import static com.example.androidstudio2dgamedevelopment.graphics.MapLayout.NUMBER_OF_COLUMN_TILES;
import static com.example.androidstudio2dgamedevelopment.graphics.MapLayout.NUMBER_OF_ROW_TILES;
import static com.example.androidstudio2dgamedevelopment.graphics.MapLayout.TILE_HEIGHT_PIXELS;
import static com.example.androidstudio2dgamedevelopment.graphics.MapLayout.TILE_WIDTH_PIXELS;


public class Tilemap {
    private SpriteSheet spriteSheet;
    private MapLayout mapLayout;
    private Tile[][] tilemap;
    private Bitmap mapBitmap;

    public Tilemap(SpriteSheet spriteSheet) {
        mapLayout = new MapLayout();
        this.spriteSheet = spriteSheet;
        initializeTilemap();
    }

    private void initializeTilemap() {
        int[][] layout = mapLayout.getLayout();
        tilemap = new Tile[NUMBER_OF_ROW_TILES][NUMBER_OF_COLUMN_TILES];
        for (int iRow = 0; iRow < NUMBER_OF_ROW_TILES; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_COLUMN_TILES; iCol++) {
                tilemap[iRow][iCol] = Tile.getTile(
                        layout[iRow][iCol],
                        spriteSheet,
                        getRectByMapIndex(iRow, iCol));
            }
        }

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        mapBitmap = Bitmap.createBitmap(
            NUMBER_OF_COLUMN_TILES*TILE_WIDTH_PIXELS,
            NUMBER_OF_ROW_TILES*TILE_HEIGHT_PIXELS,
            config
            );

        Canvas mapCanvas = new Canvas(mapBitmap);

        for (int iRow = 0; iRow < NUMBER_OF_ROW_TILES; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_COLUMN_TILES; iCol++) {
                tilemap[iRow][iCol].draw(mapCanvas);
            }
        }

    }

    private Rect getRectByMapIndex(int idxRow, int idxCol) {
        return new Rect(
                idxCol*TILE_WIDTH_PIXELS,
                idxRow*TILE_HEIGHT_PIXELS,
                (idxCol + 1)*TILE_WIDTH_PIXELS,
                (idxRow + 1)*TILE_HEIGHT_PIXELS
        );
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawBitmap(mapBitmap, gameDisplay.getGameRect(), gameDisplay.DISPLAY_RECT, null);
    }


}
