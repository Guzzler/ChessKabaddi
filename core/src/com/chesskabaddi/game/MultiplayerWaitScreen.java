package com.chesskabaddi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

public class MultiplayerWaitScreen implements Screen,InputProcessor {
    final ChessKabaddi game;
    OrthographicCamera camera;
    private Socket socket;
    boolean connected;
    boolean side;
    String playerID;
    String opponentID;

    public MultiplayerWaitScreen(final ChessKabaddi game, Socket socket){
        this.game = game;
        this.socket = socket;
        this.connected = false;
        this.side = true;
        this.playerID ="null";
        this.opponentID ="null";
        camera = new OrthographicCamera();
        camera.setToOrtho(false,1200,750);
        connectSocket();
        configSocket();

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
            game.font.draw(game.batch, "player ID:"+playerID, 500, 300);
        }
        else{
            game.font.draw(game.batch, "Player Matched ! Click to start !", 500, 400);
            game.font.draw(game.batch, "Opponent player ID:"+opponentID, 500, 300);
            if(Gdx.input.isTouched()){
                GameScreen mainGame = new GameScreen(game,true,side,true);
                game.setScreen(mainGame);
                Gdx.input.setInputProcessor(mainGame);
                dispose();
            }
        }
        game.batch.end();


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

    public void configSocket(){
      socket.on("socketID", args -> {
          JSONObject playerDetails = (JSONObject) args[0];
          try{
                int start = playerDetails.getInt("playerStart");
                playerID= playerDetails.getString("id");
                System.out.println("playerID"+playerID);
                System.out.println("start"+start);
                if(start%2==0){
                    side = false;
                }
          }
          catch(Exception e){
              System.out.println(e);
          }
      });
      socket.on("playerConnected", args -> {
         JSONObject OpponentDetails = (JSONObject) args[0];
         try{
               opponentID = OpponentDetails.getString("opponentID");
               System.out.println("Opponent ID"+opponentID);
               connected = true;
         }
         catch(Exception e){
             System.out.println(e);
         }

      });
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
