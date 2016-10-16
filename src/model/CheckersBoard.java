package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/**
 * Created by Martin on 2016-10-13.
 */



public class CheckersBoard extends Observable implements Serializable {

    private int WIDTH = 6, HEIGHT =6, timer = 500;
    private Tile[][] board = new Tile[WIDTH][HEIGHT];
    private int curPlayer = 1;
    private Tile oldTile;
    private boolean AI;
    private int winner = 0;
    private boolean moving;

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

    private void createBoard(boolean AI) {
        this.AI = AI;
        winner = 0;
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
        curPlayer = 1;
        createBoard(AI);
    }

    private ArrayList<Tile> possibleMoves(Tile tile){
        ArrayList moves = new ArrayList<Tile>();

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

        if (oldTile.isSelected() && tileFromPos(tempX, tempY).hasPiece()&& !tempTile.hasPiece() ) {
            if(tileFromPos(tempX, tempY).getPiece().getPlayer()!= oldTile.getPiece().getPlayer()) {
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

    private Tile pieceToKill(Tile tempTile){
        int tempX = (oldTile.getX() + tempTile.getX())/2;
        int tempY = (oldTile.getY() + tempTile.getY())/2;
        return tileFromPos(tempX, tempY);
    }

    private void makeMove(Tile tempTile){
        if(Math.abs(oldTile.getY() - tempTile.getY())==1) {
            moving=true;
            oldTile.setSelected(false);
            tempTile.setPiece(oldTile.getPiece());
            oldTile.getPiece().setPos(tempTile.getX(), tempTile.getY());
            oldTile.setPiece(null);
            switchPlayer();
        }

        else if(Math.abs(oldTile.getY() - tempTile.getY())==2) {
            moving=true;
            pieceToKill(tempTile).getPiece().destroyPiece();
            pieceToKill(tempTile).setPiece(null);
            oldTile.setSelected(false);
            tempTile.setPiece(oldTile.getPiece());
            oldTile.getPiece().setPos(tempTile.getX(), tempTile.getY());
            oldTile.setPiece(null);
            switchPlayer();
        }

        if (tempTile.getPiece().getDirection() == -1 && tempTile.getY() == 0){
            System.out.print("test");
            tempTile.getPiece().setQueen(true);
        }
        System.out.println(tempTile.getPiece().getDirection());
        if (tempTile.getPiece().getDirection() == 1 && tempTile.getY() == WIDTH-1) {
            tempTile.getPiece().setQueen(true);
            System.out.print("test");
        }
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(timer),
                actionEvent->checkWin()));
        timeline.play();

    }

    public void move(Tile tempTile) {
        if (!moving) {
            if (oldTile != null) {
                ArrayList<Tile> moves = possibleMoves(oldTile);
                for (Tile tile : moves
                        ) {
                    if (tempTile == tile) {
                        makeMove(tempTile);
                        if (AI && curPlayer == 2) {
                            Timeline timeline = new Timeline(new KeyFrame( //Call AI after a cretain time
                                    Duration.millis(timer),
                                    actionEvent -> moveAI()));
                            timeline.play();
                        }
                        return;
                    }
                }
            }
            if (tempTile.hasPiece() && !tempTile.isSelected()) {
                if (tempTile.getPiece().getPlayer() == curPlayer) {
                    tempTile.setSelected(true);
                    if(oldTile != null)
                        oldTile.setSelected(false);
                    oldTile = tempTile;
                }
            }
        }
    }
    private void setWinner(int winner){
        this.winner = winner;
        setChanged();
        notifyObservers();
    }

    private void checkWin(){
        int player1 = 0;
        int player2 = 0;
        for (Tile[] tile: board
                ) {
            for (Tile tilePiece: tile
                    )
            {
                if(tilePiece.hasPiece()){
                    tilePiece.setSelected(true);
                    oldTile = tilePiece;
                    if(tilePiece.getPiece().getPlayer() == 1)
                        player1 += possibleMoves(tilePiece).size();
                    if(tilePiece.getPiece().getPlayer() == 2)
                        player2 += possibleMoves(tilePiece).size();
                    oldTile.setSelected(false);
                }
            }
        }
        if(player1 == 0)
            setWinner(2);
        else if(player2 == 0)
            setWinner(1);
        else
            moving=false;
    }

    private void moveAI(){
        if(winner != 0)
            return;
        for (Tile[] tile: board) {
            for (Tile tilePiece: tile) {
                if(tilePiece.hasPiece()) {
                    if(tilePiece.getPiece().getPlayer() == curPlayer) {
                        tilePiece.setSelected(true);
                        oldTile = tilePiece;
                        ArrayList<Tile> moves = possibleMoves(tilePiece);
                        if(moves.size()>0) {
                            Random rand = new Random();
                            makeMove(moves.get(rand.nextInt(moves.size())));
                            return;
                        }
                        else {
                            tilePiece.setSelected(false);
                        }
                    }
                }
            }
        }
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
