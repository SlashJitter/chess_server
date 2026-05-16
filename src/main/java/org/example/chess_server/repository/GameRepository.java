package org.example.chess_server.repository;

import org.example.chess_server.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository
        extends JpaRepository<GameEntity, String> {

}