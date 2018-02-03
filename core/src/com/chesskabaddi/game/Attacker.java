package com.chesskabaddi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Attacker {
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
            knight1 = new Knight(pos,index);
        }
        else if(index ==2){
            knight2 = new Knight(pos,index);
        }
    }
    public void initBishop(Position pos){
        bishop = new Bishop(pos,1);
    }
    public boolean aILoop(King k){
        if (k.lastMoves.size()<6){
            return false;
        }
        else{
            // TODO: Change this to a more structured way of checking
            int moveSizeIndex = k.lastMoves.size()-1;
            // for two move loops
            if(k.lastMoves.get(moveSizeIndex).comparePos(k.lastMoves.get(moveSizeIndex-2)) && k.lastMoves.get(moveSizeIndex-1).comparePos(k.lastMoves.get(moveSizeIndex-3))){
                return true;
            }
            // for three move loops
            else if(k.lastMoves.get(moveSizeIndex).comparePos(k.lastMoves.get(moveSizeIndex-3)) && k.lastMoves.get(moveSizeIndex-1).comparePos(k.lastMoves.get(moveSizeIndex-4))
                    && k.lastMoves.get(moveSizeIndex-2).comparePos(k.lastMoves.get(moveSizeIndex-5))){
                return true;
            }
            return false;
        }

    }
    public void makeAttackerAIDecision(Defender opponent){

        Piece pieceToMove = null;
        Position posToMove = new Position(5,5);
        Position currCheckPos = new Position(5,5);
        Position oldPos = new Position(5,5);
        Position secondBestPos = new Position(5,5);
        Piece secondBestPiece = null;
        for (Piece p: Piece.allPieces){
            if(p!=null) {
                p.getValidMoves(opponent.king); // get all valid moves before making a move
            }
        }
        int minKingMoves = 10;

        // checking all moves for all pieces and finding the one that leaves the king with the least number of moves
        for (Piece currPiece: Piece.allPieces) {
            if (currPiece != null) {
                if (currPiece.getClass() == King.class) {
                    continue;
                } else {
                    for (int i = 0; i < currPiece.numValidMoves; i++) {
                        currCheckPos.changePos(currPiece.validMoves[i]);
                        oldPos.changePos(currPiece.pos);
                        currPiece.changePiecePos(currCheckPos);
                        currPiece.getValidMoves(opponent.king);
                        opponent.king.getValidMoves(opponent.king);
                        int numValid = opponent.king.numValidMoves;
                        if (numValid <= minKingMoves) {
                            secondBestPiece = pieceToMove;
                            secondBestPos.changePos(posToMove);
                            pieceToMove = currPiece;
                            posToMove.changePos(currPiece.pos);
                            minKingMoves = numValid;
                        }
                        currPiece.changePiecePos(oldPos);
                        currPiece.getValidMoves(opponent.king);
                    }
                }
            }
        }
        if(aILoop(opponent.king) && secondBestPiece !=null){
            secondBestPiece.changePiecePos(secondBestPos);
            secondBestPiece.getValidMoves(opponent.king);
            secondBestPiece.changePieceViewPos();
        }
        else{
            pieceToMove.changePiecePos(posToMove);
            pieceToMove.getValidMoves(opponent.king);
            pieceToMove.changePieceViewPos();
        }

    }

}
