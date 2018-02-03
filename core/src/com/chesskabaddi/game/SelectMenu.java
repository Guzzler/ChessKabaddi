package com.chesskabaddi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.util.concurrent.TimeUnit;


public class SelectMenu implements Screen,InputProcessor {
    final ChessKabaddi game;
    boolean singlePlayer;
    private static int SQUAREWIDTH = 150;
    private static int SQUAREHEIGHT = 150;
    Texture back_button = new Texture(Gdx.files.internal("back_button.png"));
    OrthographicCamera camera;
    Texture playerSelectImage;
    int currHeight;
    int currWidth;
    private Socket socket;

    public SelectMenu(final ChessKabaddi game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,1200,750);
        playerSelectImage = new Texture(Gdx.files.internal("playerSelectScreen.jpg"));
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
        game.batch.draw(playerSelectImage, 0, 0, 1200, 750);
        game.batch.draw(back_button, 1075, 620, 100,100);
        game.batch.end();


    }
    @Override
    public void resize(int width, int height) {
        currHeight = height;
        currWidth = width;
        SQUAREHEIGHT = (height)/5;
        SQUAREWIDTH = (width)/8;
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
        int mouseX = Gdx.input.getX() / SQUAREWIDTH;
        int mouseY = Gdx.input.getY() / SQUAREHEIGHT ;
        System.out.println(mouseX);
        System.out.println(mouseY);
        if(mouseY==0 && mouseX==7){
            MainMenu mainScreen = new MainMenu(game);
            game.setScreen(mainScreen);
            Gdx.input.setInputProcessor(mainScreen);
            dispose();
        }

        else if(mouseY<3){
            MultiplePlayerSelect multipleGame = new MultiplePlayerSelect(game);
            game.setScreen(multipleGame);

            Gdx.input.setInputProcessor(multipleGame);
            dispose();
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(mouseY>=3){

            SinglePlayerSelect singleGame = new SinglePlayerSelect(game);
            game.setScreen(singleGame);
            Gdx.input.setInputProcessor(singleGame);
            dispose();
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

