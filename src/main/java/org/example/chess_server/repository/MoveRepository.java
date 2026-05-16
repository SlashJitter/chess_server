package org.example.chess_server.repository;

import org.example.chess_server.entity.MoveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoveRepository extends JpaRepository<MoveEntity, Long> {

    List<MoveEntity> findByGameIdOrderByMoveNumberAsc(String gameId);
}