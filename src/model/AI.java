package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;
import static model.CheckersBoard.curPlayer;
import static model.CheckersBoard.canMoveAgain;

/**
 * Created by Martin on 2016-10-15.
 */
public class AI {

    private CheckersBoard board;
    private boolean moving;
    private int timer;
    public AI(CheckersBoard board){
        this.board = board;
    }


    /**
     * Make a move, if move captured a piece
     * and piece that moved can capture another go again
     * @param winner
     * Make sure no one has won
     * @param timer
     * Time between moves (don't play instantly)
     */
    public void moveAI(int winner, int timer){
        this.timer = timer;
        if(winner != 0)
            return;
        if(!moveBasedOnValue(2,false)) {
            if (!moveBasedOnValue(1, true))
                moveBasedOnValue(1, false);
        }
    }

    public void moveAgain(){
        Timeline timeline = new Timeline(new KeyFrame( //Call AI after a cretain time
                Duration.millis(timer),
                actionEvent -> moveBasedOnValue(2, false)));
        timeline.play();
    }
    /**
     * Checks if a piece could be capture by opponent
     *
     * @param tile
     * Tile to check
     * @return
     * If it can be captured or not
     */
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

    /**
     * Check for most valuable moves and preform a random one of highest value
     * Capture a piece is value highest, second is normal move that moves a pice out of harms way
     * and then just a simple move
     * @param moveValue
     * @param lookForSave
     * @return
     */
    private boolean moveBasedOnValue(int moveValue, boolean lookForSave){
        ArrayList<ArrayList<Tile>> allMoves = new ArrayList<>();
        for (Tile[] tile: board.getBoard()) {
            for (Tile tilePiece: tile) {
                if(tilePiece.hasPiece()) {
                    if(tilePiece.getPiece().getPlayer() == 2) {
                        tilePiece.setSelected(true);
                        ArrayList<Tile> moves = board.getPossibleMoves(tilePiece);
                        if(moves.size()>0) {
                            for (Tile tileMove : moves) {
                                if (Math.abs(tilePiece.getY() - tileMove.getY()) == moveValue) {
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
        moving = false;
        if(allMoves.size() == 0)
            return false;
        else if(canMoveAgain){
            for (ArrayList<Tile> tileMove:allMoves) {
                if(board.getSelectedTile() == tileMove.get(0)){
                    board.makeMove(tileMove.get(1));
                    return true;
                }
            }
        }
        else {
            int randomMove = new Random().nextInt(allMoves.size());
            board.setSelectedTile(allMoves.get(randomMove).get(0));
            board.makeMove(allMoves.get(randomMove).get(1));
            return true;
        }
        return false;
    }
}
