package com.example.androidstudio2dgamedevelopment.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.androidstudio2dgamedevelopment.R;

public class SpriteSheet {
    public static final int SPRITE_WIDTH_PIXELS = 64;
    public static final int SPRITE_HEIGHT_PIXELS = 64;
    public static final int NUMBER_OF_ROWS = 5;
    public static final int NUMBER_OF_COLUMNS = 5;

    private Bitmap bitmap;

    public SpriteSheet(Context context) throws Exception {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet, bitmapOptions);

        if (bitmap.getWidth() != SPRITE_WIDTH_PIXELS*NUMBER_OF_COLUMNS) {
            throw new Exception("Image width needs to be " + SPRITE_WIDTH_PIXELS*NUMBER_OF_COLUMNS + "px");
        } else if (bitmap.getHeight() != SPRITE_HEIGHT_PIXELS*NUMBER_OF_ROWS) {
            throw new Exception("Image height need to be: " + SPRITE_HEIGHT_PIXELS*NUMBER_OF_ROWS + "px");
        }
    }

    public Sprite getGroundSprite() {
        return getSpriteByIndex(3, 2);
    }

    private Sprite getSpriteByIndex(int idxRow, int idxCol) {
        if (idxRow < 0 || idxRow > NUMBER_OF_ROWS)
            throw new IndexOutOfBoundsException("idxRow must be greater than 0 and less than" + NUMBER_OF_ROWS);
        else if (idxCol < 0 || idxCol > NUMBER_OF_COLUMNS)
            throw new IndexOutOfBoundsException("idxCol must be greater than 0 and less than" + NUMBER_OF_COLUMNS);

        return new Sprite(this, new Rect(
            idxCol*SPRITE_WIDTH_PIXELS,
            idxRow*SPRITE_HEIGHT_PIXELS,
            (idxCol + 1)*SPRITE_WIDTH_PIXELS,
            (idxRow + 1)*SPRITE_HEIGHT_PIXELS
        ));
    }

    public Sprite[] getPlayerSpriteArray() {
        return new Sprite[] {
            getSpriteByIndex(0, 0),
            getSpriteByIndex(0, 1),
            getSpriteByIndex(0, 2),
        };
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
