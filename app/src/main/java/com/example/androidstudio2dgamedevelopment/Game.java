package com.example.androidstudio2dgamedevelopment;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.androidstudio2dgamedevelopment.gameobject.Circle;
import com.example.androidstudio2dgamedevelopment.gameobject.Enemy;
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
    private int movementJoystickPointerId = -1;
    private int directionJoystickPointerId = -1;
    private final Joystick movementJoystick;
    private final Joystick directionJoystick;
    private final Player player;
    private GameLoop gameLoop;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Spell> spellList = new ArrayList<Spell>();
    private int numberOfSpellsToCast = 0;
    private GameOver gameOver;
    private Performance performance;

    public Game(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        // Initialize game panels
        performance = new Performance(context, gameLoop);
        gameOver = new GameOver(context);
        movementJoystick = new Joystick(275, 700, 70, 40);
        directionJoystick = new Joystick(1920 - 275, 700, 70, 40);

        // Initialize game objects
        player = new Player(context, movementJoystick, 2*500, 500, 30);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Handle user input touch event actions
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        int pointerIdIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        int pointerId = event.getPointerId(pointerIdIndex);
        float x = event.getX(pointerIdIndex);
        float y = event.getY(pointerIdIndex);
        int pointerCount = event.getPointerCount();

        Log.d("Game::onTouchEvent","onTouchEvent: action=" + action + ", pointerIdIndex=" +
                pointerIdIndex + ", pointerId="+ pointerId + ", x=" + x + ", y=" + y);
        switch (actionCode) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            //case MotionEvent.ACTION_MOVE:
                if (movementJoystick.isPressed(x, y)) {
                    /* movementJoystick is pressed in this event ->
                           store pointer id and setIsPressed(true) */
                    movementJoystickPointerId = pointerId;
                    movementJoystick.setIsPressed(true);
                } else if (directionJoystick.isPressed(x, y)) {
                    /* directionJoystick is pressed in this event ->
                           store pointer id and setIsPressed(true) */
                    directionJoystickPointerId = pointerId;
                    directionJoystick.setIsPressed(true);
                } else {
                    /* No joystick was pressed -> cast spell */
                    numberOfSpellsToCast ++;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (movementJoystickPointerId == pointerId) {
                    // joystick pointer was let go off -> setIsPressed(false) and resetActuator()
                    movementJoystick.setIsPressed(false);
                    movementJoystick.resetActuator();
                    movementJoystickPointerId = -1;
                } else if (directionJoystickPointerId == pointerId) {
                    // joystick pointer was let go off -> setIsPressed(false) and resetActuator()
                    directionJoystick.setIsPressed(false);
                    directionJoystick.resetActuator();
                    directionJoystickPointerId = -1;
                }
                break;
        }

        for (int iPointerIndex = 0; iPointerIndex < pointerCount; iPointerIndex++) {
            if (movementJoystick.getIsPressed() && event.getPointerId(iPointerIndex) == movementJoystickPointerId) {
                // movementJoystick was pressed previously and is now moved
                movementJoystick.setActuator(event.getX(iPointerIndex), event.getY(iPointerIndex));
            } else if (directionJoystick.getIsPressed() && event.getPointerId(iPointerIndex) == directionJoystickPointerId) {
                // directionJoystick was pressed previously and is now moved
                directionJoystick.setActuator(event.getX(iPointerIndex), event.getY(iPointerIndex));
            }
        }

        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, holder);
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
        player.draw(canvas);

        for (Enemy enemy : enemyList) {
            enemy.draw(canvas);
        }

        for (Spell spell : spellList) {
            spell.draw(canvas);
        }

        // Draw game panels
        movementJoystick.draw(canvas);
        directionJoystick.draw(canvas);
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
        movementJoystick.update();
        directionJoystick.update();
        player.update();

        if(Enemy.readyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
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
        }
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
