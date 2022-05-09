package pl.igor.battleships.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class Grid {

    private final Map<String, Tile> tiles;

    @Getter(value = AccessLevel.PACKAGE)
    private final int size;

    Grid(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Grid size cannot be less than 2x2");
        }

        if (size > 20) {
            throw new IllegalArgumentException("Come on");
        }

        this.size = size;
        tiles = createGridOfSize(size);
    }

    private Map<String, Tile> createGridOfSize(int size) {
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

    List<Tile> getTilesInProximity(String tileNumber) {
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
                .collect(Collectors.toList());
    }

    boolean tileExists(String tileNumber) {
        return tiles.containsKey(tileNumber);
    }

    Tile getTile(@NonNull String tileNumber) {
        if (!tileExists(tileNumber)) {
            throw new IllegalArgumentException("Wrong tileNumber provided to grid: " + tileNumber);
        }

        return tiles.get(tileNumber);
    }
}

