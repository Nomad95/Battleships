package pl.igor.battleships.application;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GridHelper {

    public GridCoordinates numberToCoordinates(String tileNumber) {
        char[] chars = tileNumber.toCharArray();
        return new GridCoordinates(
                AsciiCommons.I.letterToArrayIndex(chars[0]),
                (chars[1] - '0'));
    }

    public List<String> getAllGridTileNames(int gridSize) {
        List<String> tileNumbers = new ArrayList<>();

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                String tileNumber = String.format("%s%s", AsciiCommons.I.arrayIndexToLetter(i), j + 1);
                tileNumbers.add(tileNumber);
            }
        }

        return tileNumbers;
    }

    @Value
    public static class GridCoordinates {
        int x;
        int y;

        public GridCoordinates moveRight() {
            return new GridCoordinates(x + 1, y);
        }

        public GridCoordinates moveLeft() {
            return new GridCoordinates(x - 1, y);
        }

        public GridCoordinates moveUp() {
            return new GridCoordinates(x, y - 1);
        }

        public GridCoordinates moveDown() {
            return new GridCoordinates(x, y + 1);
        }

        public String toTileNumber() {
            return String.format("%s%s", AsciiCommons.I.arrayIndexToLetter(x), y);
        }

        public Set<String> getVicinityTileNumbers() {
            GridCoordinates left = moveLeft();
            GridCoordinates leftTop = left.moveUp();
            GridCoordinates top = leftTop.moveRight();
            GridCoordinates rightTop = top.moveRight();
            GridCoordinates right = rightTop.moveDown();
            GridCoordinates rightBottom = right.moveDown();
            GridCoordinates bottom = rightBottom.moveLeft();
            GridCoordinates leftBottom = bottom.moveLeft();

            return Stream.of(left, leftTop, top, rightTop, right, rightBottom, bottom, leftBottom)
                    .map(GridCoordinates::toTileNumber)
                    .collect(Collectors.toSet());
        }
    }
}
