package com.willie.compsci;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Character
{

	private final int MAX_HEALTH;

	private static final int FRAME_COLS = 6;
	private static final int FRAME_ROWS = 4;

	private static final int SPRITE_WIDTH = 64;
	private static final int SPRITE_HEIGHT = 64;

	Animation idleAniLeft, idleAniRight, jumpAniLeft, jumpAniRight;
	Texture spriteSheet;
	TextureRegion[] idleFramesLeft, idleFramesRight, jumpFramesLeft, jumpFramesRight;
	TextureRegion currentFrame;
	private static float ANIMATION_SPEED;
	private int aniState;

	private int currentHealth;
	private Vector2 position;
	private CharacterController characterController;
	private BodyDef bodyDef;
	private Body body;
	private World world;
	private final float PIXELS_TO_METERS = 100f;
	private boolean canJump = false;
	private Fixture fixture;

	public Character(float x, float y, int maxHealth, Texture spriteSheet, CharacterController characterController, World world)
	{
		MAX_HEALTH = maxHealth;
		currentHealth = MAX_HEALTH;
		this.characterController = characterController;
		position = new Vector2(x / PIXELS_TO_METERS, y / PIXELS_TO_METERS);
		this.world = world;

		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);
		bodyDef.fixedRotation = true;
		body = world.createBody(bodyDef);

		PolygonShape rectangle = new PolygonShape();
		rectangle.setAsBox((SPRITE_WIDTH / 2) / PIXELS_TO_METERS, (SPRITE_HEIGHT / 2) / PIXELS_TO_METERS);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectangle;
		fixtureDef.density = 0.75f;
		fixtureDef.friction = 1.5f;
		fixtureDef.restitution = 0;

		fixture = body.createFixture(fixtureDef);

		this.spriteSheet = spriteSheet;

		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / FRAME_COLS, spriteSheet.getHeight() / FRAME_ROWS);
		idleFramesLeft = new TextureRegion[FRAME_COLS];
		idleFramesRight = new TextureRegion[FRAME_COLS];
		jumpFramesLeft = new TextureRegion[FRAME_COLS];
		jumpFramesRight = new TextureRegion[FRAME_COLS];

		ANIMATION_SPEED = 0.15f;

		for (int i = 0; i < FRAME_COLS; i++)
		{
			idleFramesLeft[i] = tmp[0][i];
			idleFramesRight[i] = tmp[1][i];
			jumpFramesLeft[i] = tmp[2][i];
			jumpFramesRight[i] = tmp[3][i];
		}

		idleAniLeft = new Animation(ANIMATION_SPEED, idleFramesLeft);
		idleAniRight = new Animation(ANIMATION_SPEED, idleFramesRight);
		jumpAniLeft = new Animation(ANIMATION_SPEED, jumpFramesLeft);
		jumpAniRight = new Animation(ANIMATION_SPEED, jumpFramesRight);
		currentFrame = idleFramesRight[0];
		aniState = 0;
	}

	// TODO: You slide down walls (don't stick to them)

	private final float MAX_VELOCITY = 3;
	private final float ACCELERATION = 0.15f;

	public void update(float delta)
	{
		if (Gdx.input.isKeyPressed(characterController.getLeftKey()) && canMoveLeft() && body.getLinearVelocity().x > -MAX_VELOCITY)
		{
			body.applyLinearImpulse(-ACCELERATION, 0, body.getPosition().x, body.getPosition().y, true);
			aniState = 0;
		}

		if (Gdx.input.isKeyPressed(characterController.getRightKey()) && canMoveRight() && body.getLinearVelocity().x < MAX_VELOCITY)
		{
			body.applyLinearImpulse(ACCELERATION, 0, body.getPosition().x, body.getPosition().y, true);
			aniState = 1;
		}

		if (Gdx.input.isKeyJustPressed(characterController.getJumpKey()) && body.getLinearVelocity().y == 0)
		{
			body.applyForceToCenter(new Vector2(0.0f, 100f), true);
			switch (aniState)
			{
			case 0:
				aniState = 2;
				break;
			case 1:
				aniState = 3;
				break;
			}
		}
	}

	public void draw(SpriteBatch batch, float stateTime)
	{
		switch (aniState)
		{
		case 0:
			currentFrame = idleAniLeft.getKeyFrame(stateTime, true);
			break;
		case 1:
			currentFrame = idleAniRight.getKeyFrame(stateTime, true);
			break;
		case 2:
			currentFrame = jumpAniLeft.getKeyFrame(stateTime, false);
			break;
		case 3:
			currentFrame = jumpAniRight.getKeyFrame(stateTime, false);
			break;
		}
		batch.draw(currentFrame, body.getPosition().x * PIXELS_TO_METERS - SPRITE_WIDTH / 2, body.getPosition().y * PIXELS_TO_METERS - SPRITE_HEIGHT / 2);
	}

	public boolean canFall()
	{
		return (getY() >= 0);
	}

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
		return !(position.x + SPRITE_WIDTH >= Gdx.graphics.getWidth());
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
}