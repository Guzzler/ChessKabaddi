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
    private static int SQUAREWIDTH = 150;
    private static int SQUAREHEIGHT = 150;
    Texture back_button = new Texture(Gdx.files.internal("back_button.png"));
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

        if(mouseY==0 && mouseX==7){
            SelectMenu selectScreen = new SelectMenu(game);
            game.setScreen(selectScreen);
            Gdx.input.setInputProcessor(selectScreen);
            dispose();
        }

        else if(mouseY<3){
            GameScreen mainGame = new GameScreen(game,false,false,false);
            game.setScreen(mainGame);
            Gdx.input.setInputProcessor(mainGame);
            dispose();
        }
        else if(mouseY>=3){
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

