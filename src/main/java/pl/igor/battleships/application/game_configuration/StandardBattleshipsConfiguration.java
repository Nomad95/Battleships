package pl.igor.battleships.application.game_configuration;


import pl.igor.battleships.application.GridTraverser;

import java.util.List;

public class StandardBattleshipsConfiguration implements BattleshipsConfiguration {
    List<ShipConfig> shipConfigs = List.of(new ShipConfig("Destroyer", 4,2), new ShipConfig("Battleship", 5,1));
    int boardSize = 10;
    ShipPlacementStrategy shipPlacementAlgorithm = new AdvancedShipPlacementStrategy(new GridTraverser());

    @Override
    public int getGridSize() {
        return boardSize;
    }

    @Override
    public List<PlaceableShip> newShipsPlacementMapping() {
        return shipPlacementAlgorithm.getShips(boardSize, shipConfigs);
    }

}
