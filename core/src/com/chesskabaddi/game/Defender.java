package com.chesskabaddi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Defender {
    static Texture kingTexture =  new Texture(Gdx.files.internal("king.png"));
    King king;
    private int points;
    private boolean active;

    public Defender(){
        active = true;
    }

    public void toggleActive(){
        if(active){
            active = false;
        }
        else{
            active = true;
        }
    }

    public int getPoints(){
        return points;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean x){
        active = x;
    }

    public void initKing(Position pos){
        king = new King(pos,kingTexture);
    }

    public void incrementPoints(){
        points++;
    }

    public void makeDefenderAIDecision(){

        Position posToMove = new Position(5,5);
        Position currCheckPos = new Position(5,5);
        Position oldPos  = new Position(5,5);
        getAttackerPiecesMoves();
        this.king.getValidMoves(this.king);
        int valMoves = this.king.getNumValidMoves();
        System.out.println("valid Moves:"+valMoves);
        int maxKingMoves =-1;
        for (int i = 0;i<valMoves;i++){
            System.out.println("val moves i:"+i);
            currCheckPos = this.king.validMoves[i];
            System.out.println("currCheckposx: "+currCheckPos.x);
            System.out.println("currCheckposy: "+currCheckPos.y);
            oldPos.changePos(this.king.pos);
            this.king.changePiecePos(currCheckPos);
            getAttackerPiecesMoves();
            this.king.getValidMoves(this.king);
            if(this.king.getNumValidMoves()>maxKingMoves){
                posToMove.changePos(this.king.pos);
                maxKingMoves = this.king.getNumValidMoves();
                System.out.println("posTomovex: "+posToMove.x);
                System.out.println("posTomovex: "+posToMove.y);
            }
            this.king.changePiecePos(oldPos);
            getAttackerPiecesMoves();
            this.king.getValidMoves(this.king);
        }

        this.king.changePiecePos(posToMove);
        this.king.getValidMoves(this.king);
        this.king.changePieceViewPos();
    }

    private void getAttackerPiecesMoves(){
        for (Piece p: Piece.allPieces){
            if(p.getClass() == King.class){
                continue;
            }
            else{
                p.getValidMoves(this.king);
            }
        }
    }
}
