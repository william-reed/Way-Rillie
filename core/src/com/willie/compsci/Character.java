package com.willie.compsci;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Character
{
	private Rectangle rec = new Rectangle();
	private final int MAX_HEALTH;
	private int currentHealth;
	private HashMap<String, Integer> controls;
	
	//will be changed to TextureRegion for use with a sprite sheet
	private Texture texture;
	
	public Character(float height, float width, float x, float y, int maxHealth, Texture texture, HashMap<String, Integer> controls)
	{
		rec.height = height;
		rec.width = width;
		rec.x = x;
		rec.y = y;
		MAX_HEALTH = maxHealth;
		this.texture = texture;
		this.controls = controls;
	}
	
	public int getControl(String control)
	{
		return controls.get(control);
	}
	
	public boolean canMoveLeft()
	{
		if (rec.x <= 0)
			return false;
		return true;
	}
	
	public boolean canMoveRight()
	{
		if (rec.x + rec.width >= Gdx.graphics.getWidth())
			return false;
		return true;
	}

	public int getCurrentHealth()
	{
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth)
	{
		this.currentHealth = currentHealth;
	}
	
	public float getX()
	{
		return rec.x;
	}
	
	public void setX(float x)
	{
		rec.x = x;
	}
	
	public float getY()
	{
		return rec.y;
	}
	
	public void setY(float y)
	{
		rec.y = y;
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
}
