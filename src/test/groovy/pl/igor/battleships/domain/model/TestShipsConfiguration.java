package pl.igor.battleships.domain.model;

import lombok.AllArgsConstructor;
import pl.igor.battleships.application.game_configuration.BattleshipsConfiguration;
import pl.igor.battleships.application.game_configuration.PlaceableShip;
import pl.igor.battleships.application.game_configuration.ShipPlacementStrategy;

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

