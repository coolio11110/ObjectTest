package com.mygdx.ObjectTest;

import com.badlogic.gdx.Game;
import com.mygdx.Screens.GameScreen;

public class ObjectTest extends Game {
	
	@Override
	public void render() {
		super.render();
	}

	@Override
	public void create() {
		setScreen(new GameScreen());
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
}
