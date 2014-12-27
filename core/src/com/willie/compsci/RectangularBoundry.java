package com.willie.compsci;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Represents a collision boundary in the game. Rectangles only
 * @author William Reed
 *
 */
public class RectangularBoundry {
	
	/**
	 * Creates a new CollisionBoundry. Make sure box2d has been initialized before creating
	 * any boundaries
	 * @param world the world in use
	 * @param x position of the boundary (lower left)
	 * @param y position of the boundary (lower left)
	 * @param width of the boundary
	 * @param height of the boundary
	 */
	public RectangularBoundry(World world, float x, float y, float width, float height) {
		x /= WayRillie.PIXELS_TO_METERS;
		y /= WayRillie.PIXELS_TO_METERS;
		width /= WayRillie.PIXELS_TO_METERS;
		height /= WayRillie.PIXELS_TO_METERS;

		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		
		Body body = world.createBody(bodyDef);
				
		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox(width / 2, height / 2);
		
		body.createFixture(rectangle, 0.0f);
		
	}
}
