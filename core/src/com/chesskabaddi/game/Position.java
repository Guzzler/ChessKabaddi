package com.chesskabaddi.game;

public class Position{
    int x,y;
    public Position(int x,int y){
        this.x = x;
        this.y = y;
    }
    public Position(Position pos){
        this.x = pos.x;
        this.y = pos.y;
    }

    public void changePos(Position p){
        this.x = p.x;
        this.y = p.y;
    }
}