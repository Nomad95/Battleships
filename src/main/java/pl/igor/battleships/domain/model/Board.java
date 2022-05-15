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

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Getter(value = AccessLevel.PACKAGE)
public class Board {
    private final UUID owner;
    private final List<Ship> playerShips;
    private final Map<String, Tile> tiles;
    private final int gridSize;
    private boolean readyToPlay = false;
    private boolean unsunkenShips = false;
    private boolean inOngoingGame = false;

    Board(UUID playerId, int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Grid size cannot be less than 2x2");
        }

        if (size > 20) {
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
                System.out.println(tileNumber);
                grid.put(tileNumber, new Tile(tileNumber));
            }
        }

        return grid;
    }

    List<Tile> getTilesInProximity(@NonNull String tileNumber) {
        Tile tile = getTile(tileNumber);
        Set<String> adjacentTiles = Set.of(
                tile.getTileNumberAtTopLeft(),
                tile.getTileNumberAtTop(),
                tile.getTileNumberAtTopRight(),
                tile.getTileNumberAtRight(),
                tile.getTileNumberAtLeft(),
                tile.getTileNumberAtBottomLeft(),
                tile.getTileNumberAtBottomRight(),
                tile.getTileNumberAtBottom());

        return adjacentTiles.stream()
                .filter(this::tileExists)
                .map(this::getTile)
                .collect(toList());
    }

    boolean tileExists(@NonNull String tileNumber) {
        return tiles.containsKey(tileNumber);
    }

    Tile getTile(@NonNull String tileNumber) {
        if (!tileExists(tileNumber)) {
            throw new IllegalArgumentException("Wrong tileNumber provided to grid: " + tileNumber);
        }

        return tiles.get(tileNumber);
    }

    List<String> getSortedTilesNumbers() {
        List<String> strings = new ArrayList<>(tiles.keySet());
        Collections.sort(strings);
        return strings;
    }

    void populateWithShips(ShipsMapping shipsMapping) {
        for (PlaceableShip shipToPlace : shipsMapping.getShipsMapping()) {
            List<Tile> shipTiles = shipToPlace.getTileNumbers().stream()
                    .map(this::getTile)
                    .peek(Tile::setAsShip)
                    .collect(toList());
            playerShips.add(new Ship(shipToPlace.getShipSize(), shipTiles));
        }

        setAsReady();
        checkLivingShips();
    }

    private void checkLivingShips() {
        unsunkenShips = playerShips.stream().anyMatch(ship -> !ship.isSunken());
    }

    private void setAsReady() {
        readyToPlay = true;
    }
}
