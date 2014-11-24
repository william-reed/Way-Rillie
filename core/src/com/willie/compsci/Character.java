package com.willie.compsci;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Character {

    private static final int WALK_SPEED = 5;

    private final int MAX_HEALTH;
    private int health;
    private int currentHealth;
    private Vector2 position;
    private HashMap<KeyboardMovement, Integer> controls;
    private BodyDef bodyDef;
    private Body body;
    private World world;
    private final float PIXELS_TO_METERS = 100f;

    // will be changed to TextureRegion for use with a sprite sheet
    private Texture texture;

    public Character(float x, float y, int maxHealth, Texture texture,
                    HashMap<KeyboardMovement, Integer> controls, World world) {
        MAX_HEALTH = maxHealth;
        this.texture = texture;
        this.controls = controls;
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
        fixtureDef.density = 1; 
        fixtureDef.friction = 5f;
        fixtureDef.restitution = 0.2f;
        
        Fixture fixture = body.createFixture(fixtureDef);
    }
    
    private final float MAX_VELOCITY = 3.0f;
    
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(getControl(KeyboardMovement.LEFT)) && canMoveLeft() && body.getLinearVelocity().x > -MAX_VELOCITY) {
            body.applyLinearImpulse(-0.1f, 0, body.getPosition().x, body.getPosition().y, true);
        } 
        if (Gdx.input.isKeyPressed(getControl(KeyboardMovement.RIGHT)) && canMoveRight() && body.getLinearVelocity().x < MAX_VELOCITY)
            body.applyLinearImpulse(0.1f, 0, body.getPosition().x, body.getPosition().y, true);
        //TODO: maybe more friction
        if( Gdx.input.isKeyJustPressed(getControl(KeyboardMovement.JUMP))) {
            body.applyForceToCenter(new Vector2(0.0f, 100f), true);
            
        }
       // if (Gdx.input.isKeyJustPressed(getControl(KeyboardMovement.JUMP)) && canJump() && jumpState == JumpState.CONSTANT)
       //     jumpState = JumpState.ASCENDING;

    }
    
    public void draw(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x * PIXELS_TO_METERS - texture.getWidth() / 2, body.getPosition().y * PIXELS_TO_METERS - texture.getHeight() / 2);
    }

    public boolean canFall() {
        return (getY() >= 0);
    }

    //
    public boolean canJump() {
        // TODO: implement
        return true;
    }

    public int getControl(KeyboardMovement control) {
        return controls.get(control);
    }

    public boolean canMoveLeft() {
        return !(position.x <= 0);
    }

    public boolean canMoveRight() {
        return !(position.x + texture.getWidth() >= Gdx.graphics.getWidth());
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public float getX() {
        return position.x;
    }

    public void setX(float x) {
        position.x = x;
    }

    public float getY() {
        return position.y;
    }

    public void setY(float y) {
        position.y = y;
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
        return body.getPosition();
    }

    public void setPosition(Vector2 position) {
        body.getPosition().set(position);
    }

}
