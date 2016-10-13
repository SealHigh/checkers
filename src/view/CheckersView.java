package view;

import controller.CheckersController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.CheckersBoard;
import model.Tile;

/**
 * Created by Martin on 2016-10-13.
 */
public class CheckersView extends Pane {

    public final static int TILE_SIZE = 100, WIDTH = 8, HEIGHT = 8;

    private CheckersBoard board;

    public CheckersView(CheckersBoard board){
        this.board = board;
        CheckersController controller;
        controller = new CheckersController(board, this);
        initView(controller);
    }

    public void initView(CheckersController controller) {
        this.setPrefSize(WIDTH * TILE_SIZE, HEIGHT*TILE_SIZE);

        for (Tile[] tile: board.getBoard()
             ) {
            for (Tile tilePiece: tile
                    ) {
                Rectangle tileRect = new Rectangle(tilePiece.getX()*TILE_SIZE, tilePiece.getY()*TILE_SIZE,TILE_SIZE,TILE_SIZE);
                tileRect.setFill(tilePiece.tielHasColor() ? Color.CORNSILK : Color.TAN);
                if(tilePiece.isSelected())
                    tileRect.setStroke(Color.RED);

                tileRect.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        controller.onTilePressed((int)event.getX()/TILE_SIZE,(int) event.getY()/TILE_SIZE);
                    }
                });

                this.getChildren().add(tileRect);

                //Draw pieces
                if(tilePiece.hasPiece()) {
                    Circle tileCircle = new Circle(tilePiece.getX() * TILE_SIZE+ TILE_SIZE/2, tilePiece.getY() * TILE_SIZE + TILE_SIZE/2, TILE_SIZE/2-5);
                    if(tilePiece.getPlayer() == 1)
                        tileCircle.setFill(Color.BLACK);
                    if(tilePiece.getPlayer() == 2)
                        tileCircle.setFill(Color.RED);

                    tileCircle.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {

                            controller.onPiecePressed((int)event.getSceneX()/TILE_SIZE,(int) event.getSceneY()/TILE_SIZE);
                        }
                    });
                    this.getChildren().add(tileCircle);
                }



            }
        }
    }

}
