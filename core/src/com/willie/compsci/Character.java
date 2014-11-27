package com.willie.compsci;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Character implements ContactListener
{

	private final int MAX_HEALTH;
	private int health;
	private int currentHealth;
	private Vector2 position;
	private CharacterController characterController;
	private BodyDef bodyDef;
	private Body body;
	private World world;
	private final float PIXELS_TO_METERS = 100f;
	private boolean canJump;

	// will be changed to TextureRegion for use with a sprite sheet
	private Texture texture;

	public Character(float x, float y, int maxHealth, Texture texture, CharacterController characterController, World world)
	{
		MAX_HEALTH = maxHealth;
		this.texture = texture;
		this.characterController = characterController;
		position = new Vector2(x / PIXELS_TO_METERS, y / PIXELS_TO_METERS);
		this.world = world;

		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);
		bodyDef.fixedRotation = true;
		body = world.createBody(bodyDef);

		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox((texture.getWidth() / 2) / PIXELS_TO_METERS, (texture.getHeight() / 2) / PIXELS_TO_METERS);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectangle;
		fixtureDef.density = 0.75f;
		fixtureDef.friction = 1.5f;
		fixtureDef.restitution = 0;

		body.createFixture(fixtureDef);

		canJump = true;
		world.setContactListener(this);
	}

	// TODO: You slide down walls (don't stick to them)
	// TODO: Collition detection

	private final float MAX_VELOCITY = 3;
	private final float ACCELERATION = 0.15f;

	public void update(float delta)
	{
		if (Gdx.input.isKeyPressed(characterController.getLeftKey()) && canMoveLeft() && body.getLinearVelocity().x > -MAX_VELOCITY)
			body.applyLinearImpulse(-ACCELERATION, 0, body.getPosition().x, body.getPosition().y, true);

		if (Gdx.input.isKeyPressed(characterController.getRightKey()) && canMoveRight() && body.getLinearVelocity().x < MAX_VELOCITY)
			body.applyLinearImpulse(ACCELERATION, 0, body.getPosition().x, body.getPosition().y, true);

		if (Gdx.input.isKeyJustPressed(characterController.getJumpKey()) && canJump)
		{
			body.applyForceToCenter(new Vector2(0.0f, 100f), true);
//			canJump = false;
//			System.out.println("can jump is false");
		}
	}

	public void draw(SpriteBatch batch)
	{
		batch.draw(texture, body.getPosition().x * PIXELS_TO_METERS - texture.getWidth() / 2, body.getPosition().y * PIXELS_TO_METERS - texture.getHeight() / 2);
	}

	public boolean canFall()
	{
		return (getY() >= 0);
	}

	//
	public boolean canJump()
	{
		// TODO: implement
		return true;
	}

	public boolean canMoveLeft()
	{
		return !(position.x <= 0);
	}

	public boolean canMoveRight()
	{
		return !(position.x + texture.getWidth() >= Gdx.graphics.getWidth());
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
		return position.x;
	}

	public void setX(float x)
	{
		position.x = x;
	}

	public float getY()
	{
		return position.y;
	}

	public void setY(float y)
	{
		position.y = y;
	}

	public Texture getTexture()
	{
		return texture;
	}

	public int getHealth()
	{
		return health;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public int getMaxHealth()
	{
		return MAX_HEALTH;
	}

	public Vector2 getPosition()
	{
		return body.getPosition();
	}

	public void setPosition(Vector2 position)
	{
		body.getPosition().set(position);
	}

    @Override
    public void beginContact(Contact contact) {
        canJump = true;
    }

    @Override
    public void endContact(Contact contact) {
        canJump = false;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}