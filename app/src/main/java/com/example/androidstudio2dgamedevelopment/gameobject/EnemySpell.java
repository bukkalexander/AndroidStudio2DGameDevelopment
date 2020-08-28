package com.example.androidstudio2dgamedevelopment.gameobject;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.androidstudio2dgamedevelopment.GameLoop;
import com.example.androidstudio2dgamedevelopment.R;

public class EnemySpell extends Circle {
    public static final double SPEED_PIXELS_PER_SECOND = 800.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    public EnemySpell(Context context, GameObject spellcaster) {
        super(
                context,
                ContextCompat.getColor(context, R.color.enemySpell),
                spellcaster.getPositionX(),
                spellcaster.getPositionY(),
                15
        );
        velocityX = spellcaster.getDirectionX()*MAX_SPEED;
        velocityY = spellcaster.getDirectionY()*MAX_SPEED;
    }

    @Override
    public void update() {
        positionX = positionX + velocityX + (Math.random()-0.5)*40;
        positionY = positionY + velocityY + (Math.random()-0.5)*10;
    }
}