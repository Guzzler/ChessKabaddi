package com.chesskabaddi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.socket.client.Socket;

public class MultiplayerWaitScreen implements Screen,InputProcessor {
    final ChessKabaddi game;
    OrthographicCamera camera;
    private Socket socket;
    boolean connected;

    public MultiplayerWaitScreen(final ChessKabaddi game, Socket socket){
        this.game = game;
        this.socket = socket;
        this.connected = false;
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
        if(!connected) {
            game.font.draw(game.batch, "Waiting for a player to be matched up with...", 500, 400);
        }
        else{
            game.font.draw(game.batch, "Player Matched ! Click to start !", 500, 400);
            if(Gdx.input.isTouched()){
                GameScreen mainGame = new GameScreen(game,true,false,true);
                game.setScreen(mainGame);
                Gdx.input.setInputProcessor(mainGame);
                dispose();
            }
        }
        game.batch.end();


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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
