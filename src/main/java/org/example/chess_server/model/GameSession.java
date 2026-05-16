package org.example.chess_server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSession {

    private String gameId;
    private Player player1;
    private Player player2;

    private List<Move> moves = new ArrayList<>();

    private String currentTurn = "WHITE";

    private long whiteTimeMs = 10 * 60 * 1000;
    private long blackTimeMs = 10 * 60 * 1000;

    private long lastMoveTimestamp;

    public GameSession(Player player1, Player player2) {
        this.gameId = UUID.randomUUID().toString();
        this.player1 = player1;
        this.player2 = player2;
        this.lastMoveTimestamp = System.currentTimeMillis();
    }

    public String getGameId() {
        return gameId;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public long getWhiteTimeMs() {
        return whiteTimeMs;
    }

    public long getBlackTimeMs() {
        return blackTimeMs;
    }

    public boolean isPlayersTurn(String playerId) {
        if (currentTurn.equals("WHITE")) {
            return player1.getId().equals(playerId);
        } else {
            return player2.getId().equals(playerId);
        }
    }

    public void addMove(Move move) {
        updateTime();
        moves.add(move);
        switchTurn();
        lastMoveTimestamp = System.currentTimeMillis();
    }

    private void updateTime() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastMoveTimestamp;

        if (currentTurn.equals("WHITE")) {
            whiteTimeMs -= elapsed;
            if (whiteTimeMs < 0) {
                whiteTimeMs = 0;
            }
        } else {
            blackTimeMs -= elapsed;
            if (blackTimeMs < 0) {
                blackTimeMs = 0;
            }
        }
    }

    private void switchTurn() {
        if (currentTurn.equals("WHITE")) {
            currentTurn = "BLACK";
        } else {
            currentTurn = "WHITE";
        }
    }
}