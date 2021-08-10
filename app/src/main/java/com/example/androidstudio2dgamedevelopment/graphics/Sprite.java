package com.example.androidstudio2dgamedevelopment.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

    private final SpriteSheet spriteSheet;
    private final Rect srcRect; // Which area of the sprite sheet bitmap to get the sprite from
    private Rect dstRect; // Where to draw the sprite on the canvas

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.srcRect = rect;
        this.dstRect = new Rect();
    }

    public void draw(Canvas canvas, int x, int y) {
        dstRect.set(x, y, x+getWidth(), y+getHeight());
        canvas.drawBitmap(
            spriteSheet.getBitmap(),
                srcRect,
                dstRect,
                null
        );
    }

    public int getWidth() {
        return srcRect.width();
    }

    public int getHeight() {
        return srcRect.height();
    }


}
