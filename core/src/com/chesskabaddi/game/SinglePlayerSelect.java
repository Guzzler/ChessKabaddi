package com.chesskabaddi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;


public class SinglePlayerSelect implements Screen,InputProcessor {
    final ChessKabaddi game;
    boolean selectSides;
    OrthographicCamera camera;
    Texture singlePlayerSelectImage;
    int currHeight;
    int currWidth;

    public SinglePlayerSelect(final ChessKabaddi game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,1200,750);
        singlePlayerSelectImage = new Texture(Gdx.files.internal("singlePlayerSelect.png"));
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
        game.batch.draw(singlePlayerSelectImage, 0, 0, 1200, 750);
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
            GameScreen mainGame = new GameScreen(game,false,false,false);
            game.setScreen(mainGame);
            Gdx.input.setInputProcessor(mainGame);
            dispose();
        }
        else if(mouseY<currHeight){
            GameScreen mainGame = new GameScreen(game,false,true,false);
            game.setScreen(mainGame);
            Gdx.input.setInputProcessor(mainGame);
            dispose();
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

