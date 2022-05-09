package pl.igor.battleships.application.game_configuration;


import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class StandardBattleshipsConfiguration implements BattleshipsConfiguration {
    List<ShipConfig> shipConfigs = List.of(new ShipConfig("Destroyer", 4,2), new ShipConfig("Battleship", 5,1));
    int boardSize = 10;
    String shipPlacementAlgorithm = "ADVANCED";

    @Override
    public int getGridSize() {
        return boardSize;
    }

    @Override
    public List<PlaceableShip> getShipsToPlace() {
        ArrayList<PlaceableShip> placeableShips = Lists.newArrayList();
        for (ShipConfig shipConfig : shipConfigs) {
            int numberOfShips = shipConfig.getNumberOfShips();
            for (int i = 0; i < numberOfShips; i++) {
                placeableShips.add(new PlaceableShip(shipConfig.getShipSize()));
            }
        }

        return placeableShips;
    }

    @Override
    public String getPlacementAlgorithmName() {
        return shipPlacementAlgorithm;
    }

}
