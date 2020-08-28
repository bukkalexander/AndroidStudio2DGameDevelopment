package com.example.androidstudio2dgamedevelopment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.androidstudio2dgamedevelopment.gameobject.Circle;
import com.example.androidstudio2dgamedevelopment.gameobject.Enemy;
import com.example.androidstudio2dgamedevelopment.gameobject.EnemySpell;
import com.example.androidstudio2dgamedevelopment.gameobject.EnemySpell2;
import com.example.androidstudio2dgamedevelopment.gameobject.Player;
import com.example.androidstudio2dgamedevelopment.gameobject.Spell;
import com.example.androidstudio2dgamedevelopment.gamepanel.GameOver;
import com.example.androidstudio2dgamedevelopment.gamepanel.Joystick;
import com.example.androidstudio2dgamedevelopment.gamepanel.Performance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Game manages all objects in the game and is responsible for updating all states and render all
 * objects to the screen
 */
class Game extends SurfaceView implements SurfaceHolder.Callback {

    private int joystickPointerId = 0;
    private final Joystick joystick;
    private final Player player;
    private GameLoop gameLoop;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Spell> spellList = new ArrayList<Spell>();
    private List<EnemySpell> enemySpellList = new ArrayList<>();
    private List<EnemySpell2> enemySpellList2 = new ArrayList<>();
    private int numberOfSpellsToCast = 0;
    private GameOver gameOver;
    private Performance performance;
    private GameDisplay gameDisplay;

    public Game(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        // Initialize game panels
        performance = new Performance(context, gameLoop);
        gameOver = new GameOver(context);
        joystick = new Joystick(275, 700, 70, 40);

        // Initialize game objects
        player = new Player(context, joystick, 2*500, 500, 30);

        // Initialize display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Handle user input touch event actions
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (joystick.getIsPressed()) {
                    // Joystick was pressed before this event -> cast spell
                    numberOfSpellsToCast ++;
                } else if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    // Joystick is pressed in this event -> setIsPressed(true) and store pointer id
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                } else {
                    // Joystick was not previously, and is not pressed in this event -> cast spell
                    numberOfSpellsToCast ++;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.getIsPressed()) {
                    // Joystick was pressed previously and is now moved
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    // joystick pointer was let go off -> setIsPressed(false) and resetActuator()
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            gameLoop = new GameLoop(this, surfaceHolder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("Game.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceDestroyed()");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Draw game objects
        player.draw(canvas, gameDisplay);

        for (Enemy enemy : enemyList) {
            enemy.draw(canvas, gameDisplay);
        }

        for (Spell spell : spellList) {
            spell.draw(canvas, gameDisplay);
        }

        for (EnemySpell spell : enemySpellList) {
            spell.draw(canvas, gameDisplay);
        }

        for (EnemySpell2 spell : enemySpellList2) {
            spell.draw(canvas, gameDisplay);
        }

        // Draw game panels
        joystick.draw(canvas);
        performance.draw(canvas);

        // Draw Game over if the player is dead
        if (player.getHealthPoint() <= 0) {
            gameOver.draw(canvas);
        }
    }

    public void update() {
        // Stop updating the game if the player is dead
        if (player.getHealthPoint() <= 0) {
            return;
        }

        // Update game state
        joystick.update();
        player.update();

        // Spawn enemy
        if(Enemy.readyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
        }

        // Enemies cast spells
        Iterator<Enemy> iteratorEnemy0 = enemyList.iterator();
        while (iteratorEnemy0.hasNext()) {
            Enemy enemy = iteratorEnemy0.next();
            if (enemy.readyToCastSpell()) {
                enemySpellList.add(new EnemySpell(getContext(), enemy));
            }

            if (enemy.readyToCastSpell2()) {
                enemySpellList2.add(new EnemySpell2(getContext(), enemy));
            }
        }

        // Update states of all enemies
        for (Enemy enemy : enemyList) {
            enemy.update();
        }

        // Update states of all spells
        while (numberOfSpellsToCast > 0) {
            spellList.add(new Spell(getContext(), player));
            numberOfSpellsToCast --;
        }
        for (Spell spell : spellList) {
            spell.update();
        }

        for (EnemySpell spell : enemySpellList) {
            spell.update();
        }

        for (EnemySpell2 spell : enemySpellList2) {
            spell.update();
        }

        // Iterate through enemyList and Check for collision between each enemy and the player and
        // spells in spellList.
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()) {
            Circle enemy = iteratorEnemy.next();
            if (Circle.isColliding(enemy, player)) {
                // Remove enemy if it collides with the player
                iteratorEnemy.remove();
                player.setHealthPoint(player.getHealthPoint() - 1);
                continue;
            }

            Iterator<Spell> iteratorSpell = spellList.iterator();
            while (iteratorSpell.hasNext()) {
                Circle spell = iteratorSpell.next();
                // Remove enemy if it collides with a spell
                if (Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    break;
                }
            }

            Iterator<EnemySpell> iteratorEnemySpell = enemySpellList.iterator();
            while (iteratorEnemySpell.hasNext()) {
                Circle spell = iteratorEnemySpell.next();
                // Deduct players health if it collides with a spell
                if (Circle.isColliding(spell, player)) {
                    iteratorEnemySpell.remove();
                    player.setHealthPoint(player.getHealthPoint() - 1);
                    break;
                }
            }

            Iterator<EnemySpell2> iteratorEnemySpell2 = enemySpellList2.iterator();
            while (iteratorEnemySpell2.hasNext()) {
                Circle spell = iteratorEnemySpell2.next();
                // Deduct players health if it collides with a spell
                if (Circle.isColliding(spell, player)) {
                    iteratorEnemySpell2.remove();
                    player.setHealthPoint(player.getHealthPoint() - 1);
                    break;
                }
            }


        }
        /*
        Iterator<Enemy> iteratorEnemy2 = enemyList.iterator();
        while (iteratorEnemy2.hasNext()) {
            Iterator<Spell> iteratorEnemySpell = spellList.iterator();
            while (iteratorEnemySpell.hasNext()) {
                Circle spell = iteratorSpell.next();
                // Deduct players health if it collides with a spell
                if (Circle.isColliding(spell, player)) {
                    iteratorSpell.remove();
                    player.setHealthPoint(player.getHealthPoint() - 1);
                    break;
                }
            }
        }
        */
        // Update gameDisplay so that it's center is set to the new center of the player's 
        // game coordinates
        gameDisplay.update();
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
