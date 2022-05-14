package pl.igor.battleships.application.game_configuration;

import java.util.List;

public interface ShipPlacementStrategy {
    List<PlaceableShip2> getShips(int boardSize, List<ShipConfig> shipConfigs);
}
