package pl.igor.battleships.application;

import lombok.RequiredArgsConstructor;
import pl.igor.battleships.application.game_configuration.BattleshipsConfiguration;
import pl.igor.battleships.domain.model.RandomFireComputerDifficulty;
import pl.igor.battleships.presentation.BattlefieldDto;
import pl.igor.battleships.presentation.Difficulty;
import pl.igor.battleships.presentation.PlayerDto;

@RequiredArgsConstructor
public class BattleshipsFacade {

    private final Players players;
    private final Game game;

    public PlayerDto createHumanPlayer(String name) {
        return players.createNewPlayer(name);
    }

    public PlayerDto createComputerPlayer(String name, Difficulty difficulty) {
        return players.createNewComputerPlayer(name, new RandomFireComputerDifficulty());//TODO
    }

    public BattlefieldDto createGame(PlayerDto player1, PlayerDto player2, BattleshipsConfiguration battleshipsConfiguration) {
        return game.createNewGame(player1, player2, battleshipsConfiguration);
    }
}
