package com.example.androidstudio2dgamedevelopment;

import com.example.androidstudio2dgamedevelopment.gameobject.GameObject;

public class GameDisplay {
    public final int WIDTH_PIXELS;
    public final int HEIGHT_PIXELS;
    private final GameObject centerObject;
    private final double displayCenterX;
    private final double displayCenterY;
    private double gameToDisplayCoordinatesOffsetX;
    private double gameToDisplayCoordinatesOffsetY;
    private double gameCenterX;
    private double gameCenterY;

    public GameDisplay(int widthPixels, int heightPixels, GameObject centerObject) {
        this.WIDTH_PIXELS = widthPixels;
        this.HEIGHT_PIXELS = heightPixels;
        this.centerObject = centerObject;

        displayCenterX = widthPixels/2.0;
        displayCenterY = heightPixels/2.0;

        update();
    }

    public void update() {
        gameCenterX = centerObject.getPositionX();
        gameCenterY = centerObject.getPositionY();

        gameToDisplayCoordinatesOffsetX = displayCenterX - gameCenterX;
        gameToDisplayCoordinatesOffsetY = displayCenterY - gameCenterY;
    }

    public double gameToDisplayCoordinatesX(double x) {
        return x + gameToDisplayCoordinatesOffsetX;
    }

    public double gameToDisplayCoordinatesY(double y) {
        return y + gameToDisplayCoordinatesOffsetY;
    }

}
