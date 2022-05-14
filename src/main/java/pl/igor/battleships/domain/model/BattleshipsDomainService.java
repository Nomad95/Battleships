package pl.igor.battleships.domain.model;

import lombok.NonNull;
import pl.igor.battleships.domain.ShipsMapping;

import java.util.UUID;

public class BattleshipsDomainService {

    public Board createBoard(@NonNull UUID playerId, int gridSize) {
        return new Board(playerId, gridSize);
    }

    public void placeShips(Board board, ShipsMapping shipsMapping) {
        board.populateWithShips(shipsMapping);
    }
}
