package pl.igor.battleships.domain.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Battlefield {
    private final UUID gameId;
    private final Board playerOneBoard;
    private final Board playerTwoBoard;

    public Battlefield(Board playerOneBoard, Board playerTwoBoard) {
        this.gameId = UUID.randomUUID();
        this.playerOneBoard = playerOneBoard;
        this.playerTwoBoard = playerTwoBoard;
    }
}
