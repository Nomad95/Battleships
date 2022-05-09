package pl.igor.battleships.domain.model;

public class BattlefieldCreator {
    Board createBoard(Player player, int size) {
        return new Board(player, new Grid(size));
    }
}
