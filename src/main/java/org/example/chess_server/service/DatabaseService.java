package org.example.chess_server.service;

import org.example.chess_server.entity.GameEntity;
import org.example.chess_server.entity.MoveEntity;
import org.example.chess_server.model.GameSession;
import org.example.chess_server.model.Move;
import org.example.chess_server.repository.GameRepository;
import org.example.chess_server.repository.MoveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {

    private final GameRepository gameRepository;
    private final MoveRepository moveRepository;

    public DatabaseService(GameRepository gameRepository,
                           MoveRepository moveRepository) {
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
    }

    public void saveGame(GameSession session, String result) {
        GameEntity gameEntity = new GameEntity(
                session.getGameId(),
                session.getPlayer1().getId(),
                session.getPlayer2().getId(),
                result
        );

        gameRepository.save(gameEntity);

        int moveNumber = 1;

        for (Move move : session.getMoves()) {
            String playerId;

            if (moveNumber % 2 == 1) {
                playerId = session.getPlayer1().getId();
            } else {
                playerId = session.getPlayer2().getId();
            }

            MoveEntity moveEntity = new MoveEntity(
                    session.getGameId(),
                    moveNumber,
                    move.getFromRow(),
                    move.getFromCol(),
                    move.getToRow(),
                    move.getToCol(),
                    playerId
            );

            moveRepository.save(moveEntity);
            moveNumber++;
        }
    }

    public List<GameEntity> getAllGames() {
        return gameRepository.findAll();
    }

    public List<MoveEntity> getMovesForGame(String gameId) {
        return moveRepository.findByGameIdOrderByMoveNumberAsc(gameId);
    }
}