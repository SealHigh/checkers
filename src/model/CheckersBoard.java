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

    private int WIDTH = 6, HEIGHT =6, timer = 400;
    private Tile[][] board = new Tile[WIDTH][HEIGHT];
    public static int curPlayer;
    public static boolean canMoveAgain;
    private Tile selectedTile;
    private int winner;
    private boolean moving, paused,AI;
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
     * @param selectedTile
     * Tile containing piece to check moves for
     * @return
     * Arraylist of all possible moves
     */
    public ArrayList<Tile> getPossibleMoves(Tile selectedTile){
        ArrayList<Tile> moves = new ArrayList<>();

        Tile tempTile;
        if(checkMove(tempTile = tileFromPos(selectedTile.getX()+1, selectedTile.getY()+selectedTile.getPiece().getDirection()),selectedTile))
            moves.add(tempTile);

        if(checkMove(tempTile =tileFromPos(selectedTile.getX()-1, selectedTile.getY()+selectedTile.getPiece().getDirection()),selectedTile))
            moves.add(tempTile);

        if(checkMove(tempTile =tileFromPos(selectedTile.getX()-2, selectedTile.getY()+selectedTile.getPiece().getDirection()*2),selectedTile))
            moves.add(tempTile);

        if(checkMove(tempTile =tileFromPos(selectedTile.getX()+2, selectedTile.getY()+selectedTile.getPiece().getDirection()*2),selectedTile))
            moves.add(tempTile);

        if(selectedTile.getPiece().isQueen()){
            if(checkMove(tempTile = tileFromPos(selectedTile.getX()+1, selectedTile.getY()-selectedTile.getPiece().getDirection()),selectedTile))
                moves.add(tempTile);

            if(checkMove(tempTile =tileFromPos(selectedTile.getX()-1, selectedTile.getY()-selectedTile.getPiece().getDirection()),selectedTile))
                moves.add(tempTile);

            if(checkMove(tempTile =tileFromPos(selectedTile.getX()-2, selectedTile.getY()-selectedTile.getPiece().getDirection()*2),selectedTile))
                moves.add(tempTile);

            if(checkMove(tempTile =tileFromPos(selectedTile.getX()+2, selectedTile.getY()-selectedTile.getPiece().getDirection()*2),selectedTile))
                moves.add(tempTile);
        }

        return moves;
    }

    /**
     * Check if selectedTile can move to desired tile,
     * checks both 1 step and 2 steps(step over opponent tile)
     * @param tempTile
     * Tile to getMove to
     * @return
     * If it can or can't getMove (boolean)
     */
    private boolean checkMove(Tile tempTile, Tile selectedTile){
        if (selectedTile.isSelected() && !tempTile.hasPiece() ) {
            if (Math.abs(tempTile.getX()- selectedTile.getX()) == 1 && tempTile.getY()- selectedTile.getY()  == selectedTile.getPiece().getDirection()){
                    return true;
            }
            if (selectedTile.getPiece().isQueen()){
                if (Math.abs(tempTile.getX()- selectedTile.getX()) == 1 && tempTile.getY()- selectedTile.getY()  == -selectedTile.getPiece().getDirection()){
                    return true;
                }
            }
        }

        int tempX = (selectedTile.getX() + tempTile.getX())/2;
        int tempY = (selectedTile.getY() + tempTile.getY())/2;
        Tile tileInMiddle = tileFromPos(tempX, tempY);
        if (selectedTile.isSelected() && tileInMiddle.hasPiece()&& !tempTile.hasPiece() ) {
            if(tileInMiddle.getPiece().getPlayer()!= selectedTile.getPiece().getPlayer()) {
                if (Math.abs(tempTile.getX() - selectedTile.getX()) == 2 && tempTile.getY() - selectedTile.getY() == selectedTile.getPiece().getDirection() * 2) {
                    return true;
                }
                if (selectedTile.getPiece().isQueen()){
                    if (Math.abs(tempTile.getX() - selectedTile.getX()) == 2 && tempTile.getY() - selectedTile.getY() == -selectedTile.getPiece().getDirection() * 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Removes pice inbetween selectedTile
     * and a tile 2 steps away
     * @param tempTile
     * Tile that the piece is moving to
     */
    private void killPiece(Tile tempTile){
        int tempX = (selectedTile.getX() + tempTile.getX())/2;
        int tempY = (selectedTile.getY() + tempTile.getY())/2;
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
     * Swap selectedTile with new tile
     * @param tempTile
     * Tile to swap with
     */
    private void swapOldTile(Tile tempTile){
        selectedTile.setSelected(false);
        tempTile.setPiece(selectedTile.getPiece());
        selectedTile.getPiece().setPos(tempTile.getX(), tempTile.getY());
        selectedTile.setPiece(null);
    }

    /**
     * Selected tile has been stored as selectedTile and its piece
     * is now getting moved to another tile, after move check winCondition
     * @param tempTile
     * This is the tile we want to move to
     */
    public void makeMove(Tile tempTile){
        if(!canMoveAgain && Math.abs(selectedTile.getY() - tempTile.getY())==1) { //getMove 1 step
            moving = true;
            swapOldTile(tempTile);
            setIfQueen(tempTile);
            checkWin();
            switchPlayer();
        }
        else if(Math.abs(selectedTile.getY() - tempTile.getY())==2) { //getMove 2 steps and kills an enemy piece
            canMoveAgain = false;
            moving = true;
            killPiece(tempTile);
            swapOldTile(tempTile);
            setIfQueen(tempTile);
            checkWin();

            selectedTile = tempTile;
            selectedTile.setSelected(true);
            for (Tile tile: getPossibleMoves(tempTile)) {
                if(Math.abs(tempTile.getY() - tile.getY())==2)
                    canMoveAgain = true;
                if(canMoveAgain && AI && curPlayer ==2)
                    ai.moveAgain();
            }
            if(!canMoveAgain) {
                selectedTile.setSelected(false);
                canMoveAgain=false;
                switchPlayer();
            }
        }
        Timeline timeline = new Timeline(new KeyFrame( //Wait before next move
                Duration.millis(timer),
                actionEvent -> moving=false));
        timeline.play();
    }

    /**
     * Get players move, and if AI is activated let AI play
     * @param tempTile
     * Tile that got pressed
     */
    public void getMove(Tile tempTile) {
        if (!moving && winner==0 && !paused) {
            if (selectedTile != null) {
                ArrayList<Tile> moves = getPossibleMoves(selectedTile);
                for (Tile tile : moves
                        ) {
                    if (tempTile == tile) { //desired getMove is within possible moves
                        makeMove(tempTile);
                        if (AI && curPlayer == 2) {
                            Timeline timeline = new Timeline(new KeyFrame( //Call AI after a cretain time
                                    Duration.millis(timer),
                                    actionEvent -> ai.moveAI(winner, timer)));
                            timeline.play();
                        }
                        return;
                    }
                }
            }

            if (tempTile.hasPiece() && !tempTile.isSelected() && !canMoveAgain) {
                if (tempTile.getPiece().getPlayer() == curPlayer) {
                    tempTile.setSelected(true);
                    if(selectedTile != null && selectedTile != tempTile)
                        selectedTile.setSelected(false);
                    selectedTile = tempTile;
                }
            }
            else if(tempTile == selectedTile && canMoveAgain){
                canMoveAgain = false;
                selectedTile.setSelected(false);
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
                    selectedTile = tilePiece;
                    if(tilePiece.getPiece().getPlayer() == 1)
                        player1 += getPossibleMoves(tilePiece).size();
                    if(tilePiece.getPiece().getPlayer() == 2)
                        player2 += getPossibleMoves(tilePiece).size();
                    selectedTile.setSelected(false);
                }
            }
        }
        if(player1 == 0)
            setWinner(2);
        else if(player2== 0)
            setWinner(1);

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
            return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile){
        this.selectedTile = selectedTile;
    }
    public Tile getSelectedTile(){
        return this.selectedTile;
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

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused= paused;
        setChanged();
        notifyObservers();
    }
}
