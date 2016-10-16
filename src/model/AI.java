package model;

import java.util.ArrayList;
import java.util.Random;
import static model.CheckersBoard.oldTile;
import static model.CheckersBoard.curPlayer;

/**
 * Created by Martin on 2016-10-15.
 */
public class AI {

    CheckersBoard board;
    public AI(CheckersBoard board){
        this.board = board;
    }

    public void moveAI(int winner){
        if(winner != 0)
            return;
        if(!moveBasedOnValue(2,false)) {
            if (!moveBasedOnValue(1, true))
                moveBasedOnValue(1, false);
        }

    }
    private boolean isThreatened(Tile tile){
        Tile tempTile = board.tileFromPos(tile.getX()-1, tile.getY()+ tile.getPiece().getDirection());
        if(tempTile.hasPiece())
            if(tempTile.getPiece().getPlayer() != curPlayer)
                return true;

        if((tempTile = board.tileFromPos(tile.getX()+1, tile.getY()+ tile.getPiece().getDirection())).hasPiece())
            if(tempTile.getPiece().getPlayer() != curPlayer)
                return true;
        return false;
    }
    private boolean moveBasedOnValue(int moveValue, boolean lookForSave){
        ArrayList<ArrayList<Tile>> allMoves = new ArrayList<>();
        for (Tile[] tile: board.getBoard()) {
            for (Tile tilePiece: tile) {
                if(tilePiece.hasPiece()) {
                    if(tilePiece.getPiece().getPlayer() == 2) {
                        tilePiece.setSelected(true);
                        oldTile = tilePiece;
                        ArrayList<Tile> moves = board.getPossibleMoves(tilePiece);
                        if(moves.size()>0) {
                            for (Tile tileMove : moves) {
                                if (Math.abs(oldTile.getY() - tileMove.getY()) == moveValue) {
                                    if(!lookForSave) {
                                        ArrayList<Tile> tempMove = new ArrayList<>();
                                        tempMove.add(tilePiece);
                                        tempMove.add(tileMove);
                                        allMoves.add(tempMove);
                                    }
                                    else if(isThreatened(tilePiece)){
                                        ArrayList<Tile> tempMove = new ArrayList<>();
                                        tempMove.add(tilePiece);
                                        tempMove.add(tileMove);
                                        allMoves.add(tempMove);
                                    }
                                }
                            }
                        }
                        tilePiece.setSelected(false);
                    }
                }
            }
        }
        if(allMoves.size() == 0)
            return false;
        else{
            int randomMove = new Random().nextInt(allMoves.size());
            oldTile= allMoves.get(randomMove).get(0);
            oldTile.setSelected(true);
            board.makeMove(allMoves.get(randomMove).get(1));
            return true;
        }

    }
}
