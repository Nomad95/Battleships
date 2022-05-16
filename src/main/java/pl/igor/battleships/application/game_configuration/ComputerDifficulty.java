package pl.igor.battleships.application.game_configuration;

public interface ComputerDifficulty {
    String getNextTileToShootAt();
    void acknowledgeHitting(String tileNumber);
}
