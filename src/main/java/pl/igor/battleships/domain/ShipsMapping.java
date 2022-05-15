package pl.igor.battleships.domain;

import lombok.RequiredArgsConstructor;
import pl.igor.battleships.application.game_configuration.PlaceableShip;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ShipsMapping {
    private final List<PlaceableShip> shipsMapping;

    public List<PlaceableShip> getShipsMapping() {
        return new ArrayList<>(shipsMapping);
    }
}
