package com.chesskabaddi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Knight extends Piece {

    boolean check;
    boolean checkMate;
    int index;
    int uniqueID;

    public Knight(Position pos,int indexArg) {
        super(pos);
        check = false;
        checkMate = false;
        index = indexArg;
        uniqueID=1+indexArg;
    }
    public int getUniqueID() {
        return uniqueID;
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
        for (int i =-2;i<=2;i++){
            for(int j=-2;j<=2;j++){
                if(i==0 || j==0){
                    continue;
                }
                else if ((i==j) || (i==-j)) {
                    continue;
                }
                else if (testValid(i,j,king)) {
                    this.validMoves[this.numValidMoves] = new Position(this.pos.x + i, this.pos.y + j);
                    this.numValidMoves++;
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
