package org.example.chess_server.model;

public class Move {

    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;

    public Move() {}

    public int getFromRow() { return fromRow; }
    public void setFromRow(int fromRow) { this.fromRow = fromRow; }

    public int getFromCol() { return fromCol; }
    public void setFromCol(int fromCol) { this.fromCol = fromCol; }

    public int getToRow() { return toRow; }
    public void setToRow(int toRow) { this.toRow = toRow; }

    public int getToCol() { return toCol; }
    public void setToCol(int toCol) { this.toCol = toCol; }

    @Override
    public String toString() {
        return "Move: (" + fromRow + "," + fromCol + ") -> (" + toRow + "," + toCol + ")";
    }
}