package pl.igor.battleships.domain.model;

import java.util.List;


class Ship {
    private final int totalTileSize;
    private int shipHealthyTiles;
    private final List<Tile> shipTiles;

    Ship(int totalTileSize, List<Tile> shipTiles) {
        this.totalTileSize = totalTileSize;
        this.shipHealthyTiles = totalTileSize;
        this.shipTiles = shipTiles;

    }

    boolean isSunken() {
        return shipHealthyTiles == 0;
    }

    void takeAHit() {
        shipHealthyTiles--;
    }
}
