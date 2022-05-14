package pl.igor.battleships.domain.model;

import pl.igor.battleships.application.game_configuration.PlaceableShip2;
import pl.igor.battleships.application.game_configuration.ShipConfig;
import pl.igor.battleships.application.game_configuration.ShipPlacementStrategy;

import java.util.List;

public class AdvancedShipPlacementStrategy implements ShipPlacementStrategy {

    @Override
    public List<PlaceableShip2> getShips(int boardSize, List<ShipConfig> shipConfigs) {
        //get ships placements
        //a1 = ship
        //a2 = ship etc
        return null;
    }
}
