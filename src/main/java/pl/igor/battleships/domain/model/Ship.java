package pl.igor.battleships.domain.model;

class Ship {
    private int shipHealthyTiles;

    Ship(int totalTileSize) {
        this.shipHealthyTiles = totalTileSize;
    }

    boolean isSunken() {
        return shipHealthyTiles == 0;
    }

    void takeAHit() {
        if (shipHealthyTiles != 0) {
            shipHealthyTiles--;
        }
    }
}
