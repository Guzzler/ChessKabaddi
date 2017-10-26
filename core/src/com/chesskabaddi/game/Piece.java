package com.chesskabaddi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Piece {
    private static final int PIECEHEIGHT = 150;
    private static final int PIECEWIDTH = 150;
    static Texture greenBorderImage = new Texture(Gdx.files.internal("green-border.png"));
    static Texture redBorderImage = new Texture(Gdx.files.internal("red-border.png"));
    static Piece allPieces[] = new Piece[4];
    static int numPieces = 0;
    Position pos;
    Position validMoves[] = new Position[9];
    int numValidMoves;
    Rectangle pieceStructure;
    Texture pieceImage;
    boolean currMove;
    boolean checkStatus;

    public Piece(Position p, Texture pieceTexture) {
        pos = new Position(p);
        pieceStructure = new Rectangle();
        pieceStructure.x = pos.x * PIECEWIDTH;
        pieceStructure.y = pos.y * PIECEHEIGHT;
        pieceStructure.width = PIECEWIDTH;
        pieceStructure.height = PIECEHEIGHT;
        this.pieceImage = pieceTexture;
        allPieces[numPieces] = this;
        numPieces++;
        this.numValidMoves = 0;
        currMove = false;
        checkStatus = false;

    }

    public void changePiecePos(int x,int y){
        pos.x = x;
        pos.y = y;
    }

    public void changePiecePos(Position p){
        pos.x = p.x;
        pos.y = p.y;

    }

    public void changePieceViewPos() {
        pieceStructure.x = pos.x * PIECEWIDTH;
        pieceStructure.y = pos.y * PIECEHEIGHT;
    }

    public static Piece findPiece(int mouseX, int mouseY, Attacker attacker, Defender defender) {
        Piece currSelectedPiece;
        for (Piece currPiece : allPieces) {
            if (currPiece.pos.x == mouseX && (currPiece.pos.y) == (4 - mouseY)) {
                if (attacker.isActive() && currPiece.getClass() != King.class) {
                    currSelectedPiece = currPiece;
                    return currSelectedPiece;
                } else if (defender.isActive() && currPiece.getClass() == King.class) {
                    System.out.println("abcd");
                    currSelectedPiece = currPiece;
                    return currSelectedPiece;
                }
            }
        }
        return null;

    }

    public void getValidMoves(King k){
        // used as a base function to call the various valid move functions for each piece
    }

    // takes the King as that is essential to check the check condition
    protected boolean testValid(int i,int j,King king){
        if(i==0 && j==0){
            return false;
        }
        if((this.pos.x+i) <0 || (this.pos.x+i)>5)
            return false;
        else if(this.pos.y+j<0 || (this.pos.y+j) >4)
            return false;
        else {
            for (Piece currPiece: Piece.allPieces) {
                if (currPiece.pos.x == (this.pos.x+i) && (currPiece.pos.y) == (this.pos.y+j)){
                    if(currPiece.getClass() == King.class && this.getClass() != King.class){
                        this.checkStatus = true;
                        king.updateCheckStatus();
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public int getNumValidMoves(){
        return numValidMoves;
    }
}