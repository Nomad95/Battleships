package pl.igor.battleships.domain.model;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;


class Ship {
    private final int totalTileSize;
    private int shipHealthyTiles;
    @Getter(value = AccessLevel.PACKAGE)
    private final List<Tile> shipTiles;

    public Ship(int totalTileSize, List<Tile> shipTiles) {
        this.totalTileSize = totalTileSize;
        this.shipHealthyTiles = totalTileSize;
        this.shipTiles = shipTiles;
    }

    boolean isSunken() {
        return shipHealthyTiles == 0;
    }

}
