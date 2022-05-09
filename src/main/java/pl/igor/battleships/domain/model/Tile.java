package pl.igor.battleships.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(value = AccessLevel.PACKAGE)
@RequiredArgsConstructor
class Tile {
    private TileState tileState;
    private final String tileNumber;

    String getTopLeft() {
    }

    String getTop() {
    }

    String getTopRight() {
    }

    String getRight() {
        return null;
    }

    String getLeft() {
    }

    String getBottomLeft() {
    }

    String getBottomRight() {
    }

    String getBottom() {
    }
}
