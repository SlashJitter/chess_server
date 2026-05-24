package org.example.chess_server.service;

import org.example.chess_server.model.GameSession;
import org.example.chess_server.model.Move;
import org.example.chess_server.model.Player;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Service
public class MatchmakingService {

    private Queue<Player> waitingPlayers = new LinkedList<>();
    private Map<String, GameSession> activeGames = new HashMap<>();

    public GameSession joinGame(Player player) {

        if (waitingPlayers.isEmpty()) {
            waitingPlayers.add(player);
            return null;
        }

        Player opponent = waitingPlayers.poll();
        GameSession session = new GameSession(opponent, player);

        activeGames.put(session.getGameId(), session);

        return session;
    }

    public GameSession getGame(String gameId) {
        return activeGames.get(gameId);
    }

    public GameSession getGameByPlayerId(String playerId) {

        for (GameSession session : activeGames.values()) {

            boolean isPlayer1 =
                    session.getPlayer1().getId().equals(playerId);

            boolean isPlayer2 =
                    session.getPlayer2().getId().equals(playerId);

            if (isPlayer1 || isPlayer2) {
                return session;
            }
        }

        return null;
    }

    public String addMoveToGame(String gameId,
                                Move move,
                                String playerId) {

        GameSession session = activeGames.get(gameId);

        if (session == null) {
            return "Game not found";
        }

        if (!session.isPlayersTurn(playerId)) {
            return "Not your turn";
        }

        session.addMove(move);

        return "Move added";
    }

    public void removeGame(String gameId) {
        activeGames.remove(gameId);
    }
}