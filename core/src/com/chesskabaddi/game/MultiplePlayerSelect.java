package com.chesskabaddi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import io.socket.client.IO;
import io.socket.client.Socket;


public class MultiplePlayerSelect implements Screen,InputProcessor {
    final ChessKabaddi game;
    OrthographicCamera camera;
    Texture multiplePlayerSelectImage;
    int currHeight;
    int currWidth;
    private Socket socket;

    public MultiplePlayerSelect(final ChessKabaddi game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,1200,750);
        multiplePlayerSelectImage = new Texture(Gdx.files.internal("multiplePlayerSelect.png"));
        currHeight =750;
        currWidth = 1200;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(multiplePlayerSelectImage, 0, 0, 1200, 750);
        game.batch.end();


    }
    @Override
    public void resize(int width, int height) {
        currHeight = height;
        currWidth = width;
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
        int mouseY = Gdx.input.getY();
        System.out.println(screenY+mouseY);
        if(mouseY<(currHeight/2)){
            connectSocket();
            MultiplayerWaitScreen waitScreen = new MultiplayerWaitScreen(game,socket);
            game.setScreen(waitScreen);
            Gdx.input.setInputProcessor(waitScreen);
            dispose();
        }
        else if(mouseY<currHeight){
            GameScreen mainGame = new GameScreen(game,true,false,false);
            game.setScreen(mainGame);
            Gdx.input.setInputProcessor(mainGame);
            dispose();
        }
        return false;
    }


    public void connectSocket(){
        try {
            socket = IO.socket("http://localhost:8000");
            socket.connect();
        }
        catch(Exception e){
            System.out.println(e);
        }
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

