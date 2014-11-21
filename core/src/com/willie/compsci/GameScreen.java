package com.willie.compsci;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The actual game screen
 */
public class GameScreen implements Screen {

    private Game game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private HashMap<KeyboardMovement, Integer> player1Controls, player2Controls;
    private Character duck, mojo;
    private Character[] characters;

    public GameScreen(Game game) {
        this.game = game;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // define controls (use yaml or something for this later?)
        player1Controls = new HashMap<KeyboardMovement, Integer>();
        player1Controls.put(KeyboardMovement.LEFT, Keys.A);
        player1Controls.put(KeyboardMovement.RIGHT, Keys.D);
        player1Controls.put(KeyboardMovement.JUMP, Keys.W);

        player2Controls = new HashMap<KeyboardMovement, Integer>();
        player2Controls.put(KeyboardMovement.LEFT, Keys.LEFT);
        player2Controls.put(KeyboardMovement.RIGHT, Keys.RIGHT);
        player2Controls.put(KeyboardMovement.JUMP, Keys.UP);

        // create characters at opposite ends of screen
        duck = new Character(0, 0, 10, new Texture("red.png"), player1Controls);
        mojo = new Character(w - 64, 0, 10, new Texture("blue.png"), player2Controls);
        characters = new Character[2];
        characters[0] = duck;
        characters[1] = mojo;

        // create spritebatch and camera
        batch = new SpriteBatch();

        camera = new OrthographicCamera(w, h);
        // translate camera so the bottom left pixel is (0,0)
        camera.translate(w / 2, h / 2);
        camera.update();
    }

    public void update(float delta) {
        for (Character c : characters) {
            c.update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw all the stuffs
        batch.begin();
        batch.draw(duck.getTexture(), duck.getX(), duck.getY());
        batch.draw(mojo.getTexture(), mojo.getX(), mojo.getY());
        batch.end();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
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
