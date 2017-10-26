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

    @Override
    public boolean equals(Object ob) {
        if (ob == null)
            return false;
        if (ob.getClass() != getClass())
            return false;
        Position other = (Position)ob;
        if(other.x == this.x && other.y ==this.y)
            return true;
        else{
            return false;
        }
    }
}