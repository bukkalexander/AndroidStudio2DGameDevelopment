package com.example.androidstudio2dgamedevelopment.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.androidstudio2dgamedevelopment.R;
import com.example.androidstudio2dgamedevelopment.map.tile.GrassTile;
import com.example.androidstudio2dgamedevelopment.map.tile.GroundTile;
import com.example.androidstudio2dgamedevelopment.map.tile.LavaTile;
import com.example.androidstudio2dgamedevelopment.map.tile.Tile;
import com.example.androidstudio2dgamedevelopment.map.tile.WaterTile;

public class SpriteSheet {

    public static final int NUMBER_OF_SPRITE_ROWS = 10;
    public static final int NUMBER_OF_SPRITE_COLUMNS = 10;
    public static final int SPRITE_WIDTH_PIXELS = 128;
    public static final int SPRITE_HEIGHT_PIXELS = 128;

    public Bitmap spriteSheet;

    public SpriteSheet(Context context) {
        spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet);
    }

    public Tile[][] GetSpriteSheetToTileMapping() {
        Tile[][] tileMapping = new Tile[NUMBER_OF_SPRITE_ROWS][NUMBER_OF_SPRITE_COLUMNS];
        tileMapping[0][0] = new GroundTile(getSprite(0, 0));
        tileMapping[0][1] = new GrassTile(getSprite(0, 1));
        tileMapping[0][2] = new WaterTile(getSprite(0, 2));
        tileMapping[0][3] = new LavaTile(getSprite(0, 3));
        return tileMapping;
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
