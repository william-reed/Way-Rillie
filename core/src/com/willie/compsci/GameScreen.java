package com.willie.compsci;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * The actual game screen
 */
public class GameScreen implements Screen {

	private Game game;
	private SpriteBatch batch;
	private OrthographicCamera camera;

	public GameScreen(Game game) {
		this.game = game;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		//create characters at opposite ends of screen
		Character duck = new Character(64, 64, 0, 0, 10, null);
		Character mojo = new Character(64, 64, w - 64, 0, 10, null);
		
		//create spritebatch and camera
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera(w, h);
		//translate camera so the bottom left pixel is (0,0)
		camera.translate(w / 2, h / 2);
		camera.update();
	}
	
	public void update(float delta) {
		//update all the stuffs
	}
	
	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//draw all the stuffs
		//change for harrison
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	@Override
	public void resize(int width, int height) {

	}

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
