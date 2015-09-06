package com.insight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insight.login.LoginScreen;

public class InsightGame extends Game {
	public SpriteBatch batch;

	@Override
	public void create () {
    batch = new SpriteBatch();
    Assets.load();
    setScreen(new LoginScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
