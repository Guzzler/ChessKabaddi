package com.chesskabaddi.game;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChessKabaddi extends Game {
	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create() {
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
		font = new BitmapFont();
		MainMenu startgame = new MainMenu(this);
		this.setScreen(startgame);
		Gdx.input.setInputProcessor(startgame);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		// dispose of all the native resources
		batch.dispose();
		font.dispose();
	}
}
