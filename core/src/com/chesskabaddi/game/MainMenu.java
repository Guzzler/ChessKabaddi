package com.chesskabaddi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenu implements Screen,InputProcessor {
    final ChessKabaddi game;
    private static int SQUAREWIDTH = 150;
    private static int SQUAREHEIGHT = 150;
    OrthographicCamera camera;
    Texture next_button = new Texture(Gdx.files.internal("next_button.png"));

    public MainMenu(final ChessKabaddi game){
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
        game.batch.draw(next_button, 1075, 25, 100,100);
        game.font.draw(game.batch, "Welcome to Chess Kabaddi!!! ", 500, 700);
        game.font.draw(game.batch, "The game consists of two players playing in turns, an Attacker and a Defender.", 25, 600);
        game.font.draw(game.batch, "The Attacker(Black) who has 2 knights and one bishop at its disposal, the objective of the attacker is to checkmate/stalemate the Defender's King as soon", 25, 500);
        game.font.draw(game.batch, "as possible after its first check.", 25, 485);
        game.font.draw(game.batch, "The Attacker has a maximum of 10 moves to do its first Check, else the Defender gains the maximum of 50 points.", 25, 400);
        game.font.draw(game.batch, "The Defender(White) who starts has one King at its disposal and must try to evade for as long as possible. One point is gained for every move made after the first check.", 25, 300);
        game.font.draw(game.batch, "Special Rules: No piece may be killed/taken/removed from the board.", 25, 200);
        game.font.draw(game.batch, "Tap next to begin!", 500, 100);
        game.batch.end();



    }
    @Override
    public void resize(int width, int height) {
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
        if(mouseY==4 && mouseX==7){
            SelectMenu selectPlayer = new SelectMenu(game);
            game.setScreen(selectPlayer);
            Gdx.input.setInputProcessor(selectPlayer);
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
