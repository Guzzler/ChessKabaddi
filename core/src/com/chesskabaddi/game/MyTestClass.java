package com.chesskabaddi.game;


import javafx.geometry.Pos;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyTestClass {



    @Test
    public void UnitValidateMoveTest() {
        Position x = new Position(1,2);
        Position kp = new Position(3,4);
        King k = new King(kp);
        Piece p = new Piece(x);
        p.getValidMoves(k);
        assertTrue("Should give true due to valid move",p.testValid(1,0,k));
        assertFalse("Should give false due to bad move(out of bounds)",p.testValid(5,0,k));
        assertFalse("Should give false due to bad move (out of bounds)",p.testValid(-1,10,k));
        assertFalse("Should give false due to bad move (no change in pos)",p.testValid(0,0,k));
        assertFalse("Should give false due to bad move on kings position",p.testValid(2,2,k));
    }
    @Test
    public void UnitDefenderTest() {
        Defender x = new Defender();
        x.setActive(false);
        assertFalse("defender must be inactive",x.isActive());
        x.incrementPoints();
        assertEquals("Points must be 1",1,x.getPoints());
    }


    @Test
    public void UnitPositionTest() {
        Position p = new Position(1,2);
        Position q = new Position(p);
        assertEquals("q must be (1,2)",1,q.x);
        assertEquals("q must be (1,2)",1,q.x);

    }






    @Test
    public void IntegrationValidateAITest() {
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
        d.makeDefenderAIDecision();
        assertEquals("Should move to the position(1,3)",1,d.king.pos.x);
        assertEquals("Should move to the position(1,3)",3,d.king.pos.y);

        a.toggleActive();
        d.toggleActive();
        a.makeAttackerAIDecision(d);
        assertEquals("Knight should move to the position(2,2)",2,a.knight2.pos.x);
        assertEquals("Knight should move to the position(2,2)",2,a.knight2.pos.y);
        d.king.changePiecePos(new Position(0,4));
        a.knight2.changePiecePos(new Position(3,0));
        a.knight1.getValidMoves(d.king);
        a.knight2.getValidMoves(d.king);
        a.bishop.getValidMoves(d.king);
        d.king.getValidMoves(d.king);

    }

    @Test
    public void IntegrationValidateFindPieceTest() {
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

        assertSame("Should be the same and return the king piece",d.king.getClass(),Piece.findPiece(mousex,mousey,a,d).getClass());
        assertEquals("Should be the same position and return the king piece",d.king.pos.x,Piece.findPiece(mousex,mousey,a,d).pos.x);
        assertEquals("Should be the same position and return the king piece",d.king.pos.y,Piece.findPiece(mousex,mousey,a,d).pos.y);
        mousex = 2;
        mousey = 2;
        assertNull("Should be the null as no piece exists in that position",Piece.findPiece(mousex,mousey,a,d));

        assertEquals(3,d.king.getNumValidMoves());
        d.toggleActive();
        a.toggleActive();
        mousex = 4;
        mousey=3;
        assertSame("Should be the same and return the knight1 piece",a.knight1.getClass(),Piece.findPiece(mousex,mousey,a,d).getClass());
        assertEquals("Should be the same position and return the Knight1 piece",a.knight1.pos.x,Piece.findPiece(mousex,mousey,a,d).pos.x);
        assertEquals("Should be the same position and return the Knight1 piece",a.knight1.pos.y,Piece.findPiece(mousex,mousey,a,d).pos.y);
        d.toggleActive();
        a.toggleActive();
        assertTrue("defender must be true",d.isActive());
        assertFalse("Attacker must be False",a.isActive());
    }

    @Test
    public void IntegrationGetValidMoves() {
        Attacker abc = new Attacker();
        Defender def = new Defender();
        Position knightA1StartPos = new Position(1, 1);
        Position knightA2StartPos = new Position(3, 1);
        Position bishopAStartPos = new Position(2, 2);
        Position kingAtartPos = new Position(1, 3);
        abc.initKnight(1, knightA1StartPos);
        abc.initKnight(2, knightA2StartPos);
        def.initKing(kingAtartPos);
        abc.initBishop(bishopAStartPos);
        abc.bishop.getValidMoves(def.king);
        abc.knight1.getValidMoves(def.king);
        abc.knight2.getValidMoves(def.king);
        def.king.getValidMoves(def.king);
        assertEquals("First valid position should be (3,3)", 3, abc.bishop.validMoves[0].x);
        assertEquals("First valid position should be (3,3)", 3, abc.bishop.validMoves[0].y);
        assertEquals("Second valid position should be (4,4)", 4, abc.bishop.validMoves[1].x);
        assertEquals("Second valid position should be (4,4)", 4, abc.bishop.validMoves[1].y);
        def.king.changePiecePos(new Position(0,4));
        abc.knight2.changePiecePos(new Position(3,0));
        abc.knight1.changePiecePos(new Position(4,1));
        abc.bishop.changePiecePos(new Position(5,0));
        abc.knight1.getValidMoves(def.king);
        abc.knight2.getValidMoves(def.king);
        abc.bishop.getValidMoves(def.king);


    }
}
