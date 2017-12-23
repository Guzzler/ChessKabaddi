package com.chesskabaddi.game;

import com.badlogic.gdx.graphics.Texture;

public class Bishop extends Piece {

    boolean checkStatus;
    int index;

    public Bishop(Position pos,int indexArg) {
        super(pos);
        checkStatus = false;
        index = indexArg;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void getValidMoves(King king){
        this.numValidMoves = 0;
        // resetting all valid moves
        for(int i=0;i<9;i++){
            this.validMoves[i]=null;
        }
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
                        if (testValid(i, j,king)) {
                            this.validMoves[this.numValidMoves] = new Position(this.pos.x + i, this.pos.y + j);
                            this.numValidMoves++;
                        } else {
                            flag1=false;
                        }
                    }
                    if(flag2) {
                        if (testValid(0-i, j,king)) {
                            this.validMoves[this.numValidMoves] = new Position(this.pos.x -i, this.pos.y + j);
                            this.numValidMoves++;
                        } else {
                            flag2=false;
                        }
                    }
                    if(flag3) {
                        if (testValid(i, 0-j,king)) {
                            this.validMoves[this.numValidMoves] = new Position(this.pos.x + i, this.pos.y - j);
                            this.numValidMoves++;
                        } else {
                            flag3=false;
                        }
                    }
                    if(flag4) {
                        if (testValid(0-i, 0-j,king)) {
                            this.validMoves[this.numValidMoves] = new Position(this.pos.x - i, this.pos.y - j);
                            this.numValidMoves++;
                        } else {
                            flag4=false;
                        }
                    }
                }
            }
        }
    }
    @Override
    protected boolean testValid(int i,int j,King king){
        boolean result = super.testValid(i,j,king);
        if(!result){
            return false;
        }
        return true;
    }
}