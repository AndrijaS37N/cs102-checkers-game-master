package cs102.pr.pack;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class Setup {

    public static final int TILE_SIZE = 75;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    public static int redPoints = 0;
    public static int whitePoints = 0;
    private Tile[][] board = new Tile[WIDTH][HEIGHT];
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    public Parent createContent() {

        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);

        for (int y = 0; y < HEIGHT; y++) {

            for (int x = 0; x < WIDTH; x++) {

                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                board[x][y] = tile;
                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y <= 2 && (x + y) % 2 != 0)
                    piece = makePiece(PieceType.RED, x, y);

                if (y >= 5 && (x + y) % 2 != 0)
                    piece = makePiece(PieceType.WHITE, x, y);

                if (piece != null) {

                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }

        return root;
    }

    private MoveResult tryMove(Piece piece, int newX, int newY) {

        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0)
            return new MoveResult(MoveType.NONE);

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir) {

            return new MoveResult(MoveType.NORMAL);

        } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {

            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType())
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
        }

        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel) {

        if (pixel < 0 || pixel > 700) return 0;
        else return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    public Piece makePiece(PieceType type, int x, int y) {

        Piece piece = new Piece(type, x, y);

        piece.setOnMouseReleased(e -> {

            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());
            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            MoveResult result = tryMove(piece, newX, newY);

            if (newX < 0 || newX > 7 || newY < 0 || newY > 7) piece.move(x0, y0);

            switch (result.getType()) {

                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    break;
                case KILL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    if (piece.type == PieceType.RED) {

                        // add points
                        redPoints++;

                    } else whitePoints++;

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    break;
            }

            System.out.println("R = " + redPoints + " W = " + whitePoints);
        });

        return piece;
    }
}
