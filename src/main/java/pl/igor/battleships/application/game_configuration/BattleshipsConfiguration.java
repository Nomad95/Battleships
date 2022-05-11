package pl.igor.battleships.application.game_configuration;

import pl.igor.battleships.domain.model.ShipPlacementStrategy;

import java.util.List;

public interface BattleshipsConfiguration {

    int getGridSize();

    List<PlaceableShip> getShipsToPlace();

    ShipPlacementStrategy getShipPlacementStrategy();
}
