package pl.igor.battleships.domain;

import lombok.RequiredArgsConstructor;
import pl.igor.battleships.application.game_configuration.PlaceableShip2;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ShipsMapping {
    private final List<PlaceableShip2> shipsMapping;

    public List<PlaceableShip2> getShipsMapping() {
        return new ArrayList<>(shipsMapping);
    }
}
