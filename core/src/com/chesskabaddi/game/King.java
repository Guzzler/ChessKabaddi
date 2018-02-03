package com.chesskabaddi.game;

import com.badlogic.gdx.graphics.Texture;
import javafx.geometry.Pos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class King extends Piece{
    boolean firstCheck;
    private boolean checked;
    private int uncheckedMovesLeft;
    int index;
//    Position lastMoves[]= new Position[5];
    ArrayList<Position> lastMoves = new ArrayList<Position>();


    public King(Position pos){
        super(pos);
        checked = false;
        firstCheck = false;
        uncheckedMovesLeft =10;
        this.numValidMoves=1;
        index =1;
    }

    public int getIndex() {
        return index;
    }

    public void updateCheckStatus(){
        boolean statusChange = false;
        for(Piece p:Piece.allPieces){
            if(p!=null) {
                if (p.getClass() == King.class) {
                    continue;
                }
                if (p.checkStatus == true) {
                    if (!firstCheck) {
                        firstCheck = true;
                    }
                    checked = true;
                    statusChange = true;
                }
            }
        }
        if(!statusChange){
            checked= false;
        }
    }

    public void getValidMoves(King king) {
        this.numValidMoves = 0;
        // resetting all valid moves
        for(int i=0;i<9;i++){
            this.validMoves[i]=null;
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (testValid(i,j)) {
                    this.validMoves[this.numValidMoves] = new Position(this.pos.x + i, this.pos.y + j);
                    this.numValidMoves++;
                }
            }
        }
    }


    private boolean testValid(int i,int j){
        boolean result = super.testValid(i,j,this);
        if(!result){
            return false;
        }
        for (Piece currPiece: Piece.allPieces) {
            if (currPiece != null) {
                if (currPiece.getClass() == King.class) {
                    continue;
                } else {
                    for (int k = 0; k < currPiece.numValidMoves; k++) {
                        if (currPiece.validMoves[k].x == (this.pos.x + i) && currPiece.validMoves[k].y == (this.pos.y + j)) {
                            return false;
                        }
                        if (currPiece.getClass() == Bishop.class && currPiece.checkStatus == true) { // extra test for bishop check
                            for (int q = -4; q < 4; q++) {
                                for (int r = -4; r < 4; r++) {
                                    if (q == r || q == (0 - r)) {
                                        if ((currPiece.pos.x + q) == (this.pos.x + i) && (currPiece.pos.y + r) == (this.pos.y + j)) {
                                            return false;
                                        }
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

    public void changePiecePos(Position p){
        super.changePiecePos(p);
        lastMoves.add(p);
    }
    public int getUncheckedMoves(){
        return uncheckedMovesLeft;
    }

    public void decrementUncheckedMoves(){
        uncheckedMovesLeft--;
    }
}
