package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Martin on 2016-10-13.
 */



public class CheckersBoard extends Observable implements Serializable {

    private int WIDTH = 8, HEIGHT =8, timer = 400;
    private Tile[][] board = new Tile[WIDTH][HEIGHT];
    public static int curPlayer;
    public static Tile oldTile;
    private boolean AI;
    private int winner;
    private boolean moving;
    private AI ai;

    public CheckersBoard(){
        createBoard(true);
    }

    public int getWidth(){
        return WIDTH;
    }

    public int getHeight(){
        return HEIGHT;
    }

    public void setBoard(CheckersBoard board){
        this.board = board.getBoard();
    }

    /**
     * Initializes board
     * @param AI
     * Play vs AI(true) or another player(false)
     */
    private void createBoard(boolean AI) {
        this.AI = AI;
        if(AI)
            ai = new AI(this);
        winner = 0;
        curPlayer = 1;
        moving = false;
        for (int y = 0; y< WIDTH; y ++){
            for (int x =0;  x< HEIGHT; x++){
                if(y < HEIGHT/2 -1 && (x+y)%2 != 0)
                    board[x][y] = new Tile((x+y)%2 == 0,1,x,y); //Has piece player 1
                else if(y>  HEIGHT/2  &&(x+y)%2 != 0)
                    board[x][y] = new Tile((x+y)%2 == 0,2,x,y); //Has piece player 2
                else
                    board[x][y] = new Tile((x+y)%2 == 0,0,x,y); // No piece
            }
        }
    }

    public void resetBoard(boolean AI){
        createBoard(AI);
    }

    /**
     * Test all possible moves for a tile, including if it's a queen
     * (should be perfect for an alpha-beta AI)
     * @param tile
     * Tile containing piece to check moves for
     * @return
     * Arraylist of all possible moves
     */
    public ArrayList<Tile> getPossibleMoves(Tile tile){
        ArrayList<Tile> moves = new ArrayList<>();

        Tile tempTile;
        if(checkMove(tempTile = tileFromPos(tile.getX()+1, tile.getY()+tile.getPiece().getDirection())))
            moves.add(tempTile);

        if(checkMove(tempTile =tileFromPos(tile.getX()-1, tile.getY()+tile.getPiece().getDirection())))
            moves.add(tempTile);

        if(checkMove(tempTile =tileFromPos(tile.getX()-2, tile.getY()+tile.getPiece().getDirection()*2)))
            moves.add(tempTile);

        if(checkMove(tempTile =tileFromPos(tile.getX()+2, tile.getY()+tile.getPiece().getDirection()*2)))
            moves.add(tempTile);

        if(tile.getPiece().isQueen()){
            if(checkMove(tempTile = tileFromPos(tile.getX()+1, tile.getY()-tile.getPiece().getDirection())))
                moves.add(tempTile);

            if(checkMove(tempTile =tileFromPos(tile.getX()-1, tile.getY()-tile.getPiece().getDirection())))
                moves.add(tempTile);

            if(checkMove(tempTile =tileFromPos(tile.getX()-2, tile.getY()-tile.getPiece().getDirection()*2)))
                moves.add(tempTile);

            if(checkMove(tempTile =tileFromPos(tile.getX()+2, tile.getY()-tile.getPiece().getDirection()*2)))
                moves.add(tempTile);
        }

        return moves;
    }

    /**
     * Check if selectedTile can getMove to desired tile,
     * checks both 1 step and 2 steps
     * @param tempTile
     * Tile to getMove to
     * @return
     * If it can or can't getMove (boolean)
     */
    private boolean checkMove(Tile tempTile){
        if (oldTile.isSelected() && !tempTile.hasPiece() ) {
            if (Math.abs(tempTile.getX()-oldTile.getX()) == 1 && tempTile.getY()-oldTile.getY()  == oldTile.getPiece().getDirection()){
                    return true;
            }
            if (oldTile.getPiece().isQueen()){
                if (Math.abs(tempTile.getX()-oldTile.getX()) == 1 && tempTile.getY()-oldTile.getY()  == -oldTile.getPiece().getDirection()){
                    return true;
                }
            }
        }

        int tempX = (oldTile.getX() + tempTile.getX())/2;
        int tempY = (oldTile.getY() + tempTile.getY())/2;
        Tile tileInMiddle = tileFromPos(tempX, tempY);
        if (oldTile.isSelected() && tileInMiddle.hasPiece()&& !tempTile.hasPiece() ) {
            if(tileInMiddle.getPiece().getPlayer()!= oldTile.getPiece().getPlayer()) {
                if (Math.abs(tempTile.getX() - oldTile.getX()) == 2 && tempTile.getY() - oldTile.getY() == oldTile.getPiece().getDirection() * 2) {
                    return true;
                }
                if (oldTile.getPiece().isQueen()){
                    if (Math.abs(tempTile.getX() - oldTile.getX()) == 2 && tempTile.getY() - oldTile.getY() == -oldTile.getPiece().getDirection() * 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Removes pice inbetween oldTile
     * and a tile 2 steps away
     * @param tempTile
     * Tile that the piece is moving to
     */
    private void killPiece(Tile tempTile){
        int tempX = (oldTile.getX() + tempTile.getX())/2;
        int tempY = (oldTile.getY() + tempTile.getY())/2;
        Tile tileInMiddle = tileFromPos(tempX, tempY);
        tileInMiddle.getPiece().destroyPiece();
        tileInMiddle.setPiece(null);
    }

    /**
     * Check if piece has reached opposite end
     * if it has make it to a queen
     * @param tempTile
     * Tile containing piece to check
     */
    private void setIfQueen(Tile tempTile){
        if (tempTile.getPiece().getDirection() == -1 && tempTile.getY() == 0){ //reaching opposite side
            tempTile.getPiece().setQueen(true);
        }
        if (tempTile.getPiece().getDirection() == 1 && tempTile.getY() == WIDTH-1) { //reaching opposite side
            tempTile.getPiece().setQueen(true);
        }
    }
    /**
     * Swap oldTile with new tile
     * @param tempTile
     * Tile to swap with
     */
    private void swapOldTile(Tile tempTile){
        oldTile.setSelected(false);
        tempTile.setPiece(oldTile.getPiece());
        oldTile.getPiece().setPos(tempTile.getX(), tempTile.getY());
        oldTile.setPiece(null);
    }

    /**
     * Selected tile has been stored as oldTile and its piece
     * is now getting moved to another tile.
     * @param tempTile
     * This is the tile we want to move to
     */
    public void makeMove(Tile tempTile){
        if(Math.abs(oldTile.getY() - tempTile.getY())==1) { //getMove 1 step
            moving=true;
            swapOldTile(tempTile);
            setIfQueen(tempTile);
            switchPlayer();
        }
        else if(Math.abs(oldTile.getY() - tempTile.getY())==2) { //getMove 2 steps and kills an enemy piece
            moving=true;
            killPiece(tempTile);
            swapOldTile(tempTile);
            setIfQueen(tempTile);
            switchPlayer();
        }

        Timeline timeline = new Timeline(new KeyFrame( //Wait a little while before next player can go
                Duration.millis(timer),
                actionEvent->checkWin()));
        timeline.play();

    }

    /**
     * Get players move, and if AI is activated let AI play
     * @param tempTile
     * Tile that got pressed
     */
    public void getMove(Tile tempTile) {
        if (!moving && winner==0) {
            if (oldTile != null) {
                ArrayList<Tile> moves = getPossibleMoves(oldTile);
                for (Tile tile : moves
                        ) {
                    if (tempTile == tile) { //desired getMove is within possible moves
                        makeMove(tempTile);
                        if (AI) {
                            Timeline timeline = new Timeline(new KeyFrame( //Call AI after a cretain time
                                    Duration.millis(timer),
                                    actionEvent -> ai.moveAI(winner)));
                            timeline.play();
                        }
                        return;
                    }
                }
            }

            if (tempTile.hasPiece() && !tempTile.isSelected()) {
                if (tempTile.getPiece().getPlayer() == curPlayer) {
                    tempTile.setSelected(true);
                    if(oldTile != null && oldTile != tempTile)
                        oldTile.setSelected(false);
                    oldTile = tempTile;
                }
            }
            else if(tempTile == oldTile){
                oldTile.setSelected(false);
                switchPlayer();
            }
        }
    }

    private void setWinner(int winner){
        this.winner = winner;
        setChanged();
        notifyObservers();
    }

    /**
     * Loops through all pieces and checks if a player
     * doesn't have any pieces that can move.
     */
    private void checkWin(){
        int player1 = 0;
        int player2 = 0;
        for (Tile[] tile: board) {
            for (Tile tilePiece: tile) {
                if(tilePiece.hasPiece()){
                    tilePiece.setSelected(true);
                    oldTile = tilePiece;
                    if(tilePiece.getPiece().getPlayer() == 1)
                        player1 += getPossibleMoves(tilePiece).size();
                    if(tilePiece.getPiece().getPlayer() == 2)
                        player2 += getPossibleMoves(tilePiece).size();
                    oldTile.setSelected(false);
                }
            }
        }
        if(player1 == 0)
            setWinner(2);
        else if(player2== 0)
            setWinner(1);
        else
            moving=false;
    }

    private void switchPlayer(){
        if(curPlayer == 1)
            curPlayer = 2;
        else
            curPlayer = 1;
        setChanged();
        notifyObservers();
    }

    public Tile[][] getBoard(){
        return board;
    }

    public Tile tileFromPos(int x,int y){
        if(x >= 0 && x < WIDTH && y >= 0 && y< HEIGHT)
        return board[x][y];
        else
            return oldTile;
    }

    public int getCurPlayer() {
        return curPlayer;
    }

    public int getWinner() {
        return winner;
    }

    public int getTimer() {
        return timer;
    }
}
