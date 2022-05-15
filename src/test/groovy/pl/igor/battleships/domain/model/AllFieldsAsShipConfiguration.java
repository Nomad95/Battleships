package pl.igor.battleships.domain.model;

import pl.igor.battleships.application.game_configuration.BattleshipsConfiguration;
import pl.igor.battleships.application.game_configuration.PlaceableShip;

import java.util.List;

public class AllFieldsAsShipConfiguration implements BattleshipsConfiguration {
    @Override
    public int getGridSize() {
        return 10;
    }

    @Override
    public List<PlaceableShip> newShipsPlacementMapping() {
        return null;
    }

}

