package model;

/**
 * Created by Martin on 2016-10-13.
 */
public class Tile {

    private int x,y;
    private boolean hasPiece,color;
    private int player;
    private boolean selected = false;

    public Tile(boolean color,boolean hasPiece,int player, int x, int y){
        this.color = color;
        this.hasPiece = hasPiece;
        this.player = player;
        this.x = x;
        this.y = y;
    }


    public boolean hasPiece(){
        return hasPiece;
    }
    public void setPiece(boolean hasPiece){
        this.hasPiece = hasPiece;
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

    public int getPlayer() {
        return player;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
