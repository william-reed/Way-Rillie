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

/**
 * Represents a character to be used in this game
 */
public class Character {

	private final int MAX_HEALTH;

	private static final int FRAME_COLS = 6;
	private static final int FRAME_ROWS = 4;

	private static final int SPRITE_WIDTH = 64;
	private static final int SPRITE_HEIGHT = 64;

	private Animation idleAniLeft, idleAniRight, jumpAniLeft, jumpAniRight;
	private Texture spriteSheet;
	private TextureRegion[] idleFramesLeft, idleFramesRight, jumpFramesLeft, jumpFramesRight;
	private TextureRegion currentFrame;
	private static float ANIMATION_SPEED;
	private AnimationState aniState;

	private int currentHealth;
	private Vector2 position;
	private CharacterController characterController;
	private BodyDef bodyDef;
	private Body body;
	private World world;
	private Fixture fixture;
	
	private FixtureDef fixtureDefRight, fixtureDefLeft;

	// private ArrayList<FireBawl> fireBawls;

	/**
	 * Constructs a new Character 
	 * @param x position of the character
	 * @param y position of the character
	 * @param maxHealth maximum health of the Character
	 * @param spawnDirection true spawns the character left, false spawns it right
	 * @param spriteSheet sprite sheet containing all animations
	 * @param characterController the CharacterController reference which handles all the characters controls
	 * @param world the box2d world in use
	 * @param verticesLeft the vertices to be used for collision detection when the character is facing left(maximum of 8 allowed, length of 16)
	 * @param verticesRight the vertices to be used for collision detection when the character is facing right(maximum of 8 allowed, length of 16)
	 */
	public Character(float x, float y, int maxHealth, boolean spawnDirection, Texture spriteSheet, 
			CharacterController characterController, World world, float[] verticesLeft, float[] verticesRight) {
		MAX_HEALTH = maxHealth;
		currentHealth = MAX_HEALTH;
		this.characterController = characterController;
		position = new Vector2(x / WayRillie.PIXELS_TO_METERS, y / WayRillie.PIXELS_TO_METERS);
		this.world = world;
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);
		bodyDef.fixedRotation = true;
		body = world.createBody(bodyDef);
		direction = spawnDirection;

		//TODO: make sure vertices.length is divisable by 2
		//TODO: should create a more individualized message. too broad for debugging
		if(verticesLeft.length > 16 || verticesRight.length > 16) 
			throw new IllegalArgumentException("Only 8 vertices are can be used with box2d. ask will if you desire more");
		else if(verticesLeft.length < 4 || verticesRight.length < 4)
			throw new IllegalArgumentException("You need at least 3 vertices for a triangle for box2d polygonshapes");
		
		PolygonShape borderLeft  = new PolygonShape();
		PolygonShape borderRight = new PolygonShape();
		
		//convert vertices to world coordinates
		for(int i = 0; i < verticesLeft.length; i ++){
			verticesLeft[i] -= 32;
			verticesLeft[i] /= WayRillie.PIXELS_TO_METERS;
		}

		for(int i = 0; i < verticesRight.length; i ++){
			verticesRight[i] -= 32;
			verticesRight[i] /= WayRillie.PIXELS_TO_METERS;
		}
		
		borderLeft.set(verticesLeft);
		borderRight.set(verticesRight);

		fixtureDefLeft = new FixtureDef();
		fixtureDefLeft.shape = borderLeft;
		//TODO: make these constants
		fixtureDefLeft.density = 0.75f;
		fixtureDefLeft.friction = 1.5f;
		fixtureDefLeft.restitution = 0;
		
		fixtureDefRight = new FixtureDef();
		fixtureDefRight.shape = borderRight;
		//TODO: make these constants
		fixtureDefRight.density = 0.75f;
		fixtureDefRight.friction = 1.5f;
		fixtureDefRight.restitution = 0;

		if(spawnDirection)
			fixture = body.createFixture(fixtureDefLeft);
		else
			fixture = body.createFixture(fixtureDefRight);

		this.spriteSheet = spriteSheet;

		//ddwaw fireBawls = new ArrayList<FireBawl>();

		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / FRAME_COLS, spriteSheet.getHeight() / FRAME_ROWS);
		idleFramesLeft = new TextureRegion[FRAME_COLS];
		idleFramesRight = new TextureRegion[FRAME_COLS];
		jumpFramesLeft = new TextureRegion[FRAME_COLS];
		jumpFramesRight = new TextureRegion[FRAME_COLS];

		ANIMATION_SPEED = 0.15f;

		for (int i = 0; i < FRAME_COLS; i++) {
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
		if (spawnDirection)
			aniState = AnimationState.IDLE_LEFT;
		else
			aniState = AnimationState.IDLE_RIGHT;
	}

	private final float MAX_VELOCITY = 3;
	private final float ACCELERATION = 0.15f;
	private boolean direction, directionJustChanged;

	/**
	 * All the update logic for the game (no drawing stuff)
	 */
	public void update(float delta) {
		//move left
		if (Gdx.input.isKeyPressed(characterController.getLeftKey()) && canMoveLeft() && body.getLinearVelocity().x > -MAX_VELOCITY) {
			body.applyLinearImpulse(-ACCELERATION, 0, body.getPosition().x, body.getPosition().y, true);
			direction = true;
			directionJustChanged = true;
			if (body.getLinearVelocity().y == 0)
				aniState = AnimationState.IDLE_LEFT;
			else
				aniState = AnimationState.JUMP_LEFT;
		}

		//move right
		if (Gdx.input.isKeyPressed(characterController.getRightKey()) && canMoveRight() && body.getLinearVelocity().x < MAX_VELOCITY) {
			body.applyLinearImpulse(ACCELERATION, 0, body.getPosition().x, body.getPosition().y, true);
			direction = false;
			directionJustChanged = true;
			if (body.getLinearVelocity().y == 0)
				aniState = AnimationState.IDLE_RIGHT;
			else
				aniState = AnimationState.JUMP_RIGHT;
		}

		if (Gdx.input.isKeyJustPressed(characterController.getJumpKey())
				&& canJump()) {
			body.applyForceToCenter(0.0f, 100f, true);
			switch (aniState) {
			case IDLE_LEFT:
				aniState = AnimationState.JUMP_LEFT;
				break;
			case IDLE_RIGHT:
				aniState = AnimationState.JUMP_RIGHT;
				break;
			}
		} else if (body.getLinearVelocity().y == 0) {
			switch (aniState) {
			case JUMP_LEFT:
				aniState = AnimationState.IDLE_LEFT;
				break;
			case JUMP_RIGHT:
				aniState = AnimationState.IDLE_RIGHT;
				break;
			}
		}

		if (Gdx.input.isKeyJustPressed(characterController.getFallKey())
				&& !canJump()) {
			body.applyForceToCenter(new Vector2(0.0f, -100f), true);
		}
		
		if(directionJustChanged) {
			if(direction){
				body.destroyFixture(fixture);
				body.createFixture(fixtureDefLeft);
			}else {
				body.destroyFixture(fixture);
				body.createFixture(fixtureDefRight);
			}
			directionJustChanged = false;
		}

		// if (Gdx.input.isKeyJustPressed(characterController.getFireKey()))
		// {
		//
		// switch (aniState)
		// {
		// case IDLE_LEFT:
		// fireBawls.add(new FireBawl(false, position));
		// break;
		// case IDLE_RIGHT:
		// fireBawls.add(new FireBawl(true, position));
		// break;
		// }
		// }
	}

	/**
	 * Draws all the character stuff
	 */
	public void draw(SpriteBatch batch, float stateTime) {
		switch (aniState) {
		case IDLE_LEFT:
			currentFrame = idleAniLeft.getKeyFrame(stateTime, true);
			break;
		case IDLE_RIGHT:
			currentFrame = idleAniRight.getKeyFrame(stateTime, true);
			break;
		case JUMP_LEFT:
			currentFrame = jumpAniLeft.getKeyFrame(stateTime, false);
			break;
		case JUMP_RIGHT:
			currentFrame = jumpAniRight.getKeyFrame(stateTime, false);
			break;
		}
		batch.draw(currentFrame, body.getPosition().x * WayRillie.PIXELS_TO_METERS
				- SPRITE_WIDTH / 2, body.getPosition().y * WayRillie.PIXELS_TO_METERS
				- SPRITE_HEIGHT / 2);
		// for (FireBawl b : fireBawls)
		// b.draw(batch);
	}

	/**
	 * @return true if the character can jump
	 */
	public boolean canJump() {
		return body.getLinearVelocity().y == 0;
	}

	/**
	 * @return true if the character can move left
	 */
	public boolean canMoveLeft() {
		return !(position.x <= 0);
	}

	/**
	 * @return true if the character can move right
	 */
	public boolean canMoveRight() {
		return !(position.x + SPRITE_WIDTH >= Gdx.graphics.getWidth());
	}

	/**
	 * (never changes as of 12/27/14)
	 * @return the current health of the character 
	 */
	public int getCurrentHealth() {
		return currentHealth;
	}

	/**
	 * set the current health of the character
	 * <br>If the health set to the character is greater
	 * than the maximum health of the character its health
	 * will be set to max
	 * @param currentHealth health to be set to [0, MAX_HEALTH]
	 */
	public void setCurrentHealth(int currentHealth) {
		if(currentHealth > MAX_HEALTH)
			currentHealth = MAX_HEALTH;
		this.currentHealth = currentHealth;
	}
	
	/**
	 * Sets the characters current health to its maximum
	 */
	public void setHealthToMax(){
		this.currentHealth = MAX_HEALTH;
	}

	/**
	 * @return the max health of the character
	 */
	public int getMaxHealth() {
		return MAX_HEALTH;
	}
	
	/**
	 * @return the x position of the character
	 */
	public float getX() {
		return position.x;
	}

	/**
	 * set the x position of the character
	 */
	public void setX(float x) {
		position.x = x;
	}

	/**
	 * @return the y position of the character
	 */
	public float getY() {
		return position.y;
	}

	/**
	 * set the y position of the character
	 */
	public void setY(float y) {
		position.y = y;
	}
}