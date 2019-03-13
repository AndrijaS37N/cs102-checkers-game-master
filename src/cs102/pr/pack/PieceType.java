package cs102.pr.pack;

public enum PieceType {
    RED(1), WHITE(-1);
    final int moveDir;

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}
