package org.example.chess_server.service;

import org.example.chess_server.model.Move;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private final List<Move> moves = new ArrayList<>();

    public void addMove(Move move) {
        moves.add(move);
        System.out.println(move);
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void resetGame() {
        moves.clear();
    }
}