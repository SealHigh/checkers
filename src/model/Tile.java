package model;

import java.io.Serializable;
import java.util.Observable;

/**
 * Created by Martin on 2016-10-13.
 */
public class Tile extends Observable implements Serializable {

    private int x,y;
    private boolean color;
    private Piece piece;
    private boolean selected = false;

    public Tile(boolean color,int player, int x, int y){
        this.color = color;
        if(player != 0)
            piece = new Piece(player, x,y);
        this.x = x;
        this.y = y;
    }


    public boolean hasPiece(){
        if(piece != null)
            return true;
        else
            return false;
    }

    public Piece getPiece(){
        return this.piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
        this.setChanged();
        this.notifyObservers();
    }

    public boolean tielHasColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        this.setChanged();
        this.notifyObservers();
    }

}
