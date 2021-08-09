package com.example.androidstudio2dgamedevelopment.graphics;

import android.graphics.Canvas;

import com.example.androidstudio2dgamedevelopment.GameDisplay;
import com.example.androidstudio2dgamedevelopment.gameobject.Player;
import com.example.androidstudio2dgamedevelopment.gameobject.PlayerState;


public class Animator {

    private Sprite[] spriteArray;
    private int updatesBeforeNextMoveFrame;
    private int idxMoveFrame = 1;
    private int idxNotMoveFrame = 0;

    public Animator(Sprite[] spriteArray) {
        this.spriteArray = spriteArray;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Player player) {
        switch (player.getPlayerState().getState()) {
            case NOT_MOVING:

                drawFrame(canvas, gameDisplay, player, spriteArray[idxNotMoveFrame]);
                break;
            case STARTED_MOVING: // Started moving
                updatesBeforeNextMoveFrame = 5;
                drawFrame(canvas, gameDisplay, player, spriteArray[idxMoveFrame]);
            case IS_MOVING: // Is moving
                updatesBeforeNextMoveFrame--;
                if (updatesBeforeNextMoveFrame == 0) {
                    updatesBeforeNextMoveFrame = 5;
                    toggleIdxMoveFrame();
                }
                drawFrame(canvas, gameDisplay, player, spriteArray[idxMoveFrame]);
            default:
                break;
        }
    }

    private void drawFrame(Canvas canvas, GameDisplay gameDisplay, Player player, Sprite sprite) {

        sprite.draw(
                canvas,
                (int) gameDisplay.gameToDisplayCoordinatesX(player.getPositionX()) -
                        sprite.getWidth()/2,
                (int) gameDisplay.gameToDisplayCoordinatesY(player.getPositionY()) -
                        sprite.getHeight()/2
        );

    }

    private void toggleIdxMoveFrame() {
        if (idxMoveFrame == 1)
            idxMoveFrame = 2;
        else
            idxMoveFrame = 1;
    }
}
