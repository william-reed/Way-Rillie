package com.willie.compsci;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Character {

    private static final int WALK_SPEED = 5;

    private Rectangle rec = new Rectangle();
    private final int MAX_HEALTH;
    private int health;
    private int currentHealth;
    private Vector2 position;
    private HashMap<KeyboardMovement, Integer> controls;
    private float speed, originalSpeed = 9.8f;
    private JumpState jumpState = JumpState.CONSTANT;

    private enum JumpState {
        ASCENDING, DESCENDING, CONSTANT;
    }

    // will be changed to TextureRegion for use with a sprite sheet
    private Texture texture;

    public Character(float x, float y, int maxHealth, Texture texture,
                    HashMap<KeyboardMovement, Integer> controls) {
        rec.height = texture.getHeight();
        rec.width = texture.getHeight();
        rec.x = x;
        rec.y = y;
        MAX_HEALTH = maxHealth;
        this.texture = texture;
        this.controls = controls;
        speed = originalSpeed;
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(getControl(KeyboardMovement.LEFT)) && canMoveLeft())
            setX(getX() - WALK_SPEED);
        if (Gdx.input.isKeyPressed(getControl(KeyboardMovement.RIGHT)) && canMoveRight())
            setX(getX() + WALK_SPEED);

        if (Gdx.input.isKeyJustPressed(getControl(KeyboardMovement.JUMP)) && canJump() && jumpState == JumpState.CONSTANT)
            jumpState = JumpState.ASCENDING;

        if (jumpState == JumpState.ASCENDING) {
            setY(getY() + speed);
            speed -= .6; // prob need to mess with this
            if (speed <= 0) {
                jumpState = JumpState.DESCENDING;
                speed = 0;
            }
        } else if (jumpState == JumpState.DESCENDING) {
            setY(getY() - speed);
            speed += .6;
            // We're descending. Increase the Y coordinate by the speed (at first, it's 0).
            if (!canFall()) {
                jumpState = JumpState.CONSTANT;
                setY(0); // TODO: if platforms are added this will need to be changed
                speed = originalSpeed;
            }
        }
    }

    public boolean canFall() {
        return (getY() >= 0);
    }

    public boolean canJump() {
        // TODO: implement
        return true;
    }

    public int getControl(KeyboardMovement control) {
        return controls.get(control);
    }

    public boolean canMoveLeft() {
        return !(rec.x <= 0);
    }

    public boolean canMoveRight() {
        return !(rec.x + rec.width >= Gdx.graphics.getWidth());
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public float getX() {
        return rec.x;
    }

    public void setX(float x) {
        rec.x = x;
    }

    public float getY() {
        return rec.y;
    }

    public void setY(float y) {
        rec.y = y;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

}
