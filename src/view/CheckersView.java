package view;
import controller.CheckersController;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import model.CheckersBoard;
import model.Tile;

/**
 * Created by Martin on 2016-10-13.
 */
public class CheckersView extends BorderPane {

    private final int TILE_SIZE = 100, WIDTH, HEIGHT;
    private final int menuBarHeight = 25;
    private CheckersBoard board;
    Text currentPlayer = new Text();

    public CheckersView(CheckersBoard board){
        this.board = board;
        this.WIDTH = board.getWidth();
        this.HEIGHT = board.getHeight();
        CheckersController controller;
        controller = new CheckersController(board, this);
        initView(controller);
    }

    public void setBoard(CheckersBoard board){
        this.board = board;
    }

    public void initView(CheckersController controller) {
        this.setPrefSize(WIDTH * TILE_SIZE, HEIGHT*TILE_SIZE+menuBarHeight);
        BoardState curPlayer = new BoardState(board, TILE_SIZE);
        CheckersMenu menu = new CheckersMenu(menuBarHeight,WIDTH*TILE_SIZE, controller);
        Group top  = new Group();
        top.getChildren().addAll(menu,curPlayer);
        this.setTop(menu);

        Group tileGroup = new Group();
        Group pieceGroup = new Group();
        Group game  = new Group();
        game.getChildren().addAll(tileGroup,pieceGroup);
        game.setTranslateY(menuBarHeight); //place game scene below menubar
        this.getChildren().addAll(game,curPlayer);


        for (Tile[] tile: board.getBoard()
             ) {
            for (Tile tilePiece: tile
                    ) {
                TileView tileRect  = new TileView(tilePiece, TILE_SIZE);
                tileRect.setOnMousePressed(event -> controller.onTilePressed((int)event.getSceneX()/TILE_SIZE,(int) (event.getSceneY()-menuBarHeight)/TILE_SIZE));
                tileGroup.getChildren().add(tileRect);

                if(tilePiece.hasPiece()) {
                    PieceView piece = new PieceView(tilePiece.getPiece(),TILE_SIZE, board.getTimer());
                    piece.setOnMousePressed(event -> controller.onTilePressed((int)event.getSceneX()/TILE_SIZE,(int) (event.getSceneY()-menuBarHeight)/TILE_SIZE));
                    pieceGroup.getChildren().add(piece);
                }
            }
        }
    }


}
