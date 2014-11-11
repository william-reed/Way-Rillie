package com.willie.compsci;

import com.badlogic.gdx.Game;

public class WayRillie extends Game {

	@Override
	public void create() {
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
}
