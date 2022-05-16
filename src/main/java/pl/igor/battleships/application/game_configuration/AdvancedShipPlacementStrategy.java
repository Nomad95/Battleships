package pl.igor.battleships.application.game_configuration;

import lombok.RequiredArgsConstructor;
import pl.igor.battleships.application.GridHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class AdvancedShipPlacementStrategy implements ShipPlacementStrategy {

    private static final int PLACEMENT_ERRORS_THRESHOLD = 4;
    private static final int DEAD_END_THRESHOLD = 3;
    private final GridHelper gridHelper;

    @Override
    public List<PlaceableShip> getShips(int boardSize, List<ShipConfig> shipConfigs) {
        List<String> availableTiles = gridHelper.getAllGridTileNames(boardSize);

        ArrayList<PlaceableShip> placeableShips = new ArrayList<>();
        for (ShipConfig shipConfig : shipConfigs) {
            System.out.printf("placing %s %s ships of size %s%n", shipConfig.getNumberOfShips(), shipConfig.getShipType(), shipConfig.getShipSize());
            int shipsToPlace = shipConfig.getNumberOfShips();

            int botchedPlacementThreshold = availableTiles.size();

            while (shipsToPlace > 0) {

                if (botchedPlacementThreshold == 0) {
                    throw new IllegalArgumentException("There is no possibility to place all ships");
                }

                System.out.println("Placing a " + shipConfig.getShipType());
                Set<String> shipTileNumbers = new HashSet<>(shipConfig.getShipSize());

                if (availableTiles.isEmpty() || availableTiles.size() < shipConfig.getShipSize()) {
                    throw new IllegalArgumentException("Could not fit more ships to this grid");
                }

                String randomStartingShipPlace = getRandomTile(availableTiles);

                GridHelper.GridCoordinates startingTile = gridHelper.numberToCoordinates(randomStartingShipPlace);

                shipTileNumbers.add(startingTile.toTileNumber());
                availableTiles.remove(startingTile.toTileNumber());

                PlacementDirection shipProgressionDirection = getRandomDirection();

                int shipTilesToPlace = shipConfig.getShipSize() - 1;
                int placementErrors = 0;
                int deadEnds = 0;

                GridHelper.GridCoordinates currentTile = startingTile;

                while (isShipInConstruction(shipTilesToPlace)) {
                    if (couldNotFitShipHere(deadEnds)) {
                        System.out.println("whoopsie i cant place this ship here! trying again from a new start tile!");
                        //new direction from start tile start again from diffrent start tile
                        availableTiles.addAll(shipTileNumbers);//rollback
                        break;
                    }

                    if (couldNotPlaceShipInThisDirection(placementErrors)) {
                        System.out.println("whoopsie cant place ship further! trying again from start tile and different location");
                        availableTiles.addAll(shipTileNumbers);//rollback
                        availableTiles.remove(startingTile.toTileNumber());
                        //new direction from start tile
                        shipProgressionDirection = shipProgressionDirection.nextPlacementDirection();
                        deadEnds++;
                        placementErrors = 0;
                        currentTile = startingTile;//dead end try again from starting tile
                    }

                    GridHelper.GridCoordinates nextTile = getNextTile(currentTile, shipProgressionDirection);
                    if (!availableTiles.contains(nextTile.toTileNumber())) { //no need to check if is outside the grid
                        placementErrors++;
                        continue;
                    }

                    availableTiles.remove(nextTile.toTileNumber());
                    shipTileNumbers.add(nextTile.toTileNumber());
                    shipTilesToPlace--;
                    currentTile = nextTile;
                }

                if (couldNotFitShipHere(deadEnds)) {
                    System.out.println("Starting again");
                    botchedPlacementThreshold--;
                    continue;
                }

                placeableShips.add(new PlaceableShip(shipConfig.getShipSize(), shipTileNumbers));
                System.out.println("Ship placed!");
                shipsToPlace--;
            }
        }

        return placeableShips;
    }

    private PlacementDirection getRandomDirection() {
        int randomInt = ThreadLocalRandom.current().nextInt(0, 4);
        switch (randomInt) {
            case 0: return PlacementDirection.LEFT;
            case 1: return PlacementDirection.UP;
            case 2: return PlacementDirection.RIGHT;
            case 3: return PlacementDirection.DOWN;
        }

        throw new IllegalStateException("Could not get random placement direction");
    }

    private GridHelper.GridCoordinates getNextTile(GridHelper.GridCoordinates tile, PlacementDirection direction) {
        switch (direction) {
            case LEFT: return tile.moveLeft();
            case UP: return tile.moveUp();
            case RIGHT: return tile.moveRight();
            case DOWN: return tile.moveDown();
        }

        throw new IllegalStateException("Error while trying to place another ship tile");
    }

    private String getRandomTile(List<String> availableTiles) {
        int randomTileIndex = ThreadLocalRandom.current().nextInt(0, availableTiles.size());
        return availableTiles.get(randomTileIndex);
    }

    enum PlacementDirection {
        LEFT, RIGHT, DOWN, UP;

        PlacementDirection nextPlacementDirection() {
            switch (this) {
                case UP: return RIGHT;
                case RIGHT: return DOWN;
                case DOWN: return LEFT;
                case LEFT: return UP;
            }

            throw new IllegalStateException("Cant pick up next direction");
        }
    }

    private boolean couldNotFitShipHere(int deadEnds) {
        return deadEnds == DEAD_END_THRESHOLD;
    }

    private boolean couldNotPlaceShipInThisDirection(int placementErrors) {
        return placementErrors == 1;
    }

    private boolean isShipInConstruction(int shipTilesToPlace) {
        return shipTilesToPlace > 0;
    }

}
