package com.chesskabaddi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Piece {
    private static final int PIECEHEIGHT;
    private static final int PIECEWIDTH;
    static Piece allPieces[];
    static int numPieces;

    static{
        PIECEHEIGHT=150;
        PIECEWIDTH=150;
        allPieces = new Piece[20];
        numPieces=0;
    }
    Position pos;
    Position validMoves[] = new Position[9];
    int numValidMoves;
    Rectangle pieceStructure;
    boolean currMove;
    boolean checkStatus;

    public Piece(Position p) {
        pos = new Position(p);
        pieceStructure = new Rectangle();
        pieceStructure.x = pos.x * PIECEWIDTH;
        pieceStructure.y = pos.y * PIECEHEIGHT;
        pieceStructure.width = PIECEWIDTH;
        pieceStructure.height = PIECEHEIGHT;
        allPieces[numPieces] = this;
        numPieces++;
        this.numValidMoves = 0;
        currMove = false;
        checkStatus = false;

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
            if (currPiece!=null && currPiece.pos.x == mouseX && (currPiece.pos.y) == (4 - mouseY)) {
                if (attacker.isActive() && currPiece.getClass() != King.class) {
                    currSelectedPiece = currPiece;
                    return currSelectedPiece;
                } else if (defender.isActive() && currPiece.getClass() == King.class) {
                    currSelectedPiece = currPiece;
                    return currSelectedPiece;
                }
            }
        }
        return null;

    }

    public int getIndex(){
        return 1;
    }
    public void getValidMoves(King k){}
        // used as a base function to call the various valid move functions for each piece

    // takes the King as that is essential to check the check condition
    protected boolean testValid(int i,int j,King king){
        if(i==0 && j==0){
            return false;
        }
        else if(((this.pos.x+i) <0) || ((this.pos.x+i)>5))
            return false;
        else if(((this.pos.y+j)<0) || ((this.pos.y+j) >4))
            return false;
        else {
            for (Piece currPiece: Piece.allPieces) {
                if (currPiece !=null && currPiece.pos.x == (this.pos.x+i) && (currPiece.pos.y) == (this.pos.y+j)){
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