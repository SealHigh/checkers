package controller;

import model.CheckersBoard;
import model.Tile;
import view.CheckersView;

/**
 * Created by Martin on 2016-10-13.
 */
public class CheckersController {

    CheckersBoard board;
    CheckersView view;
    Tile oldTile;

    public CheckersController(CheckersBoard board, CheckersView view){
        this.view = view;
        this.board = board;
    }

    public void onPiecePressed(int x, int y){
        Tile tempTile = board.tileFromPos(x,y);
    }

    public void onTilePressed(int x, int y){
        Tile tempTile = board.tileFromPos(x,y);
        if(oldTile != null) {
            if (oldTile.isSelected() && oldTile.hasPiece()){
                oldTile.setPiece(false);
                oldTile.setSelected(false);
                tempTile.setPiece(true);
            }
            else {
                oldTile.setSelected(false);
                tempTile.setSelected(!tempTile.isSelected());
            }
        }
        else
            tempTile.setSelected(!tempTile.isSelected());


        oldTile = tempTile;

        view.initView(this);
    }

}
