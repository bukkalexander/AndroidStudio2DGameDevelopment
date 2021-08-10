package com.example.androidstudio2dgamedevelopment.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;

public class GroundTile extends Tile {
    private Sprite sprite;

    public GroundTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getGroundSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
