package pl.igor.battleships.domain.model;

import lombok.Getter;

import java.util.Optional;
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

    public ShootResult performAttackBy(UUID shootingPlayerId, String tileNumber) {
        Optional<Board> boardOptional = getAttackedPlayerBoard(shootingPlayerId);
        if (boardOptional.isEmpty()) {
            return ShootResult.INCORRECT_PLAYER;
        }
        Board attackedPlayerBoard = boardOptional.get();

        return attackedPlayerBoard.performShootAt(tileNumber);
    }

    private Optional<Board> getAttackedPlayerBoard(UUID attackingPlayerId) {
        if (playerOneBoard.getOwner() == attackingPlayerId) {
            return Optional.of(playerTwoBoard);
        }
        if (playerTwoBoard.getOwner() == attackingPlayerId) {
            return Optional.of(playerOneBoard);
        }

        return Optional.empty();
    }

    public boolean isOver() {
        return !playerOneBoard.isUnsunkenShips() || !playerTwoBoard.isUnsunkenShips();
    }
}
