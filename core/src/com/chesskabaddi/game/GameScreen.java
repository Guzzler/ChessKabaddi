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
    static final int PIECEHEIGHT =150;
    static final int PIECEWIDTH = 150;

    static Piece allPieces[] = new Piece[4];
    static int numPieces = 0;
    Position pos;
    Position validMoves[] = new Position[9];
    int numValidMoves;
    Rectangle pieceStructure;
    Texture pieceImage;
    boolean currMove;

    public Piece(int x,int y, Texture pieceTexture){
        pos = new Position(x,y);
        pieceStructure= new Rectangle();
        pieceStructure.x=x*PIECEWIDTH;
        pieceStructure.y=y*PIECEHEIGHT;
        pieceStructure.width = PIECEWIDTH;
        pieceStructure.height= PIECEHEIGHT;
        this.pieceImage = pieceTexture;
        allPieces[numPieces]= this;
        numPieces++;
        this.numValidMoves =0;
        currMove=false;

    }

    public void changePiecePos(){
        pieceStructure.x = pos.x* PIECEWIDTH;
        pieceStructure.y = pos.y *PIECEHEIGHT;
    }
}

class King extends Piece{

    boolean firstCheck;
    boolean checkMate;
    int points;
    int uncheckedMovesLeft;
    public King(int x,int y,Texture pieceTexture){
        super(x,y,pieceTexture);
        firstCheck = false;
        checkMate = false;
        points =0;
        uncheckedMovesLeft =10;
        this.numValidMoves=1;
    }

    public void check(ChessKabaddi game){

    }
}

class Bishop extends Piece{

    boolean check;
    boolean checkMate;

    public Bishop(int x,int y,Texture pieceTexture){
        super(x,y,pieceTexture);
        check = false;
        checkMate = false;
    }

    public void check(ChessKabaddi game){

    }
}

class Knight extends Piece{

    boolean check;
    boolean checkMate;

    public Knight(int x,int y,Texture pieceTexture){
        super(x,y,pieceTexture);
        check = false;
        checkMate = false;
    }

    public void check(ChessKabaddi game){

    }
}

public class GameScreen implements Screen,InputProcessor {
    final ChessKabaddi game;

    static int SQUAREWIDTH = 150;
    static int SQUAREHEIGHT = 150;
    boolean attacker;
    boolean defender;
    Piece currSelectedPiece;
    
    int mouseX,mouseY;
    Texture backgroundImage;
    Texture knightImage;
    Texture kingImage;
    Texture bishopImage;
    Texture redBorderImage;
    Texture greenBorderImage;
    OrthographicCamera camera;
    Rectangle background;
    Rectangle redBorder;
    King king;
    Piece bishop;
    Piece knight1;
    Piece knight2;
    Piece currMovePiece;
    Position currMovePosition;


    public GameScreen(final ChessKabaddi game) {
        this.game = game;

        // load all images required
        greenBorderImage = new Texture(Gdx.files.internal("green-border.png"));
        redBorderImage = new Texture(Gdx.files.internal("red-border.png"));

        backgroundImage = new Texture(Gdx.files.internal("background.png"));
        knightImage = new Texture(Gdx.files.internal("knight.png"));
        bishopImage = new Texture(Gdx.files.internal("bishop.png"));
        kingImage = new Texture(Gdx.files.internal("king.png"));

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 750);

        background = new Rectangle();
        background.x = 0;
        background.y = 0;
        background.width = 900;
        background.height = 750;

        knight1 = new Knight(4,0,knightImage);
        knight2 = new Knight(3,0,knightImage);
        king = new King(0,4,kingImage);
        bishop = new Bishop(5,0,bishopImage);

        attacker = true;
        defender= false;

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
        game.font.draw(game.batch, "Defender's Points: "+king.points, 950, 550);
        if(!king.firstCheck) {
            game.font.draw(game.batch, "Moves Left for first Check: " + king.uncheckedMovesLeft, 950, 450);
        }
        game.batch.draw(backgroundImage, background.x, background.y, background.width, background.height);
        game.batch.draw(king.pieceImage, king.pieceStructure.x, king.pieceStructure.y, king.pieceStructure.width, king.pieceStructure.height);
        game.batch.draw(bishop.pieceImage, bishop.pieceStructure.x, bishop.pieceStructure.y, bishop.pieceStructure.width, bishop.pieceStructure.height);
        game.batch.draw(knight1.pieceImage, knight1.pieceStructure.x, knight1.pieceStructure.y, knight1.pieceStructure.width, knight1.pieceStructure.height);
        game.batch.draw(knight2.pieceImage, knight2.pieceStructure.x, knight2.pieceStructure.y, knight2.pieceStructure.width, knight2.pieceStructure.height);

        game.batch.end();
        highlightPiece(currSelectedPiece);
        highlightValidMoves(currSelectedPiece);
    }

    public Piece findPiece(int mouseX,int mouseY){
        for (Piece currPiece: Piece.allPieces) {
            if (currPiece.pos.x == mouseX && (currPiece.pos.y) == (4-mouseY)){
                if(attacker && !defender && currPiece.getClass() != King.class) {
                    currSelectedPiece = currPiece;
                    return currPiece;
                }
                else if(defender && !attacker && currPiece.getClass()==King.class){
                    currSelectedPiece = currPiece;
                    return currPiece;
                }
            }
        }
        return null;

    }

    public Position findPos(int mouseX,int mouseY){
        for (Position pos: currSelectedPiece.validMoves) {
            if ((pos!=null) && pos.x == mouseX && (pos.y) == (4-mouseY)){
                currMovePosition = pos;
                return pos;
            }
        }
        return null;
    }

    public void getValidMoves(Piece p){
        if (p.getClass() == King.class){
            p.numValidMoves = 0;
            // resetting all valid moves
            for (int i =-1;i<=1;i++){
                for(int j=-1;j<=1;j++){
                    if(i==0 && j==0){
                        continue;
                    }
                    if(testValid(p,i,j)){
                        p.validMoves[p.numValidMoves]= new Position(p.pos.x+i,p.pos.y+j);
                        p.numValidMoves++;
                    }
                }
            }
        }
        if (p.getClass() == Knight.class){
            p.numValidMoves = 0;
            // resetting all valid moves
            for (int i =-2;i<=2;i++){
                for(int j=-2;j<=2;j++){
                    if(i==0 || j==0){
                        continue;
                    }
                    else if ((i==j) || (i==-j)) {
                        continue;
                    }
                    else if (testValid(p, i, j)) {
                            p.validMoves[p.numValidMoves] = new Position(p.pos.x + i, p.pos.y + j);
                            p.numValidMoves++;
                    }
                }
            }
        }
        if (p.getClass() == Bishop.class){
            p.numValidMoves = 0;
            // resetting all valid moves
            boolean flag1=true;// check quadrant 1
            boolean flag2=true;// check quadrant 2
            boolean flag3=true; // check quadrant 3
            boolean flag4=true;// check quadrant 4
            for (int i =1;i<=4;i++){
                for(int j=1;j<=4;j++){
                    if(i==0 || j==0){
                        continue;
                    }
                    if(i==j){
                        if(flag1) {
                            if (testValid(p, i, j)) {
                                p.validMoves[p.numValidMoves] = new Position(p.pos.x + i, p.pos.y + j);
                                p.numValidMoves++;
                            } else {
                                flag1=false;
                            }
                        }
                        if(flag2) {
                            if (testValid(p, 0-i, j)) {
                                p.validMoves[p.numValidMoves] = new Position(p.pos.x -i, p.pos.y + j);
                                p.numValidMoves++;
                            } else {
                                flag2=false;
                            }
                        }
                        if(flag3) {
                            if (testValid(p, i, 0-j)) {
                                p.validMoves[p.numValidMoves] = new Position(p.pos.x + i, p.pos.y - j);
                                p.numValidMoves++;
                            } else {
                                flag3=false;
                            }
                        }
                        if(flag4) {
                            if (testValid(p, 0-i, 0-j)) {
                                p.validMoves[p.numValidMoves] = new Position(p.pos.x - i, p.pos.y - j);
                                p.numValidMoves++;
                            } else {
                                flag4=false;
                            }
                        }
                    }
                }
            }
        }
    }


    public void inferCheckMate(King k){
        if (k.numValidMoves==0){
            game.setScreen(new GameOver(game,king.points));
        }
        if (k.uncheckedMovesLeft == 0){
            game.setScreen(new GameOver(game,50));
        }
    }
    public boolean testValid(Piece p,int i,int j){
        if((p.pos.x+i) <0 || (p.pos.x+i)>5)
            return false;
        else if(p.pos.y+j<0 || (p.pos.y+j) >4)
            return false;
        else {
            for (Piece currPiece: Piece.allPieces) {
                if (currPiece.pos.x == (p.pos.x+i) && (currPiece.pos.y) == (p.pos.y+j)){
                    if(currPiece.getClass() == King.class && p.getClass() != King.class){
                        king.firstCheck= true;// test for check condition
                    }
                    return false;
                }
            }
        }
        if(p.getClass() == King.class){
            for (Piece currPiece: Piece.allPieces) {
                if (currPiece.getClass() == King.class) {
                    continue;
                }
                else{
                    for (int k = 0; k < currPiece.numValidMoves; k++) {
                        if(currPiece.validMoves[k].x ==  (p.pos.x+i) && currPiece.validMoves[k].y == (p.pos.y+j)){
                            return false;
                        }
                        if(currPiece.getClass() == Bishop.class){ // extra test for bishop check
                            for(int q = -4;q<4;q++){
                                for (int r=-4;r<4;r++){
                                    if(q==r || q== (0-r)){
                                        if((currPiece.pos.x+q)== (p.pos.x+i) && (currPiece.pos.y+r) == (p.pos.y+j)){
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        if(p.getClass() == Knight.class){
            return true;
        }
        if(p.getClass() == Bishop.class){
            return true;
        }
        return false;
    }
    public void highlightPiece(Piece p){
        if (p!=null) {
            game.batch.begin();
            game.batch.draw(redBorderImage, p.pos.x * 150, p.pos.y * 150, 150, 150);
            game.batch.end();
        }
    }

    public void highlightValidMoves(Piece p){
        if (p!=null) {
            game.batch.begin();
            for (int i = 0; i < p.numValidMoves; i++) {
                game.batch.draw(greenBorderImage, p.validMoves[i].x * 150, p.validMoves[i].y * 150, 150, 150);
            }
            game.batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("Get Width:"+width);
        System.out.println("get Height:"+height);
        SQUAREHEIGHT = (height)/5;
        SQUAREWIDTH = (width)/8;
        System.out.println("square width:"+SQUAREWIDTH);
        System.out.println("square height:"+SQUAREHEIGHT);
        for (Piece currPiece: Piece.allPieces) {
            currPiece.changePiecePos();
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
            mouseX = Gdx.input.getX() / SQUAREWIDTH;
            mouseY = Gdx.input.getY() / SQUAREHEIGHT ;
        if (currSelectedPiece==null) {
            currMovePiece = findPiece(mouseX, mouseY);
            if (currMovePiece != null) {
                getValidMoves(currMovePiece);
            }
        }
        else{
            currMovePosition = findPos(mouseX,mouseY);
            if (currMovePosition!=null){
                currSelectedPiece.pos.x=currMovePosition.x;
                currSelectedPiece.pos.y = currMovePosition.y;
                currSelectedPiece.changePiecePos();
                for(Piece p:Piece.allPieces){
                    getValidMoves(p);
                }
                if(attacker && !defender) {
                    attacker = false;
                    defender = true;
                    inferCheckMate(king);
                }
                else if(defender && !attacker){
                    if(king.firstCheck) {
                        king.points++;
                    }
                    else{
                        king.uncheckedMovesLeft--;
                    }
                    attacker = true;
                    defender = false;
                }
            }
            currSelectedPiece= null;
            currMovePosition= null;
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
