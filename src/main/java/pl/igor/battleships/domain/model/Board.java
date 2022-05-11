package pl.igor.battleships.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.igor.battleships.application.game_configuration.PlaceableShip;

import java.util.List;

@RequiredArgsConstructor
@Getter(value = AccessLevel.PACKAGE)
class Board {
    private final Player owner;
    private final Grid grid;
    private boolean ready = false;
    private boolean unsunkenShips = true;
    private boolean inOngoingGame = false;
    private List<Ship> playerShips;//TODO: maybe change board to just grid?

    void populateWithShips(List<PlaceableShip> placeableShips, ShipPlacementStrategy shipPlacementStrategy) {
        placeableShips //grid.setShips(stratrgy?)
                .forEach(placeableShip -> shipPlacementStrategy.placeShipOfTiles(grid, placeableShip.getShipSize()));
        setReady();
    }

    private void setReady() {
        ready = true;
    }
}
