package pl.igor.battleships.domain.model;

public class PlayerCreator {

    Player createPlayer(String playerName) {
        return new StandardPlayer(playerName);
    }

    Player createComputerPlayer(String playerName, ComputerDifficulty computerDifficulty) {
        return new ComputerPlayer(playerName, computerDifficulty);
    }
}
