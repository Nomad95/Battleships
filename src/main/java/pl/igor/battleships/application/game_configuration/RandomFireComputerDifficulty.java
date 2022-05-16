package pl.igor.battleships.application.game_configuration;

import pl.igor.battleships.application.GridHelper;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomFireComputerDifficulty implements ComputerDifficulty {

    private final List<String> availableTiles;

    public RandomFireComputerDifficulty(int gridSize, GridHelper gridHelper) {
        this.availableTiles = gridHelper.getAllGridTileNames(gridSize);
    }

    @Override
    public String getNextTileToShootAt() {
        return availableTiles.get(ThreadLocalRandom.current().nextInt(0, availableTiles.size()));
    }

    @Override
    public void acknowledgeHitting(String tileNumber) {
        availableTiles.remove(tileNumber);
    }
}
