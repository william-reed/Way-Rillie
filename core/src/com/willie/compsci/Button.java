package com.willie.compsci;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents a textured button
 * @author William Reed
 * @since 11/28/13 11:30 A.M.
 */
public class Button {
	
	private Texture texture;
	private Vector2 location;
	private int width, height;
	
	/**
	 * Defines a new Button
	 * @param texture Texture to be drawn as this button
	 * @param location The bottom left coordinates of the button
	 */
	public Button(Texture texture, Vector2 location){
		this.texture = texture;
		this.location = location;
		
		width = texture.getWidth();
		height = texture.getHeight();
	}
	
	/**
	 * Checks if the mouse / finger is in the 
	 * @return true if the Button is clicked
	 */
	public boolean isClicked(){
		if(Gdx.input.isTouched()){ //do a quick refining
			int inputY  = Gdx.graphics.getHeight() - Gdx.input.getY();
			if(Gdx.input.getX() >  location.x && Gdx.input.getX() < location.x + width &&
					inputY > location.y && inputY < location.y + height){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Draws the button at its location. 
	 * @param batch SpriteBatch to be used to draw this.
	 */
	public void draw(SpriteBatch batch){
		if(!isClicked()){
			batch.draw(texture, location.x, location.y);
		}
	}

	/**
	 * Texture associated with this Button
	 * @return the texture associated with this Button
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * Sets the texture involved with this Button
	 * @param texture Texture to be set to
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	/**
	 * Location of the Button
	 * @return Vector2 of the bottom left coordinates of the button
	 */
	public Vector2 getLocation() {
		return location;
	}
	
	/**
	 * Sets the location of the button
	 * @param location Coordinates of the button to be set to
	 */
	public void setLocation(Vector2 location) {
		this.location = location;
	}

	/**
	 * Gets the width of the button clickable region
	 * @return the width of the button
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width of the button clickable region
	 * @param width Width to set the button to
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Gets the height of the button clickable region
	 * @return the height of the Button 
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of the button clickable region
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Disposes of this button and its assets
	 */
	public void dispose() {
		texture.dispose();
	}
	
}
