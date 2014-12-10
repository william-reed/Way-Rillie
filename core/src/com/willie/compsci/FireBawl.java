package com.willie.compsci;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class FireBawl
{
//	false is left, true is right
	private boolean direction;
	private Vector2 origin;
	private Texture texture;

	public FireBawl(boolean direction, Vector2 origin)
	{
		this.direction = direction;
		this.origin = origin;
		texture = new Texture("fireBawl.png");
	}
	
	public void draw(SpriteBatch batch)
	{
		batch.draw(texture, origin.x, origin.y);
	}
}
