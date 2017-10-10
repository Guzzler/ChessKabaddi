package com.chesskabaddi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

class Position{
    int x,y;
    public Position(int x,int y){
        this.x = x;
        this.y = y;
    }

    public void changePos(int x,int y){
        this.x= x;
        this.y= y;
    }
}


class Piece{
    static final int PIECEHEIGHT =200;
    static final int PIECEWIDTH = 200;

    static Piece allPieces[] = new Piece[4];
    static int numPieces = 0;
    Position pos;
    Rectangle pieceStructure;
    Texture pieceImage;

    public Piece(int x,int y, Texture pieceTexture){
        pos = new Position(x,y);
        pieceStructure= new Rectangle();
        pieceStructure.x=x*200;
        pieceStructure.y=y*200;
        pieceStructure.width = PIECEWIDTH;
        pieceStructure.height= PIECEHEIGHT;
        this.pieceImage = pieceTexture;
        allPieces[numPieces]= this;
        numPieces++;
        System.out.println(allPieces.toString());

    }

    public void changePiecePos(){
        pieceStructure.x = pos.x* 200;
        pieceStructure.y = pos.y *200;
    }
}

class King extends Piece{

    boolean check;
    boolean checkMate;

    public King(int x,int y,Texture pieceTexture){
        super(x,y,pieceTexture);
        check = false;
        checkMate = false;
    }

    public void check(){

    }
}

public class GameScreen implements Screen,InputProcessor {
    final ChessKabaddi game;


    int mouseX,mouseY;
    Texture backgroundImage;
    Texture knightImage;
    Texture kingImage;
    Texture bishopImage;
    OrthographicCamera camera;
    Rectangle background;
    King king;
    Piece bishop;
    Piece knight1;
    Piece knight2;
    Piece currMovePiece;


    public GameScreen(final ChessKabaddi game) {
        this.game = game;

        // load all images required
        backgroundImage = new Texture(Gdx.files.internal("background.png"));
        knightImage = new Texture(Gdx.files.internal("knight.png"));
        bishopImage = new Texture(Gdx.files.internal("bishop.png"));
        kingImage = new Texture(Gdx.files.internal("king.png"));

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);

        background = new Rectangle();
        background.x = 0;
        background.y = 0;
        background.width = 1200;
        background.height = 800;

        knight1 = new Piece(4,0,knightImage);
        knight2 = new Piece(3,0,knightImage);
        king = new King(0,3,kingImage);
        bishop = new Piece(5,0,bishopImage);

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
        game.batch.draw(backgroundImage, background.x, background.y, background.width, background.height);
        game.batch.draw(king.pieceImage, king.pieceStructure.x, king.pieceStructure.y, king.pieceStructure.width, king.pieceStructure.height);
        game.batch.draw(bishop.pieceImage, bishop.pieceStructure.x, bishop.pieceStructure.y, bishop.pieceStructure.width, bishop.pieceStructure.height);
        game.batch.draw(knight1.pieceImage, knight1.pieceStructure.x, knight1.pieceStructure.y, knight1.pieceStructure.width, knight1.pieceStructure.height);
        game.batch.draw(knight2.pieceImage, knight2.pieceStructure.x, knight2.pieceStructure.y, knight2.pieceStructure.width, knight2.pieceStructure.height);

        game.batch.end();
    }

    public Piece findPiece(int mouseX,int mouseY){
        for (Piece currPiece: Piece.allPieces) {
            if (currPiece.pos.x == mouseX && (currPiece.pos.y) == (3-mouseY)){
                return currPiece;
            }
        }
        return null;

    }
    public void movePiece(Piece p){
        p.pos.x +=1;
        p.pos.y -=1;
        if (p.pos.x == 3){
            System.out.println("reached end");
            GameOver end = new GameOver(game);
            game.setScreen(end);
            dispose();
        }
        p.changePiecePos();
    }

    @Override
    public void resize(int width, int height) {
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
        knightImage.dispose();
        bishopImage.dispose();
        kingImage.dispose();
    }
    @Override public boolean mouseMoved (int screenX, int screenY) {
        // we can also handle mouse movement without anything pressed
//		camera.unproject(tp.set(screenX, screenY, 0));
        return false;
    }

    @Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        // ignore if its not left mouse button or first touch pointer
        mouseX = Gdx.input.getX()/200;
        mouseY = Gdx.input.getY()/200;
        currMovePiece = findPiece(mouseX,mouseY);
        if (currMovePiece !=null){
            movePiece(currMovePiece);
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
