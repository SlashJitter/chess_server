package org.example.chess_server.controller;

import org.example.chess_server.model.Move;
import org.example.chess_server.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // TEST
    @GetMapping("/test")
    public String test() {
        return "Server merge!";
    }

    // ADAUGA MUTARE
    @PostMapping("/move")
    public String makeMove(@RequestBody Move move) {
        gameService.addMove(move);
        return "Move received";
    }

    // IA TOATE MUTARILE
    @GetMapping("/moves")
    public List<Move> getMoves() {
        return gameService.getMoves();
    }

    // RESET JOC
    @PostMapping("/reset")
    public String resetGame() {
        gameService.resetGame();
        return "Game reset";
    }
}