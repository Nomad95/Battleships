package pl.igor.battleships.application.game_configuration;

import java.util.List;

public interface BattleshipsConfiguration {

    int getGridSize();

    List<PlaceableShip> newShipsPlacementMapping();
}
