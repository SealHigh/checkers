package model; /**
 * Created by Martin on 2016-10-13.
 */
import static view.CheckersView.WIDTH;
import static view.CheckersView.HEIGHT;


public class CheckersBoard {


    private Tile[][] board = new Tile[WIDTH][HEIGHT];

    public CheckersBoard(){
        createBoard();
    }

    private void createBoard() {
        for (int y = 0; y< WIDTH; y ++){
            for (int x =0;  x< HEIGHT; x++){
                if(y == 0)
                    board[x][y] = new Tile((x+y)%2 == 0, true,1,x,y); //Has piece player 1
                else if(y==HEIGHT-1)
                    board[x][y] = new Tile((x+y)%2 == 0, true,2,x,y); //Has piece player 2
                else
                    board[x][y] = new Tile((x+y)%2 == 0, false,0,x,y); // No piece
            }
        }
    }

    public Tile[][] getBoard(){
        return board;
    }
    public Tile tileFromPos(int x,int y){
        return board[x][y];
    }

}
