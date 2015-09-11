package com.insight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insight.login.LoginScreen;

/**
 *
 * import com.badlogic.gdx.tests.utils.GdxTest;

 */

public class InsightGame extends Game {

	@Override
	public void create () {
    Assets.load();
    setScreen(new LoginScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
