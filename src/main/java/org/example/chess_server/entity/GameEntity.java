package org.example.chess_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class GameEntity {

    @Id
    private String id;

    private String player1;

    private String player2;

    private String result;

    public GameEntity() {
    }

    public GameEntity(String id,
                      String player1,
                      String player2,
                      String result) {

        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getResult() {
        return result;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public void setResult(String result) {
        this.result = result;
    }
}