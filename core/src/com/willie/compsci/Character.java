package com.willie.compsci;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Character
{
	private Rectangle rec;
	private final int MAX_HEALTH;
	private int currentHealth;
	private TextureRegion texture;
	
	public Character(float height, float width, float x, float y, int maxHealth, TextureRegion texture)
	{
		rec.height = height;
		rec.width = width;
		rec.x = x;
		rec.y = y;
		MAX_HEALTH = maxHealth;
		this.texture = texture;
	}
	
	
}
