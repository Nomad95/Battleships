package pl.igor.battleships.application.game_configuration;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TestShipsConfiguration implements BattleshipsConfiguration {

    private int gridSize;
    private ShipPlacementStrategy shipPlacementStrategy;

    @Override
    public int getGridSize() {
        return gridSize;
    }

    @Override
    public List<PlaceableShip> newShipsPlacementMapping() {
        return shipPlacementStrategy.getShips(gridSize, List.of());
    }

}

