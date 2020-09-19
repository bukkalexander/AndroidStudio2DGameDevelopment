package com.example.androidstudio2dgamedevelopment.tilemap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.androidstudio2dgamedevelopment.R;
import com.example.androidstudio2dgamedevelopment.tilemap.tile.GrassTile;
import com.example.androidstudio2dgamedevelopment.tilemap.tile.GroundTile;
import com.example.androidstudio2dgamedevelopment.tilemap.tile.LavaTile;
import com.example.androidstudio2dgamedevelopment.tilemap.tile.Tile;
import com.example.androidstudio2dgamedevelopment.tilemap.tile.WaterTile;

public class SpriteSheet {

    public static final int NUMBER_OF_SPRITE_ROWS = 10;
    public static final int NUMBER_OF_SPRITE_COLUMNS = 10;
    public static final int SPRITE_WIDTH_PIXELS = 128;
    public static final int SPRITE_HEIGHT_PIXELS = 128;

    public static Tile[] GetSpriteToTileMapping(Context context) {
        BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
        bitmapOption.inScaled = false;
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet, bitmapOption);
        Tile[] tileMapping = new Tile[NUMBER_OF_SPRITE_ROWS*NUMBER_OF_SPRITE_COLUMNS];
        tileMapping[0] = new GroundTile(getSprite(spriteSheet, 0, 0));
        tileMapping[1] = new WaterTile(getSprite(spriteSheet,0, 1));
        tileMapping[2] = new GrassTile(getSprite(spriteSheet,0, 2));
        tileMapping[3] = new LavaTile(getSprite(spriteSheet,0, 3));
        return tileMapping;
    }

    private static Bitmap getSprite(Bitmap spriteSheet, int idxRow, int idxCol) {
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
