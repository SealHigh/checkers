package model;

import java.io.Serializable;
import java.util.Observable;

/**
 * Created by Martin on 2016-10-13.
 */
public class Piece extends Observable implements Serializable {
    private int player,direction;
    private int x,y;
    private boolean alive = true;
    private boolean queen = false;

    public Piece(int player, int x, int y){
        this.player = player;
        this.x =x;
        this.y =y;
        if(player == 1)
            this.direction = 1;
        else if (player== 2)
            this.direction=-1;
    }

    public int getPlayer() {
        return player;
    }

    public int getDirection() {
        return direction;
    }


    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
        setChanged();
        notifyObservers();
    }

    public void destroyPiece(){
        alive = false;
        setChanged();
        notifyObservers();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isQueen() {
        return queen;
    }

    public void setQueen(boolean queen) {
        this.queen = queen;
        setChanged();
        notifyObservers();
    }
}
