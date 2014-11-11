package com.willie.compsci;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * The main menu screen of the game
 */
public class MenuScreen implements Screen {

	private Game game;

	public MenuScreen(Game game) {
		this.game = game;
		
		//create camera and spritebatch
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//just need to draw a background, menu buttons, and probably a title
	}

	@Override
	public void resize(int width, int height) {
		//re-center menu buttons and a title
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
		//dispose of images used
	}

}
