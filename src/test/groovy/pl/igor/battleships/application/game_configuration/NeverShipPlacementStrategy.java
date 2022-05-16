package pl.igor.battleships.application.game_configuration;

import java.util.List;

public class NeverShipPlacementStrategy implements ShipPlacementStrategy {

    @Override
    public List<PlaceableShip> getShips(int boardSize, List<ShipConfig> shipConfigs) {
        return List.of();
    }
}
