package view;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import model.Piece;
import java.util.Observable;
import java.util.Observer;


/**
 * Created by Martin on 2016-10-13.
 */
public class PieceView extends StackPane implements Observer {


    private final int TILE_SIZE;
    private model.Piece piece;
    private Circle pieceBottom = new Circle();
    private Ellipse pieceTop = new Ellipse();
    private int oldX, oldY;
    private int timer;
    public PieceView(model.Piece piece, int TILE_SIZE, int timer){
        this.piece = piece;
        this.timer = timer;
        piece.addObserver(this);
        oldY = piece.getY();
        oldX = piece.getX();
        this.TILE_SIZE = TILE_SIZE;
        int offset = TILE_SIZE / 20;
        pieceBottom = new Circle(TILE_SIZE/2-TILE_SIZE/20);
        pieceTop = new Ellipse(pieceBottom.getRadius()- offset /2, pieceBottom.getRadius()- offset);
        pieceTop.setTranslateX(offset /8);
        pieceTop.setTranslateY(-offset /1.75);

        pieceBottom.setFill(Color.BLACK);
        if(piece.getPlayer() == 1)
            pieceTop.setFill(Color.RED);
        else if(piece.getPlayer() == 2)
            pieceTop.setFill(Color.WHITE);
        else
            pieceTop.setFill(Color.WHITE);

        this.getChildren().addAll(pieceBottom, pieceTop);
        this.relocate(piece.getX() * TILE_SIZE + offset,piece.getY() * TILE_SIZE+ offset);
    }

    @Override
    public void update(Observable o, Object arg) {
        TranslateTransition tt = new TranslateTransition(javafx.util.Duration.millis(timer),this);
        tt.setByX(TILE_SIZE*(piece.getX()-oldX));
        tt.setByY(TILE_SIZE*(piece.getY()-oldY));
        tt.play();
        oldX= piece.getX();
        oldY= piece.getY();

        if(!piece.isAlive())
            this.getChildren().removeAll(pieceBottom,pieceTop);

    }
}
