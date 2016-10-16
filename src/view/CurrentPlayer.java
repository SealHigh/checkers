package view;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.CheckersBoard;

import java.util.Observable;
import java.util.Observer;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by Martin on 2016-10-16.
 */
public class CurrentPlayer extends Text implements Observer {
    private CheckersBoard board;

    public CurrentPlayer(CheckersBoard board, int TILE_SIZE){
        this.board = board;
        board.addObserver(this);
        setText("Red's turn");
        setFont(new Font(18));
        setTranslateX(board.getWidth()*TILE_SIZE/2- getLayoutBounds().getWidth()/2);
        setTranslateY(25-getLayoutBounds().getHeight()/5);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(board.getWinner() == 1)
            showMessageDialog(null, "Winner is Red");
        else if(board.getWinner() == 2)
            showMessageDialog(null, "Winner is White");
        else {
            if(board.getCurPlayer() == 1)
                setText("Red's turn");
            else if(board.getCurPlayer() == 2)
                setText("White's turn");

        }

    }
}
