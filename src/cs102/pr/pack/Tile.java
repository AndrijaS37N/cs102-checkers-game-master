package cs102.pr.pack;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private Piece piece;

    public Tile(boolean light, int x, int y) {
        setWidth(Setup.TILE_SIZE);
        setHeight(Setup.TILE_SIZE);
        relocate(x * Setup.TILE_SIZE, y * Setup.TILE_SIZE);
        setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
