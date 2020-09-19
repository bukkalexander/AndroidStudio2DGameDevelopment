package com.example.androidstudio2dgamedevelopment.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.androidstudio2dgamedevelopment.GameDisplay;
import com.example.androidstudio2dgamedevelopment.R;
import com.example.androidstudio2dgamedevelopment.map.tile.GrassTile;
import com.example.androidstudio2dgamedevelopment.map.tile.GroundTile;
import com.example.androidstudio2dgamedevelopment.map.tile.LavaTile;
import com.example.androidstudio2dgamedevelopment.map.tile.Tile;
import com.example.androidstudio2dgamedevelopment.map.tile.WaterTile;

public class Map {

    public static final int NUMBER_OF_SPRITE_ROWS = 10;
    public static final int NUMBER_OF_SPRITE_COLUMNS = 10;
    public static final int SPRITE_WIDTH_PIXELS = 128;
    public static final int SPRITE_HEIGHT_PIXELS = 128;

    private Bitmap spriteSheet;
    private Tile[][] spriteToTileMapping;
    private int[][] tileLayout = Level.LAYOUT0;
    private Tile[][]
    private int[] pixelLayout

    public Map(Context context, int[][] tileLayout) {
        spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet);
        initializeSpriteToTileMapping();
        this.tileLayout = tileLayout;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        // Draw all tiles
        Bitmap bitmap = Bitmap.createBitmap(gameDisplay.WIDTH_PIXELS, gameDisplay.HEIGHT_PIXELS, Bitmap.Config.ARGB_8888);
        canvas.drawBitmap();
    }



    public void initializeSpriteToTileMapping() {
        spriteToTileMapping = new Tile[NUMBER_OF_SPRITE_ROWS][NUMBER_OF_SPRITE_COLUMNS];
        spriteToTileMapping[0][0] = new GroundTile(getSprite(0, 0));
        spriteToTileMapping[0][1] = new GrassTile(getSprite(0, 1));
        spriteToTileMapping[0][2] = new WaterTile(getSprite(0, 2));
        spriteToTileMapping[0][3] = new LavaTile(getSprite(0, 3));
    }

    private Bitmap getSprite(int idxRow, int idxCol) {
        Bitmap sprite = Bitmap.createBitmap(
                spriteSheet,
                idxCol*SPRITE_WIDTH_PIXELS,
                idxRow*SPRITE_HEIGHT_PIXELS,
                SPRITE_WIDTH_PIXELS,
                SPRITE_HEIGHT_PIXELS
        );
        return sprite;
    }
}
