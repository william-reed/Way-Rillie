package com.willie.compsci;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Character {
	private Rectangle rec = new Rectangle();
	private final int MAX_HEALTH;
	private int health;
	private int currentHealth;
	private HashMap<KeyboardMovement, Integer> controls;

	// will be changed to TextureRegion for use with a sprite sheet
	private Texture texture;

	public Character(float height, float width, float x, float y,
			int maxHealth, Texture texture,
			HashMap<KeyboardMovement, Integer> controls) {
		rec.height = height;
		rec.width = width;
		rec.x = x;
		rec.y = y;
		MAX_HEALTH = maxHealth;
		this.texture = texture;
		this.controls = controls;
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

}
