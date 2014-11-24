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
 * The main menu screen of the game
 */
public class MenuScreen implements Screen {

	private Game game;
	private Vector2 titlePos;
	private SpriteBatch batch;
	private Texture title;
	private OrthographicCamera camera;
	private Button playGameButton;

	public MenuScreen(Game game) {
		this.game = game;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		batch = new SpriteBatch();
		title = new Texture(Gdx.files.internal("title.png"));
		//gypsy calculations to center everything
		titlePos = new Vector2(w / 2 - title.getWidth() / 2, h / 2 - title.getHeight() / 2 + 100);
		
		camera = new OrthographicCamera(w, h);
		//translate camera so the bottom left pixel is (0,0)
		camera.translate(w / 2, h / 2);
		camera.update();
		
		playGameButton = new Button(new Texture("play.png"), new Vector2(w / 2 - 100, h * .2f));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//won't be needed if the camera is never moved
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		{
			batch.draw(title, titlePos.x, titlePos.y);
			playGameButton.draw(batch);
		}
		batch.end();
		
		if(playGameButton.isClicked())
		{
			game.setScreen(new GameScreen(game));
		}
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false);
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		titlePos.set(w / 2 - title.getWidth() / 2, h / 2 - title.getHeight() / 2 + 100);
		playGameButton.getLocation().set(w / 2 - 100, h * .2f);
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
		batch.dispose();
		title.dispose();
		playGameButton.dispose();
	}

}
