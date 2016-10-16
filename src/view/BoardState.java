package view;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.CheckersBoard;

import java.util.Observable;
import java.util.Observer;


/**
 * Created by Martin on 2016-10-16.
 */
public class BoardState extends Text implements Observer {
    private CheckersBoard board;
    private int TILE_SIZE;
    public BoardState(CheckersBoard board, int TILE_SIZE){
        this.board = board;
        this.TILE_SIZE = TILE_SIZE;
        board.addObserver(this);
        setText("Red's turn");
        setFont(new Font(18));
        setTranslateX(board.getWidth()*TILE_SIZE/2- getLayoutBounds().getWidth()/2);
        setTranslateY(25-getLayoutBounds().getHeight()/5);
    }

    private void displayInMiddle(){
        setFont(new Font(40));
        setTranslateX(board.getWidth()*TILE_SIZE/2- getLayoutBounds().getWidth()/2);
        setTranslateY(board.getHeight()*TILE_SIZE/2+getLayoutBounds().getHeight()/2);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(board.getWinner() == 1){
            setText("Red won");
            displayInMiddle();
        }
        else if(board.getWinner() == 2){
            setText("White won");
            displayInMiddle();
        }
        else {
            if(board.getCurPlayer() == 1)
                setText("Red's turn");
            else if(board.getCurPlayer() == 2)
                setText("White's turn");

        }

    }
}
