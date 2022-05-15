package pl.igor.battleships.domain.model;

import lombok.AccessLevel;
import lombok.Getter;


@Getter(value = AccessLevel.PACKAGE)
class Tile {
    private TileState tileState = TileState.WATER;
    private final String tileNumber;
    private Ship partOfShip;

    Tile(String tileNumber) {
        this.tileNumber = tileNumber;
        this.partOfShip = null;
    }

    ShootResult hit() {
        if (TileState.WATER == tileState) {
            setAsHitWater();
            return ShootResult.MISSED;
        }
        if (TileState.SHIP == tileState) {
            setAsHitShip();
            partOfShip.takeAHit();
            if (partOfShip.isSunken()) {
                return ShootResult.SINK;
            }

            return ShootResult.HIT;
        }
        if (TileState.HIT_SHIP == tileState || TileState.HIT_WATER == tileState) {
            return ShootResult.ALREADY_HIT;
        }

        return ShootResult.TILE_INCORRECT;
    }

    void setAsPartOfShip(Ship ship) {
        setAsShip();
        partOfShip = ship;
    }

    private void setAsShip() {
        tileState = TileState.SHIP;
    }

    private void setAsHitWater() {
        tileState = TileState.HIT_WATER;
    }

    private void setAsHitShip() {
        tileState = TileState.HIT_SHIP;
    }
}
