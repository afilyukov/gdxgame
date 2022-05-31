package com.androidtest.gdxgame;

import com.androidtest.gdxgame.screens.MainMenu;
import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

	@Override
	public void create () { this.setScreen(new MainMenu(this)); }

	@Override
	public void render () { super.render(); }

	@Override
	public void dispose () { }

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}