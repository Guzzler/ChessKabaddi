package com.chesskabaddi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Attacker {
    static Texture knightTexture =  new Texture(Gdx.files.internal("knight.png"));
    static Texture bishopTexture = new Texture(Gdx.files.internal("bishop.png"));
    Bishop bishop;
    Knight knight1;
    Knight knight2;
    private boolean active;

    public Attacker(){
        active = true;
    }

    public boolean isActive(){
        return active;
    }

    public void toggleActive(){
        if(active){
            active = false;
        }
        else{
            active = true;
        }
    }

    public void setActive(boolean x){
        active = x;
    }

    public void initKnight(int index, Position pos){
        if(index == 1){
            knight1 = new Knight(pos,knightTexture);
        }
        else if(index ==2){
            knight2 = new Knight(pos,knightTexture);
        }
    }
    public void initBishop(Position pos){
        bishop = new Bishop(pos,bishopTexture);
    }
    public void makeAttackerAIDecision(Defender opponent){

        Piece pieceToMove = null;
        Position posToMove = new Position(5,5);
        Position currCheckPos = new Position(5,5);
        Position oldPos = new Position(5,5);
        for (Piece p: Piece.allPieces){
            p.getValidMoves(opponent.king); // get all valid moves before making a move
        }
        int minKingMoves = 10;

        // checking all moves for all pieces and finding the one that leaves the king with the least number of moves
        for (Piece currPiece: Piece.allPieces){
            if(currPiece.getClass() == King.class){
                continue;
            }
            else{
                for(int i=0;i<currPiece.numValidMoves;i++){
                    currCheckPos.changePos(currPiece.validMoves[i]);
                    oldPos.changePos(currPiece.pos);
                    currPiece.changePiecePos(currCheckPos);
                    currPiece.getValidMoves(opponent.king);
                    opponent.king.getValidMoves(opponent.king);
                    int numValid = opponent.king.numValidMoves;
                    if(numValid <= minKingMoves){
                        pieceToMove = currPiece;
                        posToMove.changePos(currPiece.pos);
                        minKingMoves = numValid;
                    }
                    currPiece.changePiecePos(oldPos);
                    currPiece.getValidMoves(opponent.king);
                }
            }
        }
        pieceToMove.changePiecePos(posToMove);
        pieceToMove.getValidMoves(opponent.king);
        pieceToMove.changePieceViewPos();
    }

}
