package org.example.chess_server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "moves")
public class MoveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gameId;

    private int moveNumber;

    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;

    private String playerId;

    public MoveEntity() {
    }

    public MoveEntity(String gameId, int moveNumber,
                      int fromRow, int fromCol,
                      int toRow, int toCol,
                      String playerId) {
        this.gameId = gameId;
        this.moveNumber = moveNumber;
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.playerId = playerId;
    }

    public Long getId() {
        return id;
    }

    public String getGameId() {
        return gameId;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public int getToRow() {
        return toRow;
    }

    public int getToCol() {
        return toCol;
    }

    public String getPlayerId() {
        return playerId;
    }
}