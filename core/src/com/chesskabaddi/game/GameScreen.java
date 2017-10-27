package com.chesskabaddi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.concurrent.TimeUnit;


public class GameScreen implements Screen,InputProcessor {
    final ChessKabaddi game;

    private static int SQUAREWIDTH = 150;
    private static int SQUAREHEIGHT = 150;
    int mouseX,mouseY;
    boolean multiplayer;
    boolean sideSelect; // true for attacker, false for defender
    Attacker attacker;
    Defender defender;
    Piece currSelectedPiece;
    Texture backgroundImage;
    OrthographicCamera camera;
    Rectangle background;
    Piece currMovePiece;
    Position currMovePosition;


    public GameScreen(final ChessKabaddi game,boolean multSelect, boolean typeSelect) {
        this.game = game;

        // load all images required

        multiplayer = multSelect;
        sideSelect = typeSelect;
        backgroundImage = new Texture(Gdx.files.internal("background.png"));


        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 750);

        background = new Rectangle();
        background.x = 0;
        background.y = 0;
        background.width = 900;
        background.height = 750;

        // set background for chessboard

        attacker = new Attacker();
        defender = new Defender();
        defender.toggleActive();
        // initialize Attacker and Defender
        // set Defender Active to false

        Position knight1StartPos = new Position(4,1);
        Position knight2StartPos = new Position(3,0);
        Position bishopStartPos = new Position(5,0);
        Position kingStartPos = new Position(0,4);

        // Set the starting positions for all Pieces

        attacker.initKnight(1,knight1StartPos);
        attacker.initKnight(2,knight2StartPos);
        attacker.initBishop(bishopStartPos);
        defender.initKing(kingStartPos);

        attacker.setActive(false);
        defender.setActive(true);
        // setting start state of the game

    }


    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        // display the defender's Points
        game.font.draw(game.batch, "Defender's Points: "+defender.getPoints(), 950, 550);

        if(!defender.king.firstCheck) { // checks whether the king has been checked once before
            game.font.draw(game.batch, "Moves Left for first Check: " + defender.king.getUncheckedMoves(), 950, 450);
        }

        if(defender.isActive() && !multiplayer && sideSelect){ // if the Defender AI is to move
            game.font.draw(game.batch, "Click to see the Defender AI play !", 950, 300);
        }
        else if(attacker.isActive() && !multiplayer && !sideSelect){ // if the Attacker AI is to move
            game.font.draw(game.batch, "Click to see the Attacker AI play !", 950, 300);
        }
        else if(defender.isActive() &&!multiplayer && !sideSelect){ // Player move if single player defending
            game.font.draw(game.batch, "Make your move ! ", 950, 300);
        }
        else if(attacker.isActive() &&!multiplayer && sideSelect){ // Player move if single player attacking
            game.font.draw(game.batch, "Make your move ! ", 950, 300);
        }
        else if(defender.isActive() && multiplayer){ // Player 1 move
            game.font.draw(game.batch, "Make your move Player 1(Defender) ! ", 950, 300);
        }
        else if(attacker.isActive() && multiplayer){ // Player 2 move
            game.font.draw(game.batch, "Make your move Player 2(Attacker) ! ", 950, 300);
        }

        game.batch.draw(backgroundImage, background.x, background.y, background.width, background.height);
        game.batch.draw(defender.king.pieceImage, defender.king.pieceStructure.x, defender.king.pieceStructure.y, defender.king.pieceStructure.width, defender.king.pieceStructure.height);
        game.batch.draw(attacker.bishop.pieceImage, attacker.bishop.pieceStructure.x, attacker.bishop.pieceStructure.y, attacker.bishop.pieceStructure.width, attacker.bishop.pieceStructure.height);
        game.batch.draw(attacker.knight1.pieceImage, attacker.knight1.pieceStructure.x, attacker.knight1.pieceStructure.y, attacker.knight1.pieceStructure.width, attacker.knight1.pieceStructure.height);
        game.batch.draw(attacker.knight2.pieceImage, attacker.knight2.pieceStructure.x, attacker.knight2.pieceStructure.y, attacker.knight2.pieceStructure.width, attacker.knight2.pieceStructure.height);

        // draw all the images of the pieces including the background

        game.batch.end();
        highlightPiece(currSelectedPiece);
        highlightValidMoves(currSelectedPiece);

    }



    public Position findPos(int mouseX,int mouseY){
        currSelectedPiece.getValidMoves(defender.king);
        for (Position pos: currSelectedPiece.validMoves) {
            if ((pos!=null) && pos.x == mouseX && (pos.y) == (4-mouseY)){
                return pos;
            }
        }
        return null;
    }


    public void inferCheckMate() throws InterruptedException {
        if (defender.king.getNumValidMoves()==0){
            TimeUnit.SECONDS.sleep(2);
            game.setScreen(new GameOver(game,defender.getPoints()));
        }
        if (defender.king.getUncheckedMoves() == 0){
            TimeUnit.SECONDS.sleep(2);
            game.setScreen(new GameOver(game,50));
        }
    }


    public void highlightPiece(Piece p){
        if (p!=null) {
            game.batch.begin();
            game.batch.draw(Piece.redBorderImage, p.pos.x * 150, p.pos.y * 150, 150, 150);
            game.batch.end();
        }
    }

    public void highlightValidMoves(Piece p){
        if (p!=null) {
            game.batch.begin();
            for (int i = 0; i < p.numValidMoves; i++) {
                game.batch.draw(Piece.greenBorderImage, p.validMoves[i].x * 150, p.validMoves[i].y * 150, 150, 150);
            }
            game.batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        SQUAREHEIGHT = (height)/5;
        SQUAREWIDTH = (width)/8;
        for (Piece currPiece: Piece.allPieces) {
            currPiece.changePieceViewPos();
        }
    }

    @Override
    public void show() {
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
        backgroundImage.dispose();
        Attacker.bishopTexture.dispose();
        Attacker.knightTexture.dispose();
        Defender.kingTexture.dispose();
        Piece.greenBorderImage.dispose();
        Piece.redBorderImage.dispose();
    }
    @Override public boolean mouseMoved (int screenX, int screenY) {
        // we can also handle mouse movement without anything pressed
//		camera.unproject(tp.set(screenX, screenY, 0));
        return false;
    }

    @Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        // ignore if its not left mouse button or first touch pointer
            mouseX = Gdx.input.getX() / SQUAREWIDTH;
            mouseY = Gdx.input.getY() / SQUAREHEIGHT ;
        if(attacker.isActive() && !multiplayer && !sideSelect) {
            // used to call attacker AI
            attacker.makeAttackerAIDecision(defender);
            attacker.toggleActive();
            defender.toggleActive();
            for(Piece p:Piece.allPieces){
                p.getValidMoves(defender.king);
            }
            try{
                inferCheckMate();
            }
            catch(Exception e){
                System.out.println(e);
            }

        }

        else if(defender.isActive() && !multiplayer && sideSelect){
            defender.makeDefenderAIDecision();
            System.out.println("abcd");
            attacker.toggleActive();
            defender.toggleActive();
            for(Piece p:Piece.allPieces){
                p.checkStatus = false;
                p.getValidMoves(defender.king);
            }
            if (defender.king.firstCheck) {
                defender.incrementPoints();
            }
            else {
                defender.king.decrementUncheckedMoves();
            }
        }
        else if (currSelectedPiece==null) {
            currSelectedPiece = Piece.findPiece(mouseX, mouseY,attacker,defender);
            currMovePiece= currSelectedPiece;
            for(Piece p: Piece.allPieces){
                p.getValidMoves(defender.king);
            }
            if (currMovePiece != null) {
                currMovePiece.getValidMoves(defender.king);
            }

        }
        else{
            currMovePosition = findPos(mouseX,mouseY);
            if (currMovePosition!=null && defender.isActive()) {
                currSelectedPiece.changePiecePos(currMovePosition);
                currSelectedPiece.changePieceViewPos();
                for (Piece p : Piece.allPieces) {
                    p.checkStatus = false;
                    p.getValidMoves(defender.king);
                }
                if (defender.king.firstCheck) {
                    defender.incrementPoints();
                }
                else {
                    defender.king.decrementUncheckedMoves();
                }
                attacker.toggleActive();
                defender.toggleActive();
            }
            else if (currMovePosition!=null && attacker.isActive()) {
                currSelectedPiece.changePiecePos(currMovePosition);
                currSelectedPiece.changePieceViewPos();
                for (Piece p : Piece.allPieces) {
                    p.getValidMoves(defender.king);
                }
                try{
                    inferCheckMate();
                }
                catch(Exception e){
                    System.out.println(e);
                }
                attacker.toggleActive();
                defender.toggleActive();
            }
            currSelectedPiece = null;
            currMovePosition = null;
        }
        return true;
    }

    @Override public boolean touchDragged (int screenX, int screenY, int pointer) {
        return true;
    }

    @Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override public boolean keyDown (int keycode) {
        return false;
    }

    @Override public boolean keyUp (int keycode) {
        return false;
    }

    @Override public boolean keyTyped (char character) {
        return false;
    }

    @Override public boolean scrolled (int amount) {
        return false;
    }
}
