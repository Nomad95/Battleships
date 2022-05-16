package pl.igor.battleships.domain.model;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.igor.battleships.application.AsciiCommons;
import pl.igor.battleships.application.game_configuration.PlaceableShip;
import pl.igor.battleships.domain.ShipsMapping;

import java.util.*;

@RequiredArgsConstructor
@Getter(value = AccessLevel.PACKAGE)
public class Board {
    private final UUID owner;
    private final List<Ship> playerShips;
    private final Map<String, Tile> tiles;
    private final int gridSize;
    private boolean unsunkenShips = false;

    Board(@NonNull UUID playerId, int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Grid size cannot be less than 2x2");
        }

        if (size > 30) {
            throw new IllegalArgumentException("Come on");
        }

        this.owner = playerId;
        this.gridSize = size;
        this.playerShips = Lists.newArrayList();
        tiles = initializeGridOfSize(size);
    }

    private Map<String, Tile> initializeGridOfSize(int size) {
        HashMap<String, Tile> grid = new HashMap<>(size * size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                String tileNumber = String.format("%s%s", AsciiCommons.I.arrayIndexToLetter(i), j + 1);
                grid.put(tileNumber, new Tile(tileNumber));
            }
        }

        return grid;
    }

    Optional<Tile> getTile(@NonNull String tileNumber) {
        return Optional.ofNullable(tiles.get(tileNumber));
    }

    void populateWithShips(@NonNull ShipsMapping shipsMapping) {
        for (PlaceableShip shipToPlace : shipsMapping.getShipsMapping()) {
            Ship ship = new Ship(shipToPlace.getShipSize());
            shipToPlace.getTileNumbers().stream()
                    .map(this::getTile)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(tile -> tile.setAsPartOfShip(ship));

            playerShips.add(ship);
        }

        checkLivingShips();
    }

    private void checkLivingShips() {
        unsunkenShips = playerShips.stream().anyMatch(ship -> !ship.isSunken());
    }

    ShootResult performShootAt(@NonNull String tileNumber) {
        Optional<Tile> tileOptional = getTile(tileNumber);
        if (tileOptional.isEmpty()) {
            return ShootResult.WRONG_TILE;
        }

        ShootResult hitResult = tileOptional.get().hit();
        checkLivingShips();
        return hitResult;
    }

}
