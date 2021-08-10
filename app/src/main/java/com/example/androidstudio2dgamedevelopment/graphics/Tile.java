package com.example.androidstudio2dgamedevelopment.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;

abstract class Tile {

    protected Rect mapLocationRect;

    public enum TileType {
        GROUND_TILE
    }

    public Tile(Rect mapLocationRect) {
        this.mapLocationRect = mapLocationRect;
    }

    public static Tile getTile(int idxTileType, SpriteSheet spriteSheet, Rect rect) {
        switch(TileType.values()[idxTileType]) {
            case GROUND_TILE:
                return new GroundTile(spriteSheet, rect);
            default:
                return null;
        }
    }

    public abstract void draw(Canvas canvas);
}
