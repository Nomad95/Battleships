package pl.igor.battleships.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.igor.battleships.application.AsciiCommons;

@Getter(value = AccessLevel.PACKAGE)
@RequiredArgsConstructor
class Tile {
    private TileState tileState;
    private final String tileNumber;

    String getTileNumberAtTopLeft() {
        return asTableCoordinates(tileNumber)
                .moveLeft()
                .moveUp()
                .toTileNumber();
    }

    String getTileNumberAtTop() {
        return asTableCoordinates(tileNumber)
                .moveUp()
                .toTileNumber();
    }

    String getTileNumberAtTopRight() {
        return asTableCoordinates(tileNumber)
                .moveRight()
                .moveUp()
                .toTileNumber();
    }

    String getTileNumberAtRight() {
        return asTableCoordinates(tileNumber)
                .moveRight()
                .toTileNumber();
    }

    String getTileNumberAtLeft() {
        return asTableCoordinates(tileNumber)
                .moveLeft()
                .toTileNumber();
    }

    String getTileNumberAtBottomLeft() {
        return asTableCoordinates(tileNumber)
                .moveLeft()
                .moveDown()
                .toTileNumber();
    }

    String getTileNumberAtBottomRight() {
        return asTableCoordinates(tileNumber)
                .moveRight()
                .moveDown()
                .toTileNumber();
    }

    String getTileNumberAtBottom() {
        return asTableCoordinates(tileNumber)
                .moveDown()
                .toTileNumber();
    }

    private TableCoordinates asTableCoordinates(String tileNumber) {
        char[] chars = tileNumber.toCharArray();
        return new TableCoordinates(
                AsciiCommons.I.letterToArrayIndex(chars[0]),
                (chars[1] - '0'));
    }

    /**
     * Tile representation as a java table coordinates (i, j)
     */
    @Value
    private static class TableCoordinates {
        int x;
        int y;

        TableCoordinates moveRight() {
            return new TableCoordinates(x, y + 1);
        }

        TableCoordinates moveLeft() {
            return new TableCoordinates(x, y - 1);
        }

        TableCoordinates moveUp() {
            return new TableCoordinates(x - 1, y);
        }

        TableCoordinates moveDown() {
            return new TableCoordinates(x + 1, y);
        }

        String toTileNumber() {
            return String.format("%s%s", AsciiCommons.I.arrayIndexToLetter(x), y);
        }
    }
}
