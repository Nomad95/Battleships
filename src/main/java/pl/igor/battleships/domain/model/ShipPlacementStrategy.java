package pl.igor.battleships.domain.model;

public interface ShipPlacementStrategy {
    void placeShipOfTiles(Grid grid, int shipSize);
}
