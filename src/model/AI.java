package model;

/**
 * Created by Martin on 2016-10-15.
 */
public class AI {

    CheckersBoard board;
    public AI(CheckersBoard board){
        this.board = board;
    }

    public void moveAI(){
//        for (Tile[] tile: board
//                ) {
//            for (Tile tilePiece: tile
//                    ) {
//
//                if(tilePiece.hasPiece()){
//                    if(tilePiece.getPiece().getPlayer() == curPlayer) {
//                        tilePiece.setSelected(true);
//                        oldTile = tilePiece;
//
//                        if (checkKillMove(tileFromPos(tilePiece.getX() + 2, tilePiece.getY() + tilePiece.getPiece().getDirection()*2))!= null) {
//                            Tile pieceToKill = checkKillMove(tileFromPos(tilePiece.getX() + 2, tilePiece.getY() + tilePiece.getPiece().getDirection()*2));
//                            makeMove(tileFromPos(tilePiece.getX() + 2, tilePiece.getY() + tilePiece.getPiece().getDirection()*2));
//                            pieceToKill.getPiece().destroyPiece();
//                            pieceToKill.setPiece(null);
//
//                            switchPlayer();
//                            return;
//                        }
//                        if (checkKillMove(tileFromPos(tilePiece.getX() - 2, tilePiece.getY() + tilePiece.getPiece().getDirection()*2))!= null) {
//                            Tile pieceToKill = checkKillMove(tileFromPos(tilePiece.getX() - 2, tilePiece.getY() + tilePiece.getPiece().getDirection()*2));
//                            makeMove(tileFromPos(tilePiece.getX() - 2, tilePiece.getY() + tilePiece.getPiece().getDirection()*2));
//                            pieceToKill.getPiece().destroyPiece();
//                            pieceToKill.setPiece(null);
//                            switchPlayer();
//                            return;
//                        }
//                        if (checkMove(tileFromPos(tilePiece.getX() + 1, tilePiece.getY() + tilePiece.getPiece().getDirection()))) {
//                            makeMove(tileFromPos(tilePiece.getX() + 1, tilePiece.getY() + tilePiece.getPiece().getDirection()));
//                            switchPlayer();
//                            return;
//                        }
//
//                        if (checkMove(tileFromPos(tilePiece.getX() - 1, tilePiece.getY() + tilePiece.getPiece().getDirection()))) {
//                            makeMove(tileFromPos(tilePiece.getX() - 1, tilePiece.getY() + tilePiece.getPiece().getDirection()));
//                            switchPlayer();
//                            return;
//                        }
//                        tilePiece.setSelected(false);
//                    }
//                }
//
//            }
//        }
//
    }

}
