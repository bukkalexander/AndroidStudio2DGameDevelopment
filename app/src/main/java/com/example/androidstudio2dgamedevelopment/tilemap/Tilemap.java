package com.example.androidstudio2dgamedevelopment.tilemap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.androidstudio2dgamedevelopment.GameDisplay;
import com.example.androidstudio2dgamedevelopment.tilemap.tile.Tile;

public class Tilemap {

    private final int pixelWidth;
    private final int pixelHeight;
    private final int nRowTiles;
    private final int nColTiles;
    private int[][] tileLayout = Level.LAYOUT0;
    private Tile[] spriteToTileMapping;
    private Tile[][] tileMap;
    private int[] pixelMap;
    private Bitmap tilemapBitmap;

    public Tilemap(Context context, int[][] tileLayout, GameDisplay gameDisplay) {
        this.tileLayout = tileLayout;
        nRowTiles = tileLayout.length;
        nColTiles = tileLayout[0].length;
        pixelHeight = nRowTiles*SpriteSheet.SPRITE_HEIGHT_PIXELS;
        pixelWidth = nColTiles*SpriteSheet.SPRITE_WIDTH_PIXELS;
        spriteToTileMapping = SpriteSheet.GetSpriteToTileMapping(context);

        initializeTileMap();
        initializePixelMap();

        tilemapBitmap = Bitmap.createBitmap(pixelMap, pixelWidth, pixelHeight, Bitmap.Config.ARGB_8888);
    }

    private void initializeTileMap() {
        tileMap = new Tile[nRowTiles][nColTiles];
        for(int iRowTiles = 0; iRowTiles < nRowTiles; iRowTiles++){
            for(int iColTiles = 0; iColTiles < nColTiles; iColTiles++){
                tileMap[iRowTiles][iColTiles] = spriteToTileMapping[tileLayout[iRowTiles][iColTiles]];
            }
        }
    }

    private void initializePixelMap() {
        int lengthPixelMap = pixelWidth*pixelHeight;
        int lengthPixelSprite = SpriteSheet.SPRITE_WIDTH_PIXELS*SpriteSheet.SPRITE_HEIGHT_PIXELS;
        pixelMap = new int[lengthPixelMap];

        int iRowTilePixels;
        int iColTilePixels;
        int iPixel;
        int[] tilePixels = new int[lengthPixelSprite];

        for (int iRowTiles = 0; iRowTiles < nRowTiles; iRowTiles++) {
            for (int iColTiles = 0; iColTiles < nColTiles; iColTiles++) {
                tileMap[iRowTiles][iColTiles].getSprite().getPixels(
                        tilePixels,
                        0,
                        SpriteSheet.SPRITE_WIDTH_PIXELS,
                        0,
                        0,
                        SpriteSheet.SPRITE_WIDTH_PIXELS,
                        SpriteSheet.SPRITE_HEIGHT_PIXELS);
                for (int i = 0; i < lengthPixelSprite; i++) {
                    iRowTilePixels = i / SpriteSheet.SPRITE_WIDTH_PIXELS;
                    iColTilePixels = i % SpriteSheet.SPRITE_HEIGHT_PIXELS;
                    iPixel = pixelWidth*(iRowTiles*SpriteSheet.SPRITE_WIDTH_PIXELS + iRowTilePixels) + iColTiles*SpriteSheet.SPRITE_HEIGHT_PIXELS + iColTilePixels;
                    pixelMap[iPixel] = tilePixels[i];
                }
            }
        }
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        // Draw tilemap
        canvas.drawBitmap(tilemapBitmap,
                gameDisplay.getDisplayRect(),
                gameDisplay.DISPLAY_RECT_ORIGIN,
                null
        );

    }
}
