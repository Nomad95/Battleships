package pl.igor.battleships.domain.model;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.UUID;

@Getter(value = AccessLevel.PACKAGE)
public class Battlefield {
    private final UUID gameId;
    private final Player playerOne;
    private final Board playerOneBoard;
    private final Player playerTwo;
    private final Board playerTwoBoard;

    public Battlefield(Player playerOne, Board playerOneBoard, Player playerTwo, Board playerTwoBoard) {
        this.playerOne = playerOne;
        this.playerOneBoard = playerOneBoard;
        this.playerTwo = playerTwo;
        this.playerTwoBoard = playerTwoBoard;
        this.gameId = UUID.randomUUID();
    }
}
