package com.chesskabaddi.game;


import javafx.geometry.Pos;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyTestClass {
    @Test
    public void validateMoveTest() {
        Position x = new Position(1,2);
        Position kp = new Position(3,4);
        King k = new King(kp);
        Piece p = new Piece(x);
        assertTrue("Should give true due to valid move",p.testValid(1,0,k));
        assertFalse("Should give false due to bad move(out of bounds)",p.testValid(5,0,k));
        assertFalse("Should give false due to bad move (out of bounds)",p.testValid(-1,10,k));
        assertFalse("Should give false due to bad move (no change in pos)",p.testValid(0,0,k));
        assertFalse("Should give false due to bad move on kings position",p.testValid(2,2,k));


    }


    @Test
    public void validateFindPieceTest() {
        Attacker a = new Attacker();
        Defender d = new Defender();
        a.setActive(false);
        Position knight1StartPos = new Position(4,1);
        Position knight2StartPos = new Position(3,0);
        Position bishopStartPos = new Position(5,0);
        Position kingStartPos = new Position(0,4);

        // Set the starting positions for all Pieces

        a.initKnight(1,knight1StartPos);
        a.initKnight(2,knight2StartPos);
        a.initBishop(bishopStartPos);
        d.initKing(kingStartPos);
        a.knight1.getValidMoves(d.king);
        a.knight2.getValidMoves(d.king);
        a.bishop.getValidMoves(d.king);
        d.king.getValidMoves(d.king);
        int mousex= 0;
        int mousey = 0;
        assertSame("Should be the same and return the king piece",d.king,Piece.findPiece(mousex,mousey,a,d));
        mousex = 2;
        mousey = 2;
        assertNull("Should be the null as no piece exists in that position",Piece.findPiece(mousex,mousey,a,d));
        assertEquals(3,d.king.getNumValidMoves());



    }
}
