package com.willie.compsci;

import com.badlogic.gdx.Game;

public class WayRillie extends Game {

	public static final float PIXELS_TO_METERS = 100.0f;
	
	@Override
	public void create() {
		//before this go to a load screen and quickly load the assets and show progress with a load bar
		//will can take care of this (use freeman for the example)
		//use this for creating "sprite sheets" https://github.com/libgdx/libgdx/wiki/Texture-packer
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render() {
        super.render();
    }
}
