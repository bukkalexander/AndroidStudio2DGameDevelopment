package com.example.androidstudio2dgamedevelopment.tilemap.tile;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.androidstudio2dgamedevelopment.GameDisplay;

public abstract class Tile {

    private Bitmap sprite;

    public Tile(Bitmap sprite) {
        this.sprite = sprite;
    }

    public Bitmap getSprite() {
        return sprite;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
    }
}
