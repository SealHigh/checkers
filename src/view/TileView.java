package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Tile;

import java.util.Observable;
import java.util.Observer;



/**
 * Created by Martin on 2016-10-13.
 */
public class TileView extends Rectangle implements Observer {

    private model.Tile tile;

    public TileView(model.Tile tile, int TILE_SIZE){
        this.tile = tile;
        tile.addObserver(this);
        setX(tile.getX()*TILE_SIZE);
        setY(tile.getY()*TILE_SIZE);
        setHeight(TILE_SIZE);
        setWidth(TILE_SIZE);
        setFill(tile.tielHasColor() ? Color.CORNSILK : Color.TAN);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(tile.isSelected())
            setStroke(Color.RED);
        else
            setStroke(Color.TRANSPARENT);
    }
}
