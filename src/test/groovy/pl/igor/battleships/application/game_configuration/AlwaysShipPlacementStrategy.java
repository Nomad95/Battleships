package pl.igor.battleships.application.game_configuration;

import pl.igor.battleships.application.AsciiCommons;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlwaysShipPlacementStrategy implements ShipPlacementStrategy {

    @Override
    public List<PlaceableShip> getShips(int boardSize, List<ShipConfig> shipConfigs) {
        Set<String> tileNumbers = new HashSet<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                String tileNumber = String.format("%s%s", AsciiCommons.I.arrayIndexToLetter(i), j + 1);
                System.out.println(tileNumber);
                tileNumbers.add(tileNumber);
            }
        }

        return List.of(new PlaceableShip(boardSize * boardSize, tileNumbers));
    }
}
