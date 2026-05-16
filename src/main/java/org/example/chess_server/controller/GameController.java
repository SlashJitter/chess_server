package org.example.chess_server.controller;

import org.example.chess_server.dto.MoveDTO;
import org.example.chess_server.entity.GameEntity;
import org.example.chess_server.entity.MoveEntity;
import org.example.chess_server.model.GameSession;
import org.example.chess_server.model.Move;
import org.example.chess_server.model.Player;
import org.example.chess_server.service.DatabaseService;
import org.example.chess_server.service.GameService;
import org.example.chess_server.service.MatchmakingService;
import org.example.chess_server.service.WebSocketNotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final MatchmakingService matchmakingService;
    private final DatabaseService databaseService;
    private final WebSocketNotificationService webSocketNotificationService;

    public GameController(GameService gameService,
                          MatchmakingService matchmakingService,
                          DatabaseService databaseService,
                          WebSocketNotificationService webSocketNotificationService) {
        this.gameService = gameService;
        this.matchmakingService = matchmakingService;
        this.databaseService = databaseService;
        this.webSocketNotificationService = webSocketNotificationService;
    }

    @GetMapping("/test")
    public String test() {
        return "Server merge!";
    }

    @GetMapping("/history")
    public List<GameEntity> getGameHistory() {
        return databaseService.getAllGames();
    }

    @GetMapping("/{gameId}/saved-moves")
    public List<MoveEntity> getSavedMoves(@PathVariable String gameId) {
        return databaseService.getMovesForGame(gameId);
    }

    @PostMapping("/join")
    public String joinGame(@RequestBody Player player) {
        GameSession session = matchmakingService.joinGame(player);

        if (session == null) {
            return "Waiting for opponent...";
        }

        webSocketNotificationService.notifyGameUpdate(
                session.getGameId(),
                "Game started: " + session.getGameId()
        );

        return "Game started! ID: " + session.getGameId();
    }

    @GetMapping("/{gameId}")
    public GameSession getGame(@PathVariable String gameId) {
        return matchmakingService.getGame(gameId);
    }

    @PostMapping("/{gameId}/move")
    public String makeMove(@PathVariable String gameId,
                           @RequestBody MoveDTO moveDTO) {

        Move move = new Move();
        move.setFromRow(moveDTO.fromRow);
        move.setFromCol(moveDTO.fromCol);
        move.setToRow(moveDTO.toRow);
        move.setToCol(moveDTO.toCol);

        String result = matchmakingService.addMoveToGame(
                gameId,
                move,
                moveDTO.playerId
        );

        if (result.equals("Move added")) {
            webSocketNotificationService.notifyGameUpdate(
                    gameId,
                    "Move added by " + moveDTO.playerId
            );
        }

        return result;
    }

    @PostMapping("/{gameId}/end")
    public String endGame(@PathVariable String gameId,
                          @RequestParam String result) {

        GameSession session = matchmakingService.getGame(gameId);

        if (session == null) {
            return "Game not found";
        }

        databaseService.saveGame(session, result);
        matchmakingService.removeGame(gameId);

        webSocketNotificationService.notifyGameUpdate(
                gameId,
                "Game ended with result: " + result
        );

        return "Game saved with result: " + result;
    }

    @GetMapping("/moves")
    public List<Move> getMoves() {
        return gameService.getMoves();
    }

    @PostMapping("/reset")
    public String resetGame() {
        gameService.resetGame();
        return "Game reset";
    }
}