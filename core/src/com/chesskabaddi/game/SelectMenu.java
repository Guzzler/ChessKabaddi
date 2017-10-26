package com.chesskabaddi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class SelectMenu implements Screen {
    final ChessKabaddi game;

    OrthographicCamera camera;

    public SelectMenu(final ChessKabaddi game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,1200,750);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "SELECT TYPE OF GAME!!! ", 500, 700);
        game.font.draw(game.batch, ".", 25, 600);


        if (Gdx.input.isTouched()) {
            GameScreen mainGame = new GameScreen(game);
            game.setScreen(mainGame);
            Gdx.input.setInputProcessor(mainGame);
            dispose();
        }
        dispose();

    }
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
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

