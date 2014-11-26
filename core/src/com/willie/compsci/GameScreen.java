package com.willie.compsci;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The actual game screen
 */
public class GameScreen implements Screen {

    private Game game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private HashMap<Control, Integer> player1Controls, player2Controls;
    private Character duck, mojo;
    private Character[] characters;
    private Matrix4 debugMatrix;
    private final float PIXELS_TO_METERS = 100f;
    
    public GameScreen(Game game) {
        this.game = game;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(w, h);
        // translate camera so the bottom left pixel is (0,0)
        camera.translate(w / 2, h / 2);
        camera.update();

        // define controls (use yaml or something for this later?)
        player1Controls = new HashMap<Control, Integer>();
        player1Controls.put(Control.LEFT, Keys.A);
        player1Controls.put(Control.RIGHT, Keys.D);
        player1Controls.put(Control.JUMP, Keys.W);

        player2Controls = new HashMap<Control, Integer>();
        player2Controls.put(Control.LEFT, Keys.LEFT);
        player2Controls.put(Control.RIGHT, Keys.RIGHT);
        player2Controls.put(Control.JUMP, Keys.UP);

        initBox();
        // create characters at opposite ends of screen
        duck = new Character(100, 100, 10, new Texture("red.png"), player1Controls, world);
//        mojo = new Character(400, 100, 10, new Texture("blue.png"), player2Controls, world);
        characters = new Character[1];
        characters[0] = duck;
//        characters[1] = mojo;

        // create spritebatch and camera
        batch = new SpriteBatch();

    }

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private void initBox() {
        Box2D.init();
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();

        //these are all collision boundries. we can make a class to simplify this
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(camera.viewportWidth / 2 / PIXELS_TO_METERS, 10 / PIXELS_TO_METERS));
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth / 2 / PIXELS_TO_METERS, 10.0f / PIXELS_TO_METERS);
        groundBody.createFixture(groundBox, 0.0f);
        
        BodyDef wallLeftBodyDef = new BodyDef();
        wallLeftBodyDef.position.set(new Vector2(10 / PIXELS_TO_METERS, camera.viewportHeight / 2 / PIXELS_TO_METERS));
        Body wallLeftBody = world.createBody(wallLeftBodyDef);
        PolygonShape wallLeftBox = new PolygonShape();
        wallLeftBox.setAsBox(10 / PIXELS_TO_METERS, camera.viewportHeight / 2 / PIXELS_TO_METERS);
        wallLeftBody.createFixture(wallLeftBox, 0.0f);
        
        BodyDef wallRightBodyDef = new BodyDef();
        wallRightBodyDef.position.set(new Vector2((camera.viewportWidth - 10) / PIXELS_TO_METERS, camera.viewportHeight / 2 / PIXELS_TO_METERS));
        Body wallRightBody = world.createBody(wallRightBodyDef);
        PolygonShape wallRightBox = new PolygonShape();
        wallRightBox.setAsBox(10 / PIXELS_TO_METERS, camera.viewportHeight / 2 / PIXELS_TO_METERS);
        wallRightBody.createFixture(wallRightBox, 0.0f);
        
        //test a platform
        BodyDef platformBodyDef = new BodyDef();
        platformBodyDef.position.set(new Vector2((camera.viewportWidth / 2) / PIXELS_TO_METERS, 200 / PIXELS_TO_METERS));
        Body platformBody = world.createBody(platformBodyDef);
        PolygonShape platformBox = new PolygonShape();
        platformBox.setAsBox(100 / PIXELS_TO_METERS, 6 / PIXELS_TO_METERS);
        platformBody.createFixture(platformBox, 0.0f);
        
        BodyDef wallTopDef = new BodyDef();
        wallTopDef.position.set(new Vector2(camera.viewportWidth / 2 / PIXELS_TO_METERS, (camera.viewportHeight - 10) / PIXELS_TO_METERS));
        Body wallTop = world.createBody(wallTopDef);
        PolygonShape wallTopBox = new PolygonShape();
        wallTopBox.setAsBox(camera.viewportWidth / 2 / PIXELS_TO_METERS, 10.0f / PIXELS_TO_METERS);
        wallTop.createFixture(wallTopBox, 0.0f);
    }

    public void update(float delta) {
        for (Character c : characters) {
            c.update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        {
            for (Character c : characters) {
                c.draw(batch);
            }
        }
        batch.end();
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, 
                        PIXELS_TO_METERS, 0);
        debugRenderer.render(world, debugMatrix);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
